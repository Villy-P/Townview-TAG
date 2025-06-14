#ifndef __OPENGL_ENGINE_SHADER_HPP__
#define __OPENGL_ENGINE_SHADER_HPP__

#include <string>

namespace sparka {
    class Shader {
        public:
        unsigned int shaderProgram;

        Shader(std::string vertexFile, std::string fragmentFile);

        void use();

        void setUniform1(const std::string& attrib, const float value);

        void setUniformMatrix4(const std::string& attrib, const float* value);
    };
}

#endif