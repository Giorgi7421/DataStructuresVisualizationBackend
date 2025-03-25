package org.gpavl.datastructuresvisualizationbackend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoryNode {

    private String value;
    private String nextAddress;

    public MemoryNode() {

    }

    public MemoryNode(String value) {
        this.value = value;
    }
}
