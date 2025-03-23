package org.gpavl.datastructuresvisualizationbackend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {
    private String value;
    private Node nextNode;

    public Node() {

    }

    public Node(String value) {
        this.value = value;
    }
}
