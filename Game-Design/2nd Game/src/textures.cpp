#include "textures.hpp"
#include "image.hpp"

#define STB_IMAGE_IMPLEMENTATION
#include "stb_image.h"

#include <GL/glew.h>
#include <GL/glut.h>

#include <iostream>

unsigned int image::generateImageTexture(std::string path, unsigned int &tex) {
    // stbi_set_flip_vertically_on_load(true);
    unsigned int texture;
    glGenTextures(1, &texture);
    glBindTexture(GL_TEXTURE_2D, texture);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE); // GL_CLAMP_TO_EDGE	
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE); // GL_CLAMP_TO_EDGE	
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, 20);
    int w;
    int h;
    int channels;
    auto data = stbi_load(path.c_str(), &w, &h, &channels, 0);
    glTexImage2D(
        GL_TEXTURE_2D, 
        0, 
        GL_RGBA, 
        w, 
        h, 
        0,
        GL_RGBA,
        GL_UNSIGNED_BYTE,
        data);
    glGenerateMipmap(GL_TEXTURE_2D);  
    stbi_image_free(data); 
    tex = texture;
    return texture; 
}

unsigned int image::generateLandscapeTexture() {
    glBindBuffer(GL_PIXEL_UNPACK_BUFFER, 0);
    unsigned int texture;
    glGenTextures(1, &texture);
    glBindTexture(GL_TEXTURE_2D_ARRAY, texture);
    int w;
    int h;
    int channels;
    auto data1 = stbi_load("assets/water.jpg", &w, &h, &channels, 0);
    auto data2 = stbi_load("assets/image.jpg", &w, &h, &channels, 0);
    auto data3 = stbi_load("assets/snow.png", &w, &h, &channels, 0);
    auto data4 = stbi_load("assets/rock.jpg", &w, &h, &channels, 0);
    glTexImage3D(GL_TEXTURE_2D_ARRAY, 0, GL_RGB8, w, h, 4, 0, GL_RGB, GL_UNSIGNED_BYTE, 0);
    glTexSubImage3D(GL_TEXTURE_2D_ARRAY, 0, 0, 0, 0, w, h, 1, GL_RGB, GL_UNSIGNED_BYTE, data1);
    glTexSubImage3D(GL_TEXTURE_2D_ARRAY, 0, 0, 0, 1, w, h, 1, GL_RGB, GL_UNSIGNED_BYTE, data2);
    glTexSubImage3D(GL_TEXTURE_2D_ARRAY, 0, 0, 0, 2, w, h, 1, GL_RGB, GL_UNSIGNED_BYTE, data3);
    glTexSubImage3D(GL_TEXTURE_2D_ARRAY, 0, 0, 0, 3, w, h, 1, GL_RGB, GL_UNSIGNED_BYTE, data4);

    glGenerateMipmap(GL_TEXTURE_2D_ARRAY);  

    glTexParameteri(GL_TEXTURE_2D_ARRAY,GL_TEXTURE_MIN_FILTER,GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D_ARRAY,GL_TEXTURE_MAG_FILTER,GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D_ARRAY,GL_TEXTURE_WRAP_S,GL_CLAMP_TO_EDGE);
    glTexParameteri(GL_TEXTURE_2D_ARRAY,GL_TEXTURE_WRAP_T,GL_CLAMP_TO_EDGE);
                
    stbi_image_free(data1);
    stbi_image_free(data2);


    return texture;
}

unsigned int texture::TextureFromFile(const char *path, const std::string &directory, bool gamma) {
    std::string filename = std::string(path);
    filename = directory.substr(0, directory.find_last_of("\\")) + '/' + filename;

    unsigned int textureID;
    glGenTextures(1, &textureID);

    int width, height, nrComponents;
    // std::cout << filename << std::endl;
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