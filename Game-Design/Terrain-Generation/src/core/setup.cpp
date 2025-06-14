#include "core.hpp"
#include "skybox.hpp"
#include "tree.hpp"

#include <string>
#include <iostream>

void core::setup(int argc, char** argv) {
    const int screenWidth = 800;
    const int screenHeight = 800;
    const int x = 10;
    const int y = 10;
    core::lastX = screenWidth / 2;
    core::lastY = screenHeight / 2;
    const std::string title = "OpenGL Base";

    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH | GLUT_MULTISAMPLE);
    glutInitWindowPosition(x, y);
    glutInitWindowSize(screenWidth, screenHeight);
    glViewport(0, 0, screenWidth, screenHeight);
    glutCreateWindow(title.c_str());
    glClearColor(0, 0, 0, 1);
    glClear(GL_COLOR_BUFFER_BIT);
    glewInit();

    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, GL_ONE, GL_ONE);
    glEnable(GL_DEPTH_TEST);  
    glEnable(GL_DEPTH_CLAMP);
    glDepthFunc(GL_LESS);
    glEnable(GL_CULL_FACE);
    glCullFace(GL_BACK);
    glEnable(GL_MULTISAMPLE);
    glEnable(GL_DEBUG_OUTPUT);
    glEnable(GL_DEBUG_OUTPUT_SYNCHRONOUS);

    glutSetCursor(GLUT_CURSOR_NONE);

    core::shader = new Shader("shader/vertex.glsl", "shader/fragment.glsl");
    core::skybox = new Shader("shader/skybox.vert.glsl", "shader/skybox.frag.glsl");
    core::instance = new Shader("shader/instance.vert.glsl", "shader/instance.frag.glsl");

    std::vector<std::string> faces = {
        "assets/Skybox/left.bmp",
        "assets/Skybox/right.bmp",
        "assets/Skybox/front.bmp",
        "assets/Skybox/back.bmp",
        "assets/Skybox/top.bmp",
        "assets/Skybox/bottom.bmp",
    };
    texture::skyboxTexture = skybox::loadSkybox(faces); 
    skybox::createSkybox();

    core::terrain = new Terrain(200, 200);

    texture::grassNormalTexture = texture::textureFromFile("wispy-grass-meadow_normal-ogl.png", "assets/whispy-grass-meadow-unity", 0);
    texture::rockNormalTexture  = texture::textureFromFile("Rough-rockface1_Normal.png",        "assets/Rough-rockface1-Unity",     0);
    texture::snowNormalTexture  = texture::textureFromFile("snow-packed12-Normal-ogl.png",      "assets/snow-packed12-Unity2-1",    0);

    texture::grassHeightTexture = texture::textureFromFile("wispy-grass-meadow_height.png",     "assets/whispy-grass-meadow-unity", 0);
    texture::rockHeightTexture  = texture::textureFromFile("Rough-rockface1_Height.png",        "assets/Rough-rockface1-Unity",     0);
    texture::snowHeightTexture  = texture::textureFromFile("snow-packed12-Height.png",          "assets/snow-packed12-Unity2-1",    0);

    tree::tree = new Model("assets/realistic_pine_tree_models/scene.gltf", 0, 0, 0, 1);
    tree::treeSetup();
}