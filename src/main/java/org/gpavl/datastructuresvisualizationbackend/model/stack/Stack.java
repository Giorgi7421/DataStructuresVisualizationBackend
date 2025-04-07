package org.gpavl.datastructuresvisualizationbackend.model.stack;

import org.gpavl.datastructuresvisualizationbackend.model.DataStructure;

public abstract class Stack extends DataStructure {

    public abstract void size();
    public abstract void isEmpty();
    public abstract void clear();
    public abstract void push(String element);
    public abstract void pop();
    public abstract void peek();
}
