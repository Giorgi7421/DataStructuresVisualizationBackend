package org.gpavl.datastructuresvisualizationbackend.repository;

import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataStructureRepository extends JpaRepository<DataStructureState, Long> {
    Optional<DataStructureState> findByName(String name);
}
