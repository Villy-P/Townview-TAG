#include "core.hpp"

void core::keyboard(unsigned char key, int x, int y) {
    switch (key) {
        case 'a':
            core::camera->position -= glm::normalize(glm::cross(core::camera->forward, core::camera->up));
            break;
        case 'd':
            core::camera->position += glm::normalize(glm::cross(core::camera->forward, core::camera->up));
            break;
        case 'l':
            core::camera->position -= core::camera->backward;
            break;
        case 'o':
            core::camera->position += core::camera->backward;
            break;
        case 'z':
            core::camera->position += glm::vec3(0.0f, 0.0f, 1.0f);
            break;
        case 'x':
            core::camera->position += glm::vec3(0.0f, 0.0f, -1.0f);
            break;
    }
}