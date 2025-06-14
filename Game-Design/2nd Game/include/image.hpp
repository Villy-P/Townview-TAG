#ifndef __OPENGL_BASE_IMAGE_HPP__
#define __OPENGL_BASE_IMAGE_HPP__

#include <string>

namespace image {
    unsigned int generateImageTexture(std::string path, unsigned int &tex);
    unsigned int generateLandscapeTexture();
}

#endif