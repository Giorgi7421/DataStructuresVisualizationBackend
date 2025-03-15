package org.gpavl.datastructuresvisualizationbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseArrayVectorRepository<T> extends JpaRepository<T, Long> {
}
