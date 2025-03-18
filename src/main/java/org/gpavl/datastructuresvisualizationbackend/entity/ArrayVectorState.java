package org.gpavl.datastructuresvisualizationbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(schema = "structure")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArrayVectorState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    private int capacity;
    private int count;

    @ElementCollection
    @CollectionTable(name = "structure_value", schema = "structure", joinColumns = @JoinColumn(name = "vector_state_id"))
    @Column(name = "value")
    @OrderColumn(name = "list_order")
    private List<String> array;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "vector_state_id")
    @OrderColumn(name = "list_order")
    private List<MemorySnapshot> steps;
}
