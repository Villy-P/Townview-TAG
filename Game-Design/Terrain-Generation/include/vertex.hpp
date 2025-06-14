#ifndef __OPENGL_BASE_VERTEX_HPP__
#define __OPENGL_BASE_VERTEX_HPP__

#include <glm/vec2.hpp>
#include <glm/vec3.hpp>

#include <string>

struct Vertex {
    glm::vec3 Position;
    glm::vec3 TexCoords;
    glm::vec3 Normal;
    glm::vec3 Tangent;
    glm::vec3 BiTangent;
};

#endif