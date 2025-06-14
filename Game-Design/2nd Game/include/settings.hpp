#ifndef __OPENGL_GAME_SETTINGS_HPP__
#define __OPENGL_GAME_SETTINGS_HPP__

namespace GAME {
    inline const float MODEL_DEGREE_ROTATION = 0.0f;

    inline const float CAMERA_SCALE = 1.0f;

    inline const float MOUSE_SENSITIVITY = 0.075f;
    inline const float MAX_PITCH = 89.0f;
    inline const float MIN_PITCH = -89.0f;
    inline const int CENTER_SCREEN_X = 400;
    inline const int CENTER_SCREEN_Y = 400;

    inline const int SCREEN_WIDTH = 800;
    inline const int SCREEN_HEIGHT = 800;

    inline const int SCREEN_X = 10;
    inline const int SCREEN_Y = 10;

    inline const float FPS = 1000.0 / 60.0;

    inline const float TILE_SCALE = 50.0f;
    inline const float NOISE_SCALE = 3.0f;
    inline const float SEA_LEVEL = 0.4f;
    inline const float SNOW_LEVEL = 0.85f;
    inline const float ROCK_LEVEL = 0.65f;
    inline const int MAX_PERLIN_NOISE_SEED = 10000;
    inline const int MIN_PERLIN_NOISE_SEED = 0;

    inline const float PLAYER_HEIGHT = 0.005f;

    inline const float FIELD_OF_VIEW = 100.0f;
    inline const float NEAR_PLANE = 0.001f;
    inline const float FAR_PLANE = 100000.0f;

    inline const float TREE_DOWN_LEVEL = 0.02;
    inline const float TREE_SCALE = 0.00003;
}

#endif