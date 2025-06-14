#include "core.hpp"

#include <iostream>

void core::run() {
    glutTimerFunc(0, core::timer, 0);
    glutReshapeFunc(core::resize);
    glutKeyboardFunc(core::keyboard);
    glutDisplayFunc(core::display);
    glutPassiveMotionFunc(core::mouseMove);
    glDebugMessageCallback(core::GLDebugMessageCallback, NULL);
    glutMainLoop();
}