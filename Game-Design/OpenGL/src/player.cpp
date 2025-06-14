#include "player.hpp"
#include "core.hpp"
#include "scene.hpp"
#include "input.hpp"

#include <GL/glew.h>
#include <GL/glut.h>

#include <iostream>

#include <cpputils/set.hpp>

sparka::Player::Player(float x, float y, float width, float height, sparka::Color* color) {
    this->x = x;
    this->y = y;
    this->width = width;
    this->height = height;
    this->color = color;
}

std::vector<float> sparka::Player::getVertices() {
    const float r = this->color->r / 255.0f;
    const float b = this->color->b / 255.0f;
    const float g = this->color->g / 255.0f;
    return {
        this->x, this->y, 0.0f, r, g, b,
        this->x + this->width, this->y, 0.0f, r, g, b,
        this->x + this->width, this->y + this->height, 0.0f, r, g, b,
        this->x + this->width, this->y + this->height, 0.0f, r, g, b,
        this->x, this->y + this->height, 0.0f, r, g, b,
        this->x, this->y, 0.0f, r, g, b,
    };
}

void sparka::Player::update() {
    this->dy += 0.5;
    this->x += this->dx;
    this->y += this->dy;
    if (this->y + this->height > glutGet(GLUT_WINDOW_HEIGHT)) {
        this->y = glutGet(GLUT_WINDOW_HEIGHT) - this->height;
        this->jumps = 2;
    }
}

void sparka::Player::createEventListeners(sparka::Scene* scene, sparka::Player* player) {
    scene->eventListeners.insert({sparka::event::EVENT_LISTENER::KEYPRESS, [player](sparka::event::Event evt){
        if (evt.key == ' ' && player->jumps > 0) {
            player->dy = -12.0;
            player->jumps--;
        }
        if (evt.key == 's')
            player->height /= 2;
    }});
    scene->eventListeners.insert({sparka::event::EVENT_LISTENER::KEYUP, [player](sparka::event::Event evt){
        if (evt.key == 's')
            player->height *= 2;
    }});
    scene->eventListeners.insert({sparka::event::EVENT_LISTENER::KEYISDOWN, [player](sparka::event::Event evt){
        if (evt.key == 'a')
            player->x -= 10;
        if (evt.key == 'd')
            player->x += 10;
    }});
}