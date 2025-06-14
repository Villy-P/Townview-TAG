#ifndef __OPENGL_BASE_TEXTURE_HPP__
#define __OPENGL_BASE_TEXTURE_HPP__

#include <string>

namespace texture {
    inline unsigned int landtexture;
    inline unsigned int player;

    unsigned int TextureFromFile(const char *path, const std::string &directory, bool gamma = false);
}

struct Texture {
    unsigned int id;
    std::string type;
    std::string path;
};

#endif