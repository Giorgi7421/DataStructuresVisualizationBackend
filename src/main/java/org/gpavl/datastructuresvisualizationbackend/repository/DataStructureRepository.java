package org.gpavl.datastructuresvisualizationbackend.repository;

import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.entity.User;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataStructureRepository extends JpaRepository<DataStructureState, Long> {
    Optional<DataStructureState> findByNameAndUserAndType(String name, User user, Type type);
    List<DataStructureState> findAllByUser(User user);
    void deleteByNameAndUser(String name, User user);
}
