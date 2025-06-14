#include "shader.hpp"
#include "core.hpp"

#include <fstream>
#include <iostream>

#include <GL/glew.h>
#include <GL/glut.h>

#include <glm/mat4x4.hpp>
#include <glm/gtc/type_ptr.hpp>

Shader::Shader(std::string vertexFile, std::string fragmentFile) {
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
        std::cout << "There was an error while create a vertex shader:" << vertexFile << "\n" << vertexInfoLog << std::endl;
    }

    int fragmentSuccess;
    char fragmentInfoLog[512];
    glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, &fragmentSuccess);

    if (!fragmentSuccess) {
        glGetShaderInfoLog(fragmentShader, 512, NULL, fragmentInfoLog);
        std::cout << "There was an error while create a fragment shader:" << fragmentFile << "\n" << fragmentInfoLog << std::endl;
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

void Shader::use() {
    glUseProgram(this->shaderProgram);
}

void Shader::setInt(const std::string& attrib, int value) {
    glUniform1i(glGetUniformLocation(this->shaderProgram, attrib.c_str()), value); 
}

void Shader::setFloat(const std::string& attrib, float value) {
    glUniform1f(glGetUniformLocation(this->shaderProgram, attrib.c_str()), value); 
}

void Shader::setVec3(const std::string& attrib, float val1, float val2, float val3) {
    glUniform3f(glGetUniformLocation(this->shaderProgram, attrib.c_str()), val1, val2, val3);
}

void Shader::setVec3(const std::string& attrib, glm::vec3 vals) {
    glUniform3f(glGetUniformLocation(this->shaderProgram, attrib.c_str()), vals.x, vals.y, vals.z);
}

void Shader::setUniform1(const std::string& attrib, const float value) {
    glUniform1f(glGetUniformLocation(this->shaderProgram, attrib.c_str()), value);
}

void Shader::setUniformMatrix4(const std::string& attrib, glm::mat4 value) {
    glUniformMatrix4fv(glGetUniformLocation(this->shaderProgram, attrib.c_str()), 1, GL_FALSE, glm::value_ptr(value));
}

void Shader::updateModel(float degrees) {
    glm::mat4 model = glm::mat4(1.0f);
    model = glm::rotate(model, glm::radians(degrees), glm::vec3(1.0f, 0.0f, 0.0f)); 
    this->setUniformMatrix4("model", model);
}

void Shader::updateProjection() {
    const float w = (float)glutGet(GLUT_WINDOW_WIDTH);
    const float h = (float)glutGet(GLUT_WINDOW_HEIGHT);
    const float rotation = glm::radians(100.0f);
    glm::mat4 perspective = glm::perspective(rotation, w / h, 0.001f, 100000.0f);
    this->setUniformMatrix4("projection", perspective);
}

void Shader::updateSkyboxView() {
    glm::mat4 view = glm::mat4(glm::mat3(core::camera->getProjection()));  
    this->setUniformMatrix4("view", view);
}

void Shader::updateView() {
    glm::mat4 view = core::camera->getProjection();
    this->setUniformMatrix4("view", view);
}