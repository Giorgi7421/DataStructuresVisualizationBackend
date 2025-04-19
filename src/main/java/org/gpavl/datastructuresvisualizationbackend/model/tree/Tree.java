package org.gpavl.datastructuresvisualizationbackend.model.tree;

import org.gpavl.datastructuresvisualizationbackend.model.DataStructure;

public abstract class Tree extends DataStructure {

    public abstract void insert(String element);
    public abstract void remove(String element);
    public abstract void search(String element);
    public abstract void clear();
}
