package org.gpavl.datastructuresvisualizationbackend.model.map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class HashMapNode {

    private String key;
    private String value;
    private String linkAddress;

    public HashMapNode() {

    }
}
