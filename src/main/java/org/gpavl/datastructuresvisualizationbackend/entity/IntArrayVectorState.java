package org.gpavl.datastructuresvisualizationbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(schema = "vector_array")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IntArrayVectorState extends BaseArrayVectorState<Integer> {

    @ElementCollection
    @CollectionTable(name = "int_array_values", schema = "value", joinColumns = @JoinColumn(name = "entity_id"))
    @Column(name = "int_value")
    private List<Integer> array;
}
