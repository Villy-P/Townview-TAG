#ifndef __OPENGL_BASE_CORE_HPP__
#define __OPENGL_BASE_CORE_HPP__

#include "shader.hpp"
#include "camera.hpp"
#include "landscape.hpp"
#include "player.hpp"
#include "model.hpp"

#include <vector>
#include <iostream>

#include <GL/glew.h>
#include <GL/glut.h>

namespace core {
    void setup(int argc, char** argv);
    void run();
    void display();
    void keyboard(unsigned char key, int x, int y);
    void mouseMove(int x, int y);
    void resize(int w, int h);
    void timer(int value);

    inline int lastX;
    inline int lastY;

    inline GLuint VBO;
    inline GLuint VAO;

    inline Shader* shader;
    inline Shader* instance;
    inline Shader* skybox;
    inline Shader* sunShader;
    inline Shader* landscapeShader;
    inline Camera* camera;
    inline Model* sun;

    inline Landscape* landscape;

    inline Player* player;

    inline unsigned int cubemapTexture;
    inline unsigned int landscapeTexture;

    inline void APIENTRY GLDebugMessageCallback(GLenum source, GLenum type, GLuint id,
                            GLenum severity, GLsizei length,
                            const GLchar *msg, const void *data)
{
    std::string _source;
    std::string _type;
    std::string _severity;

    switch (source) {
        case GL_DEBUG_SOURCE_API:
        _source = "API";
        break;

        case GL_DEBUG_SOURCE_WINDOW_SYSTEM:
        _source = "WINDOW SYSTEM";
        break;

        case GL_DEBUG_SOURCE_SHADER_COMPILER:
        _source = "SHADER COMPILER";
        break;

        case GL_DEBUG_SOURCE_THIRD_PARTY:
        _source = "THIRD PARTY";
        break;

        case GL_DEBUG_SOURCE_APPLICATION:
        _source = "APPLICATION";
        break;

        case GL_DEBUG_SOURCE_OTHER:
        _source = "UNKNOWN";
        break;

        default:
        _source = "UNKNOWN";
        break;
    }

    switch (type) {
        case GL_DEBUG_TYPE_ERROR:
        _type = "ERROR";
        break;

        case GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR:
        _type = "DEPRECATED BEHAVIOR";
        break;

        case GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR:
        _type = "UDEFINED BEHAVIOR";
        break;

        case GL_DEBUG_TYPE_PORTABILITY:
        _type = "PORTABILITY";
        break;

        case GL_DEBUG_TYPE_PERFORMANCE:
        _type = "PERFORMANCE";
        break;

        case GL_DEBUG_TYPE_OTHER:
        _type = "OTHER";
        break;

        case GL_DEBUG_TYPE_MARKER:
        _type = "MARKER";
        break;

        default:
        _type = "UNKNOWN";
        break;
    }

    switch (severity) {
        case GL_DEBUG_SEVERITY_HIGH:
        _severity = "HIGH";
        break;

        case GL_DEBUG_SEVERITY_MEDIUM:
        _severity = "MEDIUM";
        break;

        case GL_DEBUG_SEVERITY_LOW:
        _severity = "LOW";
        break;

        case GL_DEBUG_SEVERITY_NOTIFICATION:
        _severity = "NOTIFICATION";
        break;

        default:
        _severity = "UNKNOWN";
        break;
    }

    printf("%d: %s of %s severity, raised from %s: %s\n",
            id, _type.c_str(), _severity.c_str(), _source.c_str(), msg);
} 
}


#endif