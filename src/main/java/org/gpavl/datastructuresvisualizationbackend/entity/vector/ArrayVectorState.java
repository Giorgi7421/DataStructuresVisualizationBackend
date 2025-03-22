package org.gpavl.datastructuresvisualizationbackend.entity.vector;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gpavl.datastructuresvisualizationbackend.entity.MemoryHistory;
import org.gpavl.datastructuresvisualizationbackend.entity.MemorySnapshot;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vector_state_id")
    private MemoryHistory memoryHistory;
}
