package org.gpavl.datastructuresvisualizationbackend.util;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.repository.DataStructureRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServiceUtils {

    private DataStructureRepository dataStructureRepository;
    private MemoryUtils memoryUtils;

    public Response create(DataStructureState state) {
        DataStructureState result;
        try {
            result = dataStructureRepository.save(state);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateKeyException("Data structure with that name already exists");
        }

        return Converter.convertToResponse(result);
    }

    public Response findByName(String name, Type type) {
        return Converter.convertToResponse(memoryUtils.getDataStructureState(name, type));
    }
}
