#ifndef __OPENGL_BASE_LANDSCAPE_HPP__
#define __OPENGL_BASE_LANDSCAPE_HPP__

#include "mesh.hpp"

#include <string>

class Landscape {
    public:
    Mesh* mesh;
    std::vector<Mesh*> meshes;
    std::vector<std::vector<double>> vertices;

    Landscape(int width, int height);

    float getZOfSquare(int squareX, int squareY, double x, double y);

    private:
    void generateMesh(int w, int h);

    double getNoiseLayer(double noise);

    unsigned int getTexture(double noise);
};

#endif