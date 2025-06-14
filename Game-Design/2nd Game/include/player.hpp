#ifndef __OPENGL_GAME_PLAYER_HPP__
#define __OPENGL_GAME_PLAYER_HPP__

#include "mesh.hpp"

class Player {
    public:
    Player();

    void move(float x, float y);

    private:
    float x;
    float y;
    float z;

    float w;
    float h;
};

#endif