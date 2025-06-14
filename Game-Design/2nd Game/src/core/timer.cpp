#include "core.hpp"

void core::timer(int value) {
    glutPostRedisplay();
    glutTimerFunc(1000.0 / 60.0, core::timer, 0);
}