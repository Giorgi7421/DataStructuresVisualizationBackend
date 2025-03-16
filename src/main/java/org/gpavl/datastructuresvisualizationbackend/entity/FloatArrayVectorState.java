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
public class FloatArrayVectorState extends BaseArrayVectorState {

    @ElementCollection
    @CollectionTable(name = "float_array_values", schema = "value", joinColumns = @JoinColumn(name = "entity_id"))
    @Column(name = "float_value")
    private List<Float> array;
}
