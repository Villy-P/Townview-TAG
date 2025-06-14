#ifndef __OPENGL_MODELS_MODEL_HPP__
#define __OPENGL_MODELS_MODEL_HPP__

#include "shader.hpp"
#include "mesh.hpp"
#include "vertex.hpp"

#include <string>
#include <vector>

#include <assimp/scene.h>

class Model {
    public:
    void draw(Shader& shader);

    Model(char* path, float x, float y, float z, float scale);
    std::vector<Mesh> meshes;
    std::vector<Texture> loadedTextures;
    private:
    std::string directory;

    float x;
    float y;
    float z;
    float scale;

    void loadModel(std::string path);
    void processNode(aiNode *node, const aiScene *scene);
    Mesh processMesh(aiMesh *mesh, const aiScene *scene);
    std::vector<Texture> loadMaterialTextures(aiMaterial *mat, aiTextureType type, std::string typeName);
};

#endif