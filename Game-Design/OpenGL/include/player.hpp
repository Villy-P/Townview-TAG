#ifndef __OPENGL_ENGINE_PLAYER_HPP__
#define __OPENGL_ENGINE_PLAYER_HPP__

#include "obj.hpp"
#include "color.hpp"
#include "scene.hpp"

namespace sparka {
    class Player : public sparka::Obj {
        public:
        sparka::Color* color;

        float dx = 0.0f;
        float dy = 0.0f;

        void update() override;
        std::vector<float> getVertices() override;

        bool onground = false;

        int jumps = 2;

        Player(float x, float y, float width, float height, sparka::Color* color);

        static void createEventListeners(sparka::Scene* scene, sparka::Player* player);
    };
}

#endif