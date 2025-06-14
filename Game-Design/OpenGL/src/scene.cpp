#include "scene.hpp"
#include "core.hpp"

#include <GL/glew.h>
#include <GL/glut.h>

#include <cppconsole/console.hpp>

void sparka::Scene::render() {
    this->vertices.clear();
    for (auto& v : this->objects) {
        const std::vector<float> objVertices = v->getVertices();
        this->vertices.insert(this->vertices.end(), objVertices.begin(), objVertices.end());
    }
    glBindBuffer(GL_ARRAY_BUFFER, sparka::core::VBO);
    glBufferData(GL_ARRAY_BUFFER, this->vertices.size() * sizeof(this->vertices), this->vertices.data(), GL_DYNAMIC_DRAW);

    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 6 * sizeof(float), 0);
    glEnableVertexAttribArray(0);

    glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 6 * sizeof(float), (void*)(3 * sizeof(float)));
    glEnableVertexAttribArray(1);
    
    glBindBuffer(GL_ARRAY_BUFFER, 0);

    glDrawArrays(GL_TRIANGLES, 0, this->vertices.size() / 6);
}