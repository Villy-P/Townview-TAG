#include "tree.hpp"
#include "core.hpp"

#include <random>
#include <iostream>
#include <PerlinNoise.hpp>

#include <glm/gtx/string_cast.hpp>

void tree::treeSetup() {
    std::vector<glm::mat4> trees;

    std::random_device rd;
    std::mt19937 gen(rd());
    std::mt19937_64 rng(rd());
    std::uniform_int_distribution<> distr(0, 1000);
    std::uniform_real_distribution<> vary(-.5, 5);
    std::uniform_real_distribution<double> unif(0, 3);
    std::uniform_real_distribution<float> rotation(0, 90);
    const siv::PerlinNoise perlin{ distr(gen) };

    for (double x = 0; x < 200; x++) {
        for (double y = 0; y < 200; y++) {
            double noise = perlin.octave2D_01((x * 0.01), (y * 0.01), 4);
            double rand = unif(rng);
            if (rand > noise)
                continue;
            glm::mat4 model = glm::mat4(1.0f);

            double trueX = x + vary(rng);
            double trueY = y + vary(rng);

            int squareXOff = (int)(trueX / 1.0);
            int squareYOff = (int)(trueY / 1.0);
            double squareX = fmod(trueX, 1);
            double squareY = fmod(trueY, 1);

            double z = core::terrain->getZOfSquare(squareXOff, squareYOff, squareX, squareY);

            if (z > 33.0f)
                continue;

            model = glm::translate(model, glm::vec3(trueX, trueY, z - 0.3));
            model = glm::scale(model, glm::vec3(.0009));
            model = glm::rotate(model, glm::radians(rotation(gen)), glm::vec3(0, 0, 1));

            trees.push_back(model);
        }
    }

    glGenBuffers(1, &tree::InstanceVBO);
    glBindBuffer(GL_ARRAY_BUFFER, tree::InstanceVBO);
    glBufferData(GL_ARRAY_BUFFER, trees.size() * sizeof(glm::mat4), trees.data(), GL_STATIC_DRAW);

    for (unsigned int i = 0; i < tree::tree->meshes.size(); i++) {
        unsigned int VAO = tree::tree->meshes[i].VAO;
        glBindVertexArray(VAO);

        glEnableVertexAttribArray(5);
        glVertexAttribPointer(5, 4, GL_FLOAT, GL_FALSE, sizeof(glm::mat4), (void*)0);
        glEnableVertexAttribArray(6);
        glVertexAttribPointer(6, 4, GL_FLOAT, GL_FALSE, sizeof(glm::mat4), (void*)(sizeof(glm::vec4)));
        glEnableVertexAttribArray(7);
        glVertexAttribPointer(7, 4, GL_FLOAT, GL_FALSE, sizeof(glm::mat4), (void*)(2 * sizeof(glm::vec4)));
        glEnableVertexAttribArray(8);
        glVertexAttribPointer(8, 4, GL_FLOAT, GL_FALSE, sizeof(glm::mat4), (void*)(3 * sizeof(glm::vec4)));

        glVertexAttribDivisor(5, 1);
        glVertexAttribDivisor(6, 1);
        glVertexAttribDivisor(7, 1);
        glVertexAttribDivisor(8, 1);

        glBindVertexArray(0);
    } 
}