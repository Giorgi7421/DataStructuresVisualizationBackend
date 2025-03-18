package org.gpavl.datastructuresvisualizationbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gpavl.datastructuresvisualizationbackend.util.ObjectToJsonConverter;

import java.util.Map;

@Entity
@Table(schema = "structure")
@Getter
@Setter
@NoArgsConstructor
public class MemorySnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "local_variables", joinColumns = @JoinColumn(name = "memory_snapshot_id"))
    @MapKeyColumn(name = "variable_name")
    @Column(name = "variable_value")
    @Convert(converter = ObjectToJsonConverter.class)
    private Map<String, Object> localVariables;

    @ElementCollection
    @CollectionTable(name = "instance_variables", joinColumns = @JoinColumn(name = "memory_snapshot_id"))
    @MapKeyColumn(name = "variable_name")
    @Column(name = "variable_value")
    @Convert(converter = ObjectToJsonConverter.class)
    private Map<String, Object> instanceVariables;

    @Setter
    private String message;
}
