package org.gpavl.datastructuresvisualizationbackend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreeNode {

    private String value;
    private String left;
    private String right;

    public TreeNode() {

    }

    public TreeNode(String value) {
        this.value = value;
    }
}
