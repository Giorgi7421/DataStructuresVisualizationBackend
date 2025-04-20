package org.gpavl.datastructuresvisualizationbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gpavl.datastructuresvisualizationbackend.model.Type;

@Entity
@Table(schema = "structure")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DataStructureState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    private Type type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "memory_history_id")
    private MemoryHistory memoryHistory;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
