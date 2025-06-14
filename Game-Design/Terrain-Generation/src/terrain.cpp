#include "core.hpp"
#include "terrain.hpp"
#include "texture.hpp"
#include  <iostream>

Terrain::Terrain(int width, int height) {
    this->generateMesh(width, height);
}

void Terrain::generateMesh(int width, int height) {
    std::random_device rd;
    std::mt19937 gen(rd());
    std::uniform_int_distribution<> distr(0, 1000);
    this->perlin = new siv::PerlinNoise(distr(gen));

    std::vector<Vertex> vertices;
    std::vector<unsigned int> indices;
    std::vector<Texture> textures;

    vertices.reserve(width * height);

    float w = (float)glutGet(GLUT_WINDOW_WIDTH);
    int currentIndex = 0;

    const float scale = 50.0f;

    std::vector<std::string> tex = {
        "assets/whispy-grass-meadow-unity/wispy-grass-meadow_albedo.png",
        "assets/Rough-rockface1-Unity/Rough-rockface1_Base_Color.png",
        "assets/snow-packed12-Unity2-1/snow-packed12-Base_Color.png"
    };

    std::vector<double> previousNoise;
    std::vector<double> currentNoise;

    for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
            glm::vec2 coord = glm::vec2(1.0f, 0.0f);
            if (x % 2 == 0 && y % 2 == 0)
                coord = glm::vec2(0.0f, 0.0f);
            if (x % 2 != 0 && y % 2 != 0)
                coord = glm::vec2(1.0f, 1.0f);
            if (x % 2 == 0 && y % 2 != 0)
                coord = glm::vec2(0.0f, 1.0f);
            
            double z = this->generateNoise(x, y);

            currentNoise.push_back(z);

            glm::vec3 tangent(0.0f, 0.0f, 1.0f);
            glm::vec3 bitangent(0.0f, 0.0f, 1.0f);

            if (x > 0 && y > 0) {
                glm::vec3 pos1(x, y, z);
                glm::vec3 pos2(x - 1, y, currentNoise[x - 1]);
                glm::vec3 pos3(x, y - 1, previousNoise[x]);

                glm::vec2 uv1(coord);
                glm::vec2 uv2(abs(coord.x - 1.0f), coord.y);
                glm::vec2 uv3(coord.x, abs(coord.y - 1.0f));

                glm::vec3 edge1 = pos2 - pos1;
                glm::vec3 edge2 = pos3 - pos1;
                glm::vec2 deltaUV1 = uv2 - uv1;
                glm::vec2 deltaUV2 = uv3 - uv1;

                float f = 1.0f / (deltaUV1.x * deltaUV2.y - deltaUV2.x * deltaUV1.y);

                tangent.x = f * (deltaUV2.y * edge1.x - deltaUV1.y * edge2.x);
                tangent.y = f * (deltaUV2.y * edge1.y - deltaUV1.y * edge2.y);
                tangent.z = f * (deltaUV2.y * edge1.z - deltaUV1.y * edge2.z);

                bitangent.x = f * (-deltaUV2.x * edge1.x + deltaUV1.x * edge2.x);
                bitangent.y = f * (-deltaUV2.x * edge1.y + deltaUV1.x * edge2.y);
                bitangent.z = f * (-deltaUV2.x * edge1.z + deltaUV1.x * edge2.z);
            }

            vertices.push_back({
                glm::vec3(x, y, z), 
                glm::vec3(coord, this->getLayer(z)), 
                glm::vec3(0.0f, 0.0f, 1.0f),
                glm::vec3(1.0f, 0.0f, 0.0f),
                glm::vec3(0.0f, 1.0f, 0.0f)    
            });
        }
        previousNoise = currentNoise;
        currentNoise.clear();
    }

    for (int i = 0; i < vertices.size(); i++) {
        if (vertices[i].Position.x == width - 1 || vertices[i].Position.y == height - 1)
            continue;
        indices.push_back(i);
        indices.push_back(i + width + 1);
        indices.push_back(i + 1);
        indices.push_back(i + width);
        indices.push_back(i + width + 1);
        indices.push_back(i);
    }

    textures.push_back({texture::generate2DArrayTexture(tex)});

    this->mesh = new Mesh(vertices, indices, textures);
}

double Terrain::generateNoise(int x, int y) {
    double noise = this->perlin->octave2D_01((x * 0.006), (y * 0.006), 4) +
                   (0.5  * this->perlin->octave2D_01((x * 0.012), (y * 0.012), 4)) +
                   (0.25 * this->perlin->octave2D_01((x * 0.024), (y * 0.024), 4));
    return (pow((noise), 0.9) / (1.5)) * this->noiseScale;
}

float Terrain::getLayer(double noise) {
    if (noise > 40.0)
        return 2.0f;
    if (noise > 33.0f)
        return 1.0f;
    return 0.0f;
}

float Terrain::getZOfSquare(int squareX, int squareY, double x, double y) {
    glm::vec3 A = glm::vec3(squareX, squareY, this->generateNoise(squareX, squareY));
    glm::vec3 B = glm::vec3(squareX, squareY + 50, this->generateNoise(squareX, squareY + 1));
    glm::vec3 C = glm::vec3(squareX + 50, squareY + 50, this->generateNoise(squareX + 1, squareY + 1));
    glm::vec3 D = glm::vec3(squareX + 50, squareY, this->generateNoise(squareX + 1, squareY));
    glm::vec3 r = glm::vec3(squareX + x, squareY + y, 0);

    glm::vec3 close = glm::distance(B, r) > glm::distance(D, r) ? D : B;

    glm::vec3 n = glm::cross(C - A, close - A);

    float d = glm::dot(n, A);
    float nd = n.x * r.x + n.y * r.y;
    float z = d - nd;
    return z / n.z;
}