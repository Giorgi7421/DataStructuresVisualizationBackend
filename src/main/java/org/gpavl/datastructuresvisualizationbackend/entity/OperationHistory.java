package org.gpavl.datastructuresvisualizationbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gpavl.datastructuresvisualizationbackend.util.ObjectToJsonConverter;

import java.util.List;
import java.util.Map;

@Entity
@Table(schema = "structure")
@Getter
@Setter
@NoArgsConstructor
public class OperationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operationName;

    @ElementCollection
    @CollectionTable(name = "parameters", schema = "structure", joinColumns = @JoinColumn(name = "operation_history_id"))
    @MapKeyColumn(name = "parameter_name")
    @Column(name = "parameter_value")
    @Convert(converter = ObjectToJsonConverter.class)
    private Map<String , Object> parameters;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "operation_history_id")
    @OrderColumn(name = "list_order")
    private List<MemorySnapshot> memorySnapshots;
}
