package org.gpavl.datastructuresvisualizationbackend.model;

public enum Type {
    ARRAY_VECTOR,
    LINKED_LIST_VECTOR,
    ARRAY_STACK,
    LINKED_LIST_STACK,
    TWO_QUEUE_STACK,
    ARRAY_QUEUE,
    LINKED_LIST_QUEUE,
    ARRAY_MAP,
    HASH_MAP,
    TREE_MAP,
    GRID,
    DEQUEUE,
    BS_TREE,
    AVL_TREE,
    EXPRESSION_TREE,
    HASH_SET,
    TREE_SET,
    SMALL_INT_SET,
    MOVE_TO_FRONT_SET,
    UNSORTED_VECTOR_PRIORITY_QUEUE,
    SORTED_LINKED_LIST_PRIORITY_QUEUE,
    UNSORTED_DOUBLY_LINKED_LIST_PRIORITY_QUEUE,
    BINARY_HEAP_PRIORITY_QUEUE,
    BIG_INTEGER,
    WEB_BROWSER,
    FILE_SYSTEM,
    ARRAY_EDITOR_BUFFER,
    TWO_STACK_EDITOR_BUFFER,
    LINKED_LIST_EDITOR_BUFFER,
    DOUBLY_LINKED_LIST_EDITOR_BUFFER;

    public String getImplementation() {
        String text = this.name();
        int lastUnderscoreIndex = text.lastIndexOf("_");
        return text.substring(0, lastUnderscoreIndex);
    }

    public String getDataStructure() {
        String text = this.name();
        int lastUnderscoreIndex = text.lastIndexOf("_");
        return text.substring(lastUnderscoreIndex + 1);
    }
}
