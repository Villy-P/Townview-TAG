#include "tree.hpp"
#include "core.hpp"

#include <random>
#include <PerlinNoise.hpp>

#include <glm/gtx/string_cast.hpp>

void tree::treeSetup() {
    std::vector<glm::mat4> trees;

    std::random_device rd;
    std::mt19937 gen(rd());
    std::mt19937_64 rng(rd());
    std::uniform_int_distribution<> distr(0, 1000);
    std::uniform_int_distribution<> vary(-15, 15);
    std::uniform_real_distribution<double> unif(0, 3);
    std::uniform_real_distribution<float> rotation(0, 90);
    const siv::PerlinNoise perlin{ distr(gen) };

    float width = (float)glutGet(GLUT_WINDOW_WIDTH);

    for (double x = 0; x < 500; x++) {
        for (double y = 0; y < 500; y++) {
            double noise = perlin.octave2D_01((x * 0.01), (y * 0.01), 4);
            double rand = unif(rng);
            if (rand > noise)
                continue;
            glm::mat4 model = glm::mat4(1.0f);

            double trueY = y * 30 + vary(gen);
            double trueX = x * 30 + vary(gen);

            int squareXOff = (int)(trueX / 50.0);
            int squareYOff = (int)(trueY / 50.0);
            double squareX = fmod(trueX, 50);
            double squareY = fmod(trueY, 50);

            double z = (core::landscape->getZOfSquare(squareXOff, squareYOff, squareX, squareY) * 3.0f);

            if (z < 0.4 * 3.0f || z > 0.65 * 3.0f)
                continue;

            model = glm::translate(model, glm::vec3(trueX / width, trueY / width, z - 0.02));
            model = glm::scale(model, glm::vec3(.00003));
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

        glEnableVertexAttribArray(3);
        glVertexAttribPointer(3, 4, GL_FLOAT, GL_FALSE, sizeof(glm::mat4), (void*)0);
        glEnableVertexAttribArray(4);
        glVertexAttribPointer(4, 4, GL_FLOAT, GL_FALSE, sizeof(glm::mat4), (void*)(sizeof(glm::vec4)));
        glEnableVertexAttribArray(5);
        glVertexAttribPointer(5, 4, GL_FLOAT, GL_FALSE, sizeof(glm::mat4), (void*)(2 * sizeof(glm::vec4)));
        glEnableVertexAttribArray(6);
        glVertexAttribPointer(6, 4, GL_FLOAT, GL_FALSE, sizeof(glm::mat4), (void*)(3 * sizeof(glm::vec4)));

        glVertexAttribDivisor(3, 1);
        glVertexAttribDivisor(4, 1);
        glVertexAttribDivisor(5, 1);
        glVertexAttribDivisor(6, 1);

        glBindVertexArray(0);
    } 
}