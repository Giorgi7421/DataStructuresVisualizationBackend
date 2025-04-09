package org.gpavl.datastructuresvisualizationbackend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoublyLinkedNode {

    private String value;
    private String previousAddress;
    private String nextAddress;

    public DoublyLinkedNode() {

    }

    public DoublyLinkedNode(String value) {
        this.value = value;
    }
}
