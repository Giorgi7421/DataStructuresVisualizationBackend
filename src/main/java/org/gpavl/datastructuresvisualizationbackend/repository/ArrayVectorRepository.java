package org.gpavl.datastructuresvisualizationbackend.repository;

import org.gpavl.datastructuresvisualizationbackend.entity.ArrayVectorState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArrayVectorRepository extends JpaRepository<ArrayVectorState, Long> {
    Optional<ArrayVectorState> findByName(String name);
}
