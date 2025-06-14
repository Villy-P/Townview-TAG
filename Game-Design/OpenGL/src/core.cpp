#include "core.hpp"
#include "rect.hpp"
#include "color.hpp"
#include "scene.hpp"
#include "input.hpp"

#include <string>
#include <iostream>

#include <boost/property_tree/json_parser.hpp>
#include <boost/property_tree/ptree.hpp>

#include <glm/vec4.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>

#include <cpputils/random.hpp>
#include <cpputils/set.hpp>

void sparka::core::initialize(int argc, char** argv) {
    boost::property_tree::ptree pt;
    boost::property_tree::read_json("sparkaconfig.json", pt);
    const std::string title = pt.get<std::string>("title");
    const int width = pt.get<int>("width");
    const int height = pt.get<int>("height");
    const int x = pt.get<int>("x");
    const int y = pt.get<int>("y");
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB);
    glutInitWindowPosition(x, y);
    glutInitWindowSize(width, height);
    glutCreateWindow(title.c_str());
    glutIgnoreKeyRepeat(3);
    GLenum err = glewInit();
    if (err != GLEW_OK) {
        std::cerr << "GLEW error: " << glewGetErrorString(err) << std::endl;
        return;
    }
    glClearColor(1, 0, 0, 1);
    glClear(GL_COLOR_BUFFER_BIT);

    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    glGenBuffers(1, &sparka::core::VBO);
    glGenVertexArrays(1, &sparka::core::VAO);

    glBindVertexArray(sparka::core::VAO);
    glBindBuffer(GL_ARRAY_BUFFER, sparka::core::VBO);

    sparka::core::basicShader = new sparka::Shader("shaders/vertex.glsl", "shaders/fragment.glsl");
}

void sparka::core::run() {
    glutReshapeFunc(sparka::core::onresize);
    glutTimerFunc(0, sparka::core::timer, 0);
    glutDisplayFunc(sparka::core::display);
    glutKeyboardFunc(sparka::core::keydown);
    glutKeyboardUpFunc(sparka::core::keyup);
    glutMainLoop();
}

void sparka::core::display() {
    glClear(GL_COLOR_BUFFER_BIT);

    glBindBuffer(GL_ARRAY_BUFFER, sparka::core::VBO);

    sparka::core::basicShader->use();

    sparka::core::basicShader->setUniformMatrix4("projection", glm::value_ptr(glm::ortho(0.0f, (float)glutGet(GLUT_WINDOW_WIDTH), (float)glutGet(GLUT_WINDOW_HEIGHT), 0.0f, -1.0f, 1.0f)));
    sparka::core::basicShader->setUniformMatrix4("modelview", glm::value_ptr(glm::mat4(1.0f)));

    sparka::Scene::currentScene->render();

    glutSwapBuffers();
}

void sparka::core::onresize(int w, int h) {
    glViewport(0, 0, w, h);
    sparka::core::basicShader->setUniformMatrix4("projection", glm::value_ptr(glm::ortho(0.0f, (float)w, (float)h, 0.0f, -1.0f, 1.0f)));
    sparka::core::basicShader->setUniformMatrix4("modelview", glm::value_ptr(glm::mat4(1.0f)));
}

void sparka::core::timer(int value) {
    for (auto& i : sparka::Scene::currentScene->objects)
        i->update();
    for (auto& i : sparka::Scene::currentScene->eventListeners) {
        if (i.first != sparka::event::EVENT_LISTENER::KEYISDOWN)
            continue;
        for (auto& j : sparka::input::keys) {
            sparka::event::Event e;
            e.key = j;
            i.second(e);
        }
    }
    glutPostRedisplay();
    glutTimerFunc(1000.0 / 60.0, sparka::core::timer, 0);
}

void sparka::core::keydown(unsigned char key, int x, int y) {
    for (auto& i : sparka::Scene::currentScene->eventListeners) {
        if (i.first != sparka::event::EVENT_LISTENER::KEYPRESS)
            continue;
        sparka::event::Event e;
        e.key = key;
        i.second(e);
    }
    sparka::input::keys.insert(key);
}

void sparka::core::keyup(unsigned char key, int x, int y) {
    for (auto& i : sparka::Scene::currentScene->eventListeners) {
        if (i.first != sparka::event::EVENT_LISTENER::KEYUP)
            continue;
        sparka::event::Event e;
        e.key = key;
        i.second(e);
    }
    sparka::input::keys.erase(key);
}