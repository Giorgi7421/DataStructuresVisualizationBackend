package org.gpavl.datastructuresvisualizationbackend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class MemorySnapshotDto {

    private Map<String, Object> localVariables;
    private Map<String, Object> instanceVariables;

    private String message;

    public MemorySnapshotDto() {
        localVariables = new HashMap<>();
        instanceVariables = new HashMap<>();
    }

    public MemorySnapshotDto(MemorySnapshotDto other) {
        this.localVariables = new HashMap<>(other.localVariables);
        this.instanceVariables = new HashMap<>(other.instanceVariables);
    }
}
