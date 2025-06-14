#ifndef __OPENGL_BASE_MESH_HPP__
#define __OPENGL_BASE_MESH_HPP__

#include "vertex.hpp"
#include "texture.hpp"
#include "shader.hpp"

#include <vector>

class Mesh {
    public:
    std::vector<Vertex> vertices;
    std::vector<unsigned int> indices;
    std::vector<Texture> textures;

    Mesh(std::vector<Vertex> vertices, std::vector<unsigned int> indices, std::vector<Texture> textures);

    void updateVertices(std::vector<Vertex> vertices);
    void draw(Shader &shader);
    void draw(Shader &shader, int amount);
    void drawArray(Shader &shader);
    unsigned int VAO;
    private:
    unsigned int VBO;
    unsigned int EBO;

    void setupMesh();
};  

#endif