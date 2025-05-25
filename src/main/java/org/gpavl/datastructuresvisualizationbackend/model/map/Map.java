package org.gpavl.datastructuresvisualizationbackend.model.map;

import org.gpavl.datastructuresvisualizationbackend.model.DataStructure;

public abstract class Map extends DataStructure {

    public abstract void size();
    public abstract void isEmpty();
    public abstract void clear();
    public abstract void put(String key, String value);
    public abstract void get(String key);
    public abstract void containsKey(String key);
}
