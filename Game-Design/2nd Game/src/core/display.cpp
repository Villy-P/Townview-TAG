#include "core.hpp"
#include "tree.hpp"
#include "skybox.hpp"

#include <iostream>

void core::display() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    glBindBuffer(GL_ARRAY_BUFFER, core::VBO);

    glDepthMask(GL_FALSE);
    core::skybox->use();
    core::skybox->updateProjection();
    core::skybox->updateSkyboxView();
    glBindVertexArray(skybox::VAO);
    glActiveTexture(GL_TEXTURE0);
    glBindTexture(GL_TEXTURE_CUBE_MAP, core::cubemapTexture);
    glDrawArrays(GL_TRIANGLES, 0, 36);
    glBindVertexArray(0);
    glDepthMask(GL_TRUE);

    core::landscapeShader->use();

    core::landscapeShader->setInt("material.diffuse", 0);
    core::landscapeShader->setVec3("light.position", glm::vec3(0, 0, 40));
    core::landscapeShader->setVec3("viewPos", core::camera->position);

    core::landscapeShader->setVec3("light.ambient", 0.2f, 0.2f, 0.2f); 
    core::landscapeShader->setVec3("light.diffuse", 0.8f, 0.8f, 0.8f);
    core::landscapeShader->setVec3("light.specular", 1.0f, 1.0f, 1.0f);

    // core::shader->setVec3("material.specular", 0.5f, 0.5f, 0.5f);
    // core::shader->setFloat("material.shininess", 64.0f);

    core::landscapeShader->updateModel(0.0f);
    // core::shader->setUniformMatrix4("model", glm::mat4(1.0f));
    core::landscapeShader->updateProjection();
    core::landscapeShader->updateView();

    glDepthFunc(GL_LESS);
    glEnable(GL_CULL_FACE);
    core::landscape->mesh->drawArray(*core::landscapeShader);

    glDisable(GL_CULL_FACE);
    core::sunShader->use();
    core::sunShader->updateProjection();
    core::sunShader->updateView();
    core::sunShader->updateModel(0.0f);
    core::sun->draw(*core::sunShader);

    core::instance->use();

    core::instance->setInt("material.diffuse", 0);
    core::instance->setVec3("light.position", glm::vec3(0, 0, 5));
    core::instance->setVec3("viewPos", core::camera->position);

    core::instance->setVec3("light.ambient", glm::vec3(0.6f)); 
    core::instance->setVec3("light.diffuse", glm::vec3(0.3f));
    core::instance->setVec3("light.specular", glm::vec3(1.0f));

    // core::instance->setVec3("material.specular", 0.5f, 0.5f, 0.5f);
    // core::instance->setFloat("material.shininess", 64.0f);

    core::instance->updateView();
    core::instance->updateProjection();
    for (unsigned int i = 0; i < tree::tree->meshes.size(); i++) {
        tree::tree->meshes[i].draw(*core::instance, tree::amount);
    }

    glutSwapBuffers();
}