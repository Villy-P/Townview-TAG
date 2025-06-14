#include "texture.hpp"
#include "core.hpp"

#define STB_IMAGE_IMPLEMENTATION
#include "stb_image.h"

#include <GL/glew.h>
#include <GL/glut.h>

#include <iostream>

unsigned int texture::generate2DArrayTexture(std::vector<std::string> paths) {
    glBindBuffer(GL_PIXEL_UNPACK_BUFFER, 0);
    unsigned int texture;
    glGenTextures(1, &texture);
    glBindTexture(GL_TEXTURE_2D_ARRAY, texture);
    int w;
    int h;
    int channels;
    for (int i = 0; i < paths.size(); i++) {
        auto d = stbi_load(paths[i].c_str(), &w, &h, &channels, 0);
        if (i == 0)
            glTexImage3D(GL_TEXTURE_2D_ARRAY, 0, GL_RGB8, w, h, paths.size(), 0, GL_RGB, GL_UNSIGNED_BYTE, 0);
        if (d)
            glTexSubImage3D(GL_TEXTURE_2D_ARRAY, 0, 0, 0, i, w, h, 1, GL_RGB, GL_UNSIGNED_BYTE, d);
        else {
            std::cout << "Texture failed to load at path: " << paths[i] << std::endl;
            stbi_image_free(d);
        }
    }

    glGenerateMipmap(GL_TEXTURE_2D_ARRAY);  

    glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
    glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_WRAP_S, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D_ARRAY, GL_TEXTURE_WRAP_T, GL_LINEAR);

    return texture;
}

unsigned int texture::textureFromFile(const char *path, const std::string &directory, bool gamma) {
    std::string filename = std::string(path);
    filename = directory.substr(0, directory.find_last_of("\\")) + '/' + filename;

    unsigned int textureID;
    glGenTextures(1, &textureID);

    int width;
    int height;
    int nrComponents;
    unsigned char *data = stbi_load(filename.c_str(), &width, &height, &nrComponents, 0);
    if (data) {
        GLenum format;
        if (nrComponents == 1)
            format = GL_RED;
        else if (nrComponents == 3)
            format = GL_RGB;
        else if (nrComponents == 4)
            format = GL_RGBA;

        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexImage2D(GL_TEXTURE_2D, 0, format, width, height, 0, format, GL_UNSIGNED_BYTE, data);
        glGenerateMipmap(GL_TEXTURE_2D);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        stbi_image_free(data);
    } else {
        std::cout << "Texture failed to load at path: " << path << std::endl;
        stbi_image_free(data);
    }

    return textureID;
}