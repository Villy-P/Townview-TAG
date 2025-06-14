#include "landscape.hpp"
#include "core.hpp"

#include <random>
#include <iostream>

#include <PerlinNoise.hpp>

#include <GL/glew.h>
#include <GL/glut.h>

Landscape::Landscape(int width, int height) {
    this->generateMesh(width, height);
}

void Landscape::generateMesh(int w, int h) {
    int texture_units;
    glGetIntegerv(GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS, &texture_units);

    std::random_device rd;
    std::mt19937 gen(rd());
    std::uniform_int_distribution<> distr(0, 1000);
    const siv::PerlinNoise perlin{ distr(gen) };

    std::vector<Vertex> vertices;
    std::vector<unsigned int> indices;
    std::vector<Texture> textures;

    vertices.reserve(w * h);

    float width = (float)glutGet(GLUT_WINDOW_WIDTH);
    int currentIndex = 0;

    const float scale = 50.0f;
    const float noiseScale = 3.0f;

    std::vector<std::vector<double>> noise;

    noise.reserve(w);

    for (double y = 0; y < w; y++) {
        std::vector<double> vec1;
        vec1.reserve(h);
        for (double x = 0; x < h; x++) {
            double noise = perlin.octave2D_01((x * 0.006), (y * 0.006), 4) +
                           (0.5 * perlin.octave2D_01((x * 0.012), (y * 0.012), 4)) +
                           (0.25 * perlin.octave2D_01((x * 0.024), (y * 0.024), 4));
            vec1.push_back(pow((noise), .9) / (1.5));
        }
        noise.push_back(vec1);
    }

    if (noise[0][0] < 0.5)
        this->generateMesh(w, h);

    this->vertices = noise;

    for (int i = 0; i < w - 1; i++) {
        for (int j = 0; j < h - 1; j++) {
            const double iPosition = i * scale;
            const double jPosition = j * scale;

            const double offX = 0.0;
            const double offY = 0.0;

            const double imageOff = 1.0;

            const double layer = this->getNoiseLayer(noise[i][j]);

            if (noise[i][j] < 0.4 && noise[i + 1][j] < 0.4 && noise[i + 1][j + 1] < 0.4 && noise[i][j + 1] < 0.4)
                continue;

            double v[] = {
                (iPosition) , (jPosition), noise[i][j] * noiseScale, offX, offY + imageOff, layer,
                (iPosition + scale), (jPosition), noise[i + 1][j] * noiseScale, offX + imageOff, offY + imageOff, layer,
                (iPosition + scale), (jPosition + scale), noise[i + 1][j + 1] * noiseScale, offX + imageOff, offY, layer,
                (iPosition), (jPosition + scale), noise[i][j + 1] * noiseScale, offX, offY, layer,
            };

            for (int i = 0; i < 24; i += 6) {
                Vertex vertex;

                glm::vec3 vector;
                vector.x = v[i] / width;
                vector.y = v[i + 1] / width;
                vector.z = v[i + 2];
                glm::vec3 texture;
                texture.x = v[i + 3];
                texture.y = v[i + 4];
                texture.z = v[i + 5];
                glm::vec3 normals = glm::vec3(0.0f, 0.0f, vector.z + 1.0f);

                vertex.Position = vector;
                vertex.TexCoords = texture;
                vertex.Normal = normals;

                vertices.push_back(vertex);
            }
            indices.push_back(currentIndex);
            indices.push_back(currentIndex + 1);
            indices.push_back(currentIndex + 2);
            indices.push_back(currentIndex + 2);
            indices.push_back(currentIndex + 3);
            indices.push_back(currentIndex);
            currentIndex += 4;
        }
    }
    vertices.push_back({glm::vec3(0.0f, 0.0f, 0.4f * noiseScale), glm::vec3(0.0f, 0.0f, 0.0f)});
    vertices.push_back({glm::vec3(w / 10, 0.0f, 0.4f * noiseScale), glm::vec3(1.0f, 0.0f, 0.0f)});
    vertices.push_back({glm::vec3(w / 10, w / 10, 0.4f * noiseScale), glm::vec3(1.0f, 1.0f, 0.0f)});
    vertices.push_back({glm::vec3(0.0f, w / 10, 0.4f * noiseScale), glm::vec3(0.0f, 1.0f, 0.0f)});
    indices.push_back(currentIndex);
    indices.push_back(currentIndex + 1);
    indices.push_back(currentIndex + 2);
    indices.push_back(currentIndex + 2);
    indices.push_back(currentIndex + 3);
    indices.push_back(currentIndex);

    textures.push_back({core::landscapeTexture});
    this->mesh = new Mesh(vertices, indices, textures);
}

double Landscape::getNoiseLayer(double noise) {
    if (noise > 0.85)
        return 2.0;
    if (noise > 0.65)
        return 3.0;
    if (noise < 0.3)
        return 0.0;
    return 1.0;
}

unsigned int Landscape::getTexture(double noise) {
    return texture::landtexture;
}

float Landscape::getZOfSquare(int squareX, int squareY, double x, double y) {
    glm::vec3 A = glm::vec3(squareX, squareY, this->vertices[squareX][squareY]);
    glm::vec3 B = glm::vec3(squareX, squareY + 50, this->vertices[squareX][squareY + 1]);
    glm::vec3 C = glm::vec3(squareX + 50, squareY + 50, this->vertices[squareX + 1][squareY + 1]);
    glm::vec3 D = glm::vec3(squareX + 50, squareY, this->vertices[squareX + 1][squareY]);
    glm::vec3 r = glm::vec3(squareX + x, squareY + y, 0);

    glm::vec3 close = glm::distance(B, r) > glm::distance(D, r) ? D : B;

    glm::vec3 n = glm::cross(C - A, close - A);

    float d = glm::dot(n, A);
    float nd = n.x * r.x + n.y * r.y;
    float z = d - nd;
    return z / n.z;
}