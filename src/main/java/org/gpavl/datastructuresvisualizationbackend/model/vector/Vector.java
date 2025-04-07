package org.gpavl.datastructuresvisualizationbackend.model.vector;

import org.gpavl.datastructuresvisualizationbackend.model.DataStructure;

public abstract class Vector extends DataStructure {

    public abstract void size();
    public abstract void isEmpty();
    public abstract void clear();
    public abstract void get(int index);
    public abstract void set(int index, String element);
    public abstract void add(String element);
    public abstract void insertAt(int index, String element);
    public abstract void removeAt(int index);
}
