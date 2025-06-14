#ifndef __OPENGL_BASE_CAMERA_HPP__
#define __OPENGL_BASE_CAMERA_HPP__

#include <glm/vec3.hpp>
#include <glm/mat4x4.hpp>
#include <glm/gtc/matrix_transform.hpp>

#include <GL/glew.h>
#include <GL/glut.h>

class Camera {
    public:
    glm::vec3 position;
    glm::vec3 up;
    glm::vec3 forward;
    glm::vec3 backward;
    glm::vec3 center;

    glm::vec3 direction;

    float yaw = -90.0f;
    float pitch = 0.0f;

    glm::mat4 getProjection();

    void centerOn(glm::vec3 pos);

    Camera(const glm::vec3& pos);
};

#endif