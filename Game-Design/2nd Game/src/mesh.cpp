#include "mesh.hpp"

#include <iostream>

#include <GL/glew.h>
#include <GL/glut.h>

Mesh::Mesh(std::vector<Vertex> vertices, std::vector<unsigned int> indices, std::vector<Texture> textures) {
    this->vertices = vertices;
    this->indices = indices;
    this->textures = textures; 

    this->setupMesh();
}

void Mesh::setupMesh() {
    glGenVertexArrays(1, &this->VAO);
    glGenBuffers(1, &this->VBO);
    glGenBuffers(1, &this->EBO);
  
    glBindVertexArray(this->VAO);
    glBindBuffer(GL_ARRAY_BUFFER, this->VBO);

    glBufferData(GL_ARRAY_BUFFER, this->vertices.size() * sizeof(Vertex), &this->vertices[0], GL_STATIC_DRAW);  

    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this->EBO);
    glBufferData(GL_ELEMENT_ARRAY_BUFFER, this->indices.size() * sizeof(unsigned int), &this->indices[0], GL_STATIC_DRAW);
    glEnableVertexAttribArray(0);	
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, sizeof(Vertex), (void*)0);

    glEnableVertexAttribArray(1);	
    glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, sizeof(Vertex), (void*)offsetof(Vertex, TexCoords));

    glEnableVertexAttribArray(2);	
    glVertexAttribPointer(2, 3, GL_FLOAT, GL_FALSE, sizeof(Vertex), (void*)offsetof(Vertex, Normal));

    glBindVertexArray(0);
} 

void Mesh::draw(Shader& shader) {
    unsigned int diffuse = 1;
    unsigned int specular = 1;
    for (int i = 0; i < this->textures.size(); i++) {
        glActiveTexture(GL_TEXTURE0 + i);
        std::string number;
        std::string name = textures[i].type;
        if (name == "texture_diffuse")
            number = std::to_string(diffuse++);
        else if (name == "texture_specular")
            number = std::to_string(specular++);

        shader.setInt((name + number).c_str(), i);
        glBindTexture(GL_TEXTURE_2D, textures[i].id);
    }
    glActiveTexture(GL_TEXTURE0);
    glBindVertexArray(VAO);
    glDrawElements(GL_TRIANGLES, this->indices.size(), GL_UNSIGNED_INT, 0);
    glBindVertexArray(0);
}

void Mesh::drawArray(Shader& shader) {
    unsigned int diffuse = 1;
    unsigned int specular = 1;
    for (int i = 0; i < this->textures.size(); i++) {
        glActiveTexture(GL_TEXTURE0 + i);
        std::string number;
        std::string name = textures[i].type;
        if (name == "texture_diffuse")
            number = std::to_string(diffuse++);
        else if (name == "texture_specular")
            number = std::to_string(specular++);

        shader.setInt((name + number).c_str(), i);
        glBindTexture(GL_TEXTURE_2D_ARRAY, textures[i].id);
    }
    glActiveTexture(GL_TEXTURE0);
    glBindVertexArray(VAO);
    glDrawElements(GL_TRIANGLES, this->indices.size(), GL_UNSIGNED_INT, 0);
    glBindVertexArray(0);
}

void Mesh::draw(Shader &shader, int amount) {
    unsigned int diffuse = 1;
    unsigned int specular = 1;
    for (int i = 0; i < this->textures.size(); i++) {
        glActiveTexture(GL_TEXTURE0 + i);
        std::string number;
        std::string name = textures[i].type;
        if (name == "texture_diffuse")
            number = std::to_string(diffuse++);
        else if (name == "texture_specular")
            number = std::to_string(specular++);

        shader.setInt((name + number).c_str(), i);
        glBindTexture(GL_TEXTURE_2D, textures[i].id);
        glEnable(GL_TEXTURE_2D);
    }
    glActiveTexture(GL_TEXTURE0);
    glBindVertexArray(VAO);
    glDrawElementsInstanced(GL_TRIANGLES, this->indices.size(), GL_UNSIGNED_INT, 0, amount);
    glBindVertexArray(0);
}

void Mesh::updateVertices(std::vector<Vertex> vertices) {
    this->vertices = vertices;
    glBindVertexArray(this->VAO);
    glBindBuffer(GL_ARRAY_BUFFER, this->VBO);
    glBufferData(GL_ARRAY_BUFFER, this->vertices.size() * sizeof(Vertex), &this->vertices[0], GL_DYNAMIC_DRAW); 
}