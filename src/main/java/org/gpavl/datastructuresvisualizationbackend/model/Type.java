package org.gpavl.datastructuresvisualizationbackend.model;

import java.util.ArrayList;
import java.util.List;

public enum Type {
    ARRAY_VECTOR(true, false),
    LINKED_LIST_VECTOR(true, false),
    ARRAY_STACK(true, false),
    LINKED_LIST_STACK(true, false),
    TWO_QUEUE_STACK(true, false),
    ARRAY_QUEUE(true, false),
    LINKED_LIST_QUEUE(true, false),
    ARRAY_MAP(true, false),
    HASH_MAP(true, false),
    TREE_MAP(true, false),
    GRID(false, true),
    DEQUE(false, true),
    BS_TREE(true, false),
    AVL_TREE(true, false),
    EXPRESSION_TREE(true, false),
    HASH_SET(true, false),
    TREE_SET(true, false),
    SMALL_INT_SET(true, false),
    MOVE_TO_FRONT_SET(true, false),
    UNSORTED_VECTOR_PRIORITY_QUEUE(true, false),
    SORTED_LINKED_LIST_PRIORITY_QUEUE(true, false),
    UNSORTED_DOUBLY_LINKED_LIST_PRIORITY_QUEUE(true, false),
    BINARY_HEAP_PRIORITY_QUEUE(true, false),
    BIG_INTEGER(false, true),
    WEB_BROWSER(false, true),
    FILE_SYSTEM(false, true),
    ARRAY_EDITOR_BUFFER(false, false),
    TWO_STACK_EDITOR_BUFFER(false, false),
    LINKED_LIST_EDITOR_BUFFER(false, false),
    DOUBLY_LINKED_LIST_EDITOR_BUFFER(false, false);

    private final boolean isLastUnderscoreSeparator;
    private final boolean hasOneImplementation;

    Type(boolean isLastUnderscoreSeparator, boolean hasOneImplementation) {
        this.isLastUnderscoreSeparator = isLastUnderscoreSeparator;
        this.hasOneImplementation = hasOneImplementation;
    }

    public String getImplementation() {
        if (hasOneImplementation) {
            return null;
        }
        String text = this.name();
        if (isLastUnderscoreSeparator) {
            int lastUnderscoreIndex = text.lastIndexOf("_");
            return text.substring(0, lastUnderscoreIndex);
        }
        int secondToLastIndex = getSecondToLastIndex(text);
        return text.substring(0, secondToLastIndex);
    }

    public String getDataStructure() {
        if (hasOneImplementation) {
            return this.name();
        }

        String text = this.name();
        if (isLastUnderscoreSeparator) {
            int lastUnderscoreIndex = text.lastIndexOf("_");
            return text.substring(lastUnderscoreIndex + 1);
        }

        int secondToLastIndex = getSecondToLastIndex(text);
        return text.substring(secondToLastIndex + 1);
    }

    private int getSecondToLastIndex(String text) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '_') {
                indexes.add(i);
            }
        }

        return indexes.get(indexes.size() - 2);
    }
}
