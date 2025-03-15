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
public class StringArrayVectorState extends BaseArrayVectorState<String> {

    @ElementCollection
    @CollectionTable(name = "string_array_values", joinColumns = @JoinColumn(name = "entity_id"))
    @Column(name = "string_value")
    private List<String> array;
}
