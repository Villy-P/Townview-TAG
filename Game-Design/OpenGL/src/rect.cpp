#include "rect.hpp"
#include "core.hpp"
#include "shader.hpp"

#include <iostream>

#include <GL/glew.h>
#include <GL/glut.h>

sparka::Rect::Rect(float x, float y, float width, float height, sparka::Color* color) {
    this->x = x;
    this->y = y;
    this->width = width;
    this->height = height;
    this->color = color;
}