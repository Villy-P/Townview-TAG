#ifndef __OPENGL_ENGINE_CORE_HPP__
#define __OPENGL_ENGINE_CORE_HPP__

#include <GL/glew.h>
#include <GL/glut.h>

#include "shader.hpp"

namespace sparka {
    class core {
        public:
        static inline GLuint VBO;
        static inline GLuint VAO;
        static inline GLuint EBO;

        static inline sparka::Shader* basicShader;

        static void initialize(int argc, char** argv);
        static void run();

        private:
        static void display();
        static void timer(int value);
        static void onresize(int w, int h);
        static void keydown(unsigned char key, int x, int y);
        static void keyup(unsigned char key, int x, int y);
    };
}

#endif