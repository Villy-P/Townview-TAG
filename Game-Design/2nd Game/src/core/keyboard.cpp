#include "core.hpp"

#include <iostream>

void core::keyboard(unsigned char key, int x, int y) {
    // Keyboard Func Here
    const float scale = 1.0f;
    switch (key) {
        case 'w':
            core::player->move(core::camera->direction.x, core::camera->direction.y);
            break;
        case 's':
            core::player->move(-core::camera->direction.x, -core::camera->direction.y);
            break;
    }
}