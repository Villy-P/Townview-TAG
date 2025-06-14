#include "core.hpp"
#include "tree.hpp"
#include "skybox.hpp"
#include "image.hpp"

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

    glGenBuffers(1, &core::VBO);
    glGenVertexArrays(1, &core::VAO);

    glBindVertexArray(core::VAO);
    glBindBuffer(GL_ARRAY_BUFFER, core::VBO);

    glutSetCursor(GLUT_CURSOR_NONE);

    core::sun = new Model("assets/sun/scene.gltf", 0, 0, 5, .1);
    tree::tree = new Model("assets/realistic_pine_tree_models/scene.gltf", 0, 0, 0, 1);
    core::camera = new Camera(glm::vec3(0, 1, 0));
    core::shader = new Shader("shader/vertex.glsl", "shader/fragment.glsl");
    core::skybox = new Shader("shader/skybox.vertex.glsl", "shader/skybox.frag.glsl");
    core::instance = new Shader("shader/instance_vsh.glsl", "shader/fragment.glsl");
    core::sunShader = new Shader("shader/sun.vertex.glsl", "shader/sun.fragment.glsl");
    core::landscapeShader = new Shader("shader/vertex.glsl", "shader/landscape.fragment.glsl");
    core::landscapeTexture = image::generateLandscapeTexture();

    std::vector<std::string> faces = {
        "assets/Skybox/left.bmp",
        "assets/Skybox/right.bmp",
        "assets/Skybox/front.bmp",
        "assets/Skybox/back.bmp",
        "assets/Skybox/top.bmp", // Y
        "assets/Skybox/bottom.bmp", // Y
    };
    core::cubemapTexture = skybox::loadSkybox(faces); 
    skybox::createSkybox();
}