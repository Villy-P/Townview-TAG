#include "player.hpp"
#include "core.hpp"
#include "image.hpp"

#include <iostream>

#include <glm/gtx/string_cast.hpp>

Player::Player() {
    this->x = 100.0f;
    this->y = 100.0f;
    this->z = 0.0f;

    this->w = 5.0f;
    this->h = 5.0f;
}

void Player::move(float x, float y) {
    float width = (float)glutGet(GLUT_WINDOW_WIDTH);
    this->x += x;
    this->y += y;
    int squareXOff = (int)(this->x / 50.0);
    int squareYOff = (int)(this->y / 50.0);
    double squareX = fmod(this->x, 50);
    double squareY = fmod(this->y, 50);
    this->z = (core::landscape->getZOfSquare(squareXOff, squareYOff, squareX, squareY) * 3.0f);
    if (this->z < 0.4f)
        this->z = 0.4f;
    core::camera->centerOn(glm::vec3(this->x / width, this->y / width, this->z + .005));
}