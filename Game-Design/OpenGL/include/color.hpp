#ifndef __OPENGL_ENGINE_COLOR_HPP__
#define __OPENGL_ENGINE_COLOR_HPP__

namespace sparka {
    class Color {
        public:
        unsigned char r;
        unsigned char g;
        unsigned char b;

        Color(unsigned char r, unsigned char g, unsigned char b);
    };
}

#endif