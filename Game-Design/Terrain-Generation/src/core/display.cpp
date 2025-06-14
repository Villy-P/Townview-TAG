#include "core.hpp"
#include "skybox.hpp"
#include "tree.hpp"

#include <iostream>

void core::display() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    glCullFace(GL_BACK);
    glDepthMask(GL_FALSE);
    core::skybox->use();
    core::skybox->updateProjection();
    core::skybox->updateSkyboxView();
    glBindVertexArray(skybox::VAO);
    glActiveTexture(GL_TEXTURE0);
    glBindTexture(GL_TEXTURE_CUBE_MAP, texture::skyboxTexture);
    glDrawArrays(GL_TRIANGLES, 0, 36);
    glBindVertexArray(0);
    glDepthMask(GL_TRUE);

    core::shader->use();

    core::shader->setInt("material.diffuse", 0);
    core::shader->setVec3("light.direction", glm::vec3(10.0f, 10.0f, 10.0f));
    core::shader->setVec3("viewPos", core::camera->position);

    core::shader->setVec3("light.ambient", 0.8f, 0.8f, 0.8f); 
    core::shader->setVec3("light.diffuse", 0.8f, 0.8f, 0.8f);
    core::shader->setVec3("light.specular", 1.0f, 1.0f, 1.0f);

    core::shader->setVec3("material.specular", 0.1f, 0.1f, 0.1f);
    core::shader->setFloat("material.shininess", 5.0f);

    core::shader->setInt("terrainMaps.grassNormal", texture::grassNormalTexture);
    core::shader->setInt("terrainMaps.rockNormal", texture::rockNormalTexture);
    core::shader->setInt("terrainMaps.snowNormal", texture::snowNormalTexture);

    core::shader->setInt("terrainMaps.grassHeight", texture::grassHeightTexture);
    core::shader->setInt("terrainMaps.rockHeight", texture::rockHeightTexture);
    core::shader->setInt("terrainMaps.snowHeight", texture::snowHeightTexture);

    core::shader->updateModel(0.0f);
    core::shader->updateProjection();
    core::shader->updateView();

    core::terrain->mesh->drawArray(*core::shader);


    glCullFace(GL_FRONT);
    core::instance->use();

    core::instance->setInt("material.diffuse", 0);
    core::instance->setVec3("light.direction", glm::vec3(10.0f, 10.0f, 10.0f));
    core::instance->setVec3("viewPos", core::camera->position);

    core::instance->setVec3("light.ambient", glm::vec3(0.8f)); 
    core::instance->setVec3("light.diffuse", glm::vec3(0.8f));
    core::instance->setVec3("light.specular", glm::vec3(1.0f));

    core::instance->setVec3("material.specular", glm::vec3(0.0f));
    core::instance->setFloat("material.shininess", 32.0f);

    core::instance->updateView();
    core::instance->updateProjection();
    for (unsigned int i = 0; i < tree::tree->meshes.size(); i++)
        tree::tree->meshes[i].draw(*core::instance, tree::amount);

    glutSwapBuffers();
}