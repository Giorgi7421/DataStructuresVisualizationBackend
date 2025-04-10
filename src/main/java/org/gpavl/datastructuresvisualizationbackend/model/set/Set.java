package org.gpavl.datastructuresvisualizationbackend.model.set;

import org.gpavl.datastructuresvisualizationbackend.model.DataStructure;

public abstract class Set extends DataStructure {

    public abstract void size();
    public abstract void isEmpty();
    public abstract void clear();
    public abstract void add(String element);
    public abstract void remove(String element);
    public abstract void contains(String element);
}
