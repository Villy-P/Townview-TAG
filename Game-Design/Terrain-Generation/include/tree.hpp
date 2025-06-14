#ifndef __OPENGL_TREE_HPP__
#define __OPENGL_TREE_HPP__

#include <model.hpp>

namespace tree {
    inline Model* tree;

    inline unsigned int InstanceVBO;

    void treeSetup();

    inline int amount = 10000;
}

#endif