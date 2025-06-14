#ifndef __OPENGL_BASE_SHADER_HPP__
#define __OPENGL_BASE_SHADER_HPP__

#include <string>

#include <glm/vec4.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>

class Shader {
    public:
    unsigned int shaderProgram;

    Shader(std::string vertexFile, std::string fragmentFile);

    void use();

    void setInt(const std::string& attrib, int value);
    void setFloat(const std::string& attrib, float value);
    void setVec3(const std::string& attrib, float val1, float val2, float val3);
    void setVec3(const std::string& attrib, glm::vec3 vals);
    void setUniform1(const std::string& attrib, const float value);

    void setUniformMatrix4(const std::string& attrib, glm::mat4 value);

    void updateProjection();
    void updateView();
    void updateSkyboxView();
    void updateModel(float degrees);
};

#endif