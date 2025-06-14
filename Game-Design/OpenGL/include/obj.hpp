#ifndef __OPENGL_ENGINE_SPARKA_OBJ_HPP__
#define __OPENGL_ENGINE_SPARKA_OBJ_HPP__

#include <vector>

namespace sparka {
    class Obj {
        public:
        float x;
        float y;
        float width;
        float height;

        virtual void update() = 0;
        virtual std::vector<float> getVertices() = 0;
    };
}

#endif