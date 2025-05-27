package org.gpavl.datastructuresvisualizationbackend.model.set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class HashSetNode {

    private String element;
    private String linkAddress;

    public HashSetNode() {

    }
}
