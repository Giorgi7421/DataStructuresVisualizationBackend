package org.gpavl.datastructuresvisualizationbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoolArrayVectorState extends BaseArrayVectorState<Boolean> {

    @ElementCollection
    @CollectionTable(name = "bool_array_values", joinColumns = @JoinColumn(name = "entity_id"))
    @Column(name = "bool_value")
    private List<Boolean> array;
}
