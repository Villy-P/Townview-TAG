#ifndef __OPENGL_ENGINE_RECT_HPP__
#define __OPENGL_ENGINE_RECT_HPP__

#include "color.hpp"
#include "obj.hpp"

namespace sparka {
    class Rect : public sparka::Obj {
        public:
        sparka::Color* color;

        Rect(float x, float y, float width, float height, sparka::Color* color);
    };
}

#endif