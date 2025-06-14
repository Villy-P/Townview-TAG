#ifndef __OPENGL_ENGINE_SCENE_HPP__
#define __OPENGL_ENGINE_SCENE_HPP__

#include <vector>
#include <map>
#include <string>
#include <functional>

#include "obj.hpp"
#include "event.hpp"

namespace sparka {
    class Scene {
        public:
        std::vector<float> vertices;
        std::vector<sparka::Obj*> objects;
        std::multimap<sparka::event::EVENT_LISTENER, std::function<void(sparka::event::Event)>> eventListeners;

        void updateVertexPositions();
        void render();

        inline static std::map<std::string, sparka::Scene*> scenes;
        inline static sparka::Scene* currentScene;
    };
}

#endif