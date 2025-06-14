#include "core.hpp"

void core::mouseMove(int x, int y) {
    float xoffset = x - core::lastX;
    float yoffset = core::lastY - y; // reversed since y-coordinates range from bottom to top
    core::lastX = x;
    core::lastY = y;

    const float sensitivity = 0.075f;
    x *= sensitivity;
    y *= sensitivity;

    core::camera->yaw += xoffset;
    core::camera->pitch += yoffset;

    if(core::camera->pitch > 89.0f)
        core::camera->pitch =  89.0f;
    if(core::camera->pitch < -89.0f)
        core::camera->pitch = -89.0f;

    core::camera->direction.x = cos(glm::radians(core::camera->yaw)) * cos(glm::radians(core::camera->pitch));
    core::camera->direction.z = sin(glm::radians(core::camera->pitch));
    core::camera->direction.y = -sin(glm::radians(core::camera->yaw)) * cos(glm::radians(core::camera->pitch));
    core::camera->forward = glm::normalize(core::camera->direction);

    glutWarpPointer(400, 400);
    core::lastX = 400;
    core::lastY = 400;
}