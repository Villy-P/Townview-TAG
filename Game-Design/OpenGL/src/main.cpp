#include "core.hpp"
#include "shader.hpp"
#include "scene.hpp"
#include "player.hpp"
#include "event.hpp"

int main(int argc, char** argv) {
    sparka::core::initialize(argc, argv);

    sparka::Scene* scene1 = new sparka::Scene();
    sparka::Player* player = new sparka::Player(0.0f, 0.0f, 100.0f, 100.0f, new sparka::Color(100, 0, 100));

    sparka::Player::createEventListeners(scene1, player);

    scene1->objects.push_back(player);

    sparka::Scene::scenes.insert(std::make_pair("scene1", scene1));
    sparka::Scene::currentScene = scene1;

    sparka::core::run();
}