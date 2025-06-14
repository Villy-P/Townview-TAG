#include "shader.hpp"
#include "core.hpp"

#include <fstream>
#include <iostream>

#include <GL/glew.h>
#include <GL/glut.h>

sparka::Shader::Shader(std::string vertexFile, std::string fragmentFile) {
    std::string vertexCodeCurrent;
    std::string fragmentCodeCurrent;

    std::string vertexShaderContent;
    std::string fragmentShaderContent;

    std::ifstream vShaderFile(vertexFile);
    std::ifstream fShaderFile(fragmentFile);

    if (!vShaderFile.is_open())
        std::cerr << "Opening file " << vertexFile << " failed" << std::endl;
    if (!fShaderFile.is_open())
        std::cerr << "Opening file " << fragmentFile << " failed" << std::endl;

    while (getline(vShaderFile, vertexCodeCurrent))
        vertexShaderContent += vertexCodeCurrent + '\n';
    while (getline(fShaderFile, fragmentCodeCurrent))
        fragmentShaderContent += fragmentCodeCurrent + '\n';

    vShaderFile.close();
    fShaderFile.close();

    unsigned int vertexShader = glCreateShader(GL_VERTEX_SHADER);
    unsigned int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);

    const char* const* vertexShaderSource = reinterpret_cast<const char* const*>(&vertexShaderContent);
    const char* const* fragmentShaderSource = reinterpret_cast<const char* const*>(&fragmentShaderContent);
    
    glShaderSource(vertexShader, 1, vertexShaderSource, NULL);
    glShaderSource(fragmentShader, 1, fragmentShaderSource, NULL);
    glCompileShader(vertexShader);
    glCompileShader(fragmentShader);

    int vertexSuccess;
    char vertexInfoLog[512];
    glGetShaderiv(vertexShader, GL_COMPILE_STATUS, &vertexSuccess);

    if (!vertexSuccess) {
        glGetShaderInfoLog(vertexShader, 512, NULL, vertexInfoLog);
        std::cout << "There was an error while create a vertex shader:\n" << vertexInfoLog << std::endl;
    }

    int fragmentSuccess;
    char fragmentInfoLog[512];
    glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, &fragmentSuccess);

    if (!fragmentSuccess) {
        glGetShaderInfoLog(fragmentShader, 512, NULL, fragmentInfoLog);
        std::cout << "There was an error while create a fragment shader:\n" << fragmentInfoLog << std::endl;
    }

    this->shaderProgram = glCreateProgram();

    glAttachShader(this->shaderProgram, vertexShader);
    glAttachShader(this->shaderProgram, fragmentShader);
    glLinkProgram(this->shaderProgram);

    int shaderSuccess;
    char shaderInfoLog[512];
    glGetProgramiv(this->shaderProgram, GL_LINK_STATUS, &shaderSuccess);

    if (!shaderSuccess) {
        glGetShaderInfoLog(this->shaderProgram, 512, NULL, shaderInfoLog);
        std::cout << "There was an error while linking the shader:\n" << shaderInfoLog << std::endl;
    }

    glDeleteShader(vertexShader);
    glDeleteShader(fragmentShader);
}

void sparka::Shader::use() {
    glUseProgram(this->shaderProgram);
}

void sparka::Shader::setUniform1(const std::string& attrib, const float value) {
    glUniform1f(glGetUniformLocation(sparka::core::basicShader->shaderProgram, attrib.c_str()), value);
}

void sparka::Shader::setUniformMatrix4(const std::string& attrib, const float* value) {
    glUniformMatrix4fv(glGetUniformLocation(this->shaderProgram, attrib.c_str()), 1, GL_FALSE, value);
}