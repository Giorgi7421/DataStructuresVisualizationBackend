package org.gpavl.datastructuresvisualizationbackend.model.vector;

public interface Vector {

    int size();
    boolean isEmpty();
    void clear();
    String get(int index);
    void set(int index, String element);
    void add(String element);
    void insertAt(int index, String element);
    void removeAt(int index);

}
