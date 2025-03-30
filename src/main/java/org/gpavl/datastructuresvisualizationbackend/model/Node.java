package org.gpavl.datastructuresvisualizationbackend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {

    private String value;
    private String nextAddress;

    public Node() {

    }

    public Node(String value) {
        this.value = value;
    }
}
