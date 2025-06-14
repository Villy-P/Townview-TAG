#include "camera.hpp"

#include <iostream>

Camera::Camera(const glm::vec3& pos) {
    this->position = pos;
    this->up = glm::vec3(0.0f, 0.0f, 1.0f);
    this->forward = glm::vec3(0.0f, 0.0f, -1.0f);
    this->backward = glm::vec3(0.0f, 1.0f, 0.0f);
    this->center = this->position + this->forward;
}

glm::mat4 Camera::getProjection() {
    return glm::lookAt(this->position, this->position + this->forward, this->up);
}

void Camera::centerOn(glm::vec3 pos) {
    this->position = pos;
}