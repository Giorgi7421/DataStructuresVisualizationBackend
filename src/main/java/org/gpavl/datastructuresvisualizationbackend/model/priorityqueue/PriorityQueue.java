package org.gpavl.datastructuresvisualizationbackend.model.priorityqueue;

import org.gpavl.datastructuresvisualizationbackend.model.DataStructure;

public abstract class PriorityQueue extends DataStructure {

    public abstract void size();
    public abstract void isEmpty();
    public abstract void enqueue(String element, int priority);
    public abstract void dequeue();
    public abstract void peek();
}
