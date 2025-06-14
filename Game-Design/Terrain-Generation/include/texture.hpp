#ifndef __OPENGL_BASE_TEXTURE_HPP__
#define __OPENGL_BASE_TEXTURE_HPP__

#include <vector>
#include <string>

namespace texture {
    inline unsigned int skyboxTexture;

    inline unsigned int grassNormalTexture;
    inline unsigned int rockNormalTexture;
    inline unsigned int snowNormalTexture;

    inline unsigned int grassHeightTexture;
    inline unsigned int rockHeightTexture;
    inline unsigned int snowHeightTexture;

    unsigned int generate2DArrayTexture(std::vector<std::string> paths);
    unsigned int textureFromFile(const char *path, const std::string &directory, bool gamma);
}

struct Texture {
    unsigned int id;
    std::string type;
    std::string path;
};

#endif