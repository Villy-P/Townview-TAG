#ifndef __OPENGL_BASE_TERRAIN_HPP__
#define __OPENGL_BASE_TERRAIN_HPP__

#include "mesh.hpp"

#include <string>
#include <PerlinNoise.hpp>

class Terrain {
    public:
    Mesh* mesh;
    std::vector<Mesh*> meshes;
    std::vector<std::vector<double>> vertices;

    Terrain(int width, int height);

    float getZOfSquare(int squareX, int squareY, double x, double y);

    double generateNoise(int x, int y);

    private:
    void generateMesh(int w, int h);
    float getLayer(double noise);

    float noiseScale = 50.0f;
    siv::PerlinNoise* perlin;
};

#endif