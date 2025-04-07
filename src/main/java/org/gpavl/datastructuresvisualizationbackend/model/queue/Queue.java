package org.gpavl.datastructuresvisualizationbackend.model.queue;

import org.gpavl.datastructuresvisualizationbackend.model.DataStructure;

public abstract class Queue extends DataStructure {

    public abstract void size();
    public abstract void isEmpty();
    public abstract void clear();
    public abstract void enqueue(String element);
    public abstract void dequeue();
    public abstract void peek();
}
