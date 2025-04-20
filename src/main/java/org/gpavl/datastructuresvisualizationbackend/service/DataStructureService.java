package org.gpavl.datastructuresvisualizationbackend.service;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.entity.User;
import org.gpavl.datastructuresvisualizationbackend.model.DataStructure;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.repository.DataStructureRepository;
import org.gpavl.datastructuresvisualizationbackend.security.UserService;
import org.gpavl.datastructuresvisualizationbackend.util.Converter;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public abstract class DataStructureService {

    protected DataStructureRepository dataStructureRepository;
    protected MemoryUtils memoryUtils;
    protected UserService userService;

    public Response create(
            Type type,
            String name,
            DataStructure dataStructure
    ) {
        DataStructureState state = Converter.convertToDataStructureState(dataStructure, name, type);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.getCurrentUser(username);

        state.setUser(currentUser);

        DataStructureState result;
        try {
            result = dataStructureRepository.save(state);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateKeyException("Data structure with that name already exists");
        }

        return Converter.convertToResponse(result);
    }

    public Response findByName(Type type, String name) {
        return Converter.convertToResponse(memoryUtils.getDataStructureState(name, type));
    }
}
