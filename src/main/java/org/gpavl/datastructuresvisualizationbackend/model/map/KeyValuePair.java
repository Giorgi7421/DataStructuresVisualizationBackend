package org.gpavl.datastructuresvisualizationbackend.model.map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class KeyValuePair {

    private String key;
    private String value;

    public KeyValuePair() {

    }
}
