#include "core.hpp"
#include "textures.hpp"
#include "image.hpp"
#include "core.hpp"
#include "tree.hpp"

#include <iostream>

int main(int argc, char** argv) {
    core::setup(argc, argv);
    texture::player = image::generateImageTexture("assets/player.png", texture::player);
    texture::landtexture = image::generateImageTexture("assets/spritesheet.png", texture::landtexture);
    core::landscape = new Landscape(500, 500);
    core::player = new Player();
    tree::treeSetup();

    core::player->move(1000, 1000);
    
    core::run();
}