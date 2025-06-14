#ifndef __OPENGL_BASE_CORE_HPP__
#define __OPENGL_BASE_CORE_HPP__

#include "camera.hpp"
#include "shader.hpp"
#include "terrain.hpp"

#include <GL/glew.h>
#include <GL/glut.h>

#include <glm/glm.hpp>

namespace core {
    void setup(int argc, char** argv);
    void run();
    void display();
    void keyboard(unsigned char key, int x, int y);
    void mouseMove(int x, int y);
    void resize(int w, int h);
    void timer(int value);
    void APIENTRY GLDebugMessageCallback(GLenum source, GLenum type, GLuint id,
                            GLenum severity, GLsizei length,
                            const GLchar *msg, const void *data);

    inline int lastX;
    inline int lastY;

    inline Camera* camera = new Camera(glm::vec3(0.0f));
    inline Shader* shader;
    inline Shader* skybox;
    inline Shader* instance;
    inline Shader* shadow;
    inline Terrain* terrain;
}

#endif