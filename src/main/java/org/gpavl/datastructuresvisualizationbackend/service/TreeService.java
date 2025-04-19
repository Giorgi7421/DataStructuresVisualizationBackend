package org.gpavl.datastructuresvisualizationbackend.service;

import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.queue.*;
import org.gpavl.datastructuresvisualizationbackend.model.tree.AVLTree;
import org.gpavl.datastructuresvisualizationbackend.model.tree.BSTree;
import org.gpavl.datastructuresvisualizationbackend.model.tree.Tree;
import org.gpavl.datastructuresvisualizationbackend.repository.DataStructureRepository;
import org.gpavl.datastructuresvisualizationbackend.util.Converter;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;
import org.gpavl.datastructuresvisualizationbackend.util.OperationUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class TreeService extends DataStructureService {

    private final OperationUtils operationUtils;
    private final Map<Type, Supplier<Tree>> typeMap;

    public TreeService(
            DataStructureRepository dataStructureRepository,
            MemoryUtils memoryUtils,
            OperationUtils operationUtils
    ) {
        super(dataStructureRepository, memoryUtils);
        this.operationUtils = operationUtils;

        typeMap = Map.of(
                Type.BS_TREE, BSTree::new,
                Type.AVL_TREE, AVLTree::new
        );
    }

    public Response insert(Type type, String name, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Tree tree = convertToTree(state, type);
        return operationUtils.executeOneArgumentOperation(
                state,
                tree,
                Tree::insert,
                element
        );
    }

    public Response remove(Type type, String name, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Tree tree = convertToTree(state, type);
        return operationUtils.executeOneArgumentOperation(
                state,
                tree,
                Tree::remove,
                element
        );
    }

    public Response search(Type type, String name, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Tree tree = convertToTree(state, type);
        return operationUtils.executeOneArgumentOperation(
                state,
                tree,
                Tree::search,
                element
        );
    }

    public Response clear(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Tree tree = convertToTree(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                tree,
                Tree::clear
        );
    }

    private Tree convertToTree(DataStructureState state, Type type) {
        Tree tree = typeMap.get(type).get();
        MemoryHistoryDto memoryHistoryDto = new MemoryHistoryDto();
        memoryHistoryDto.setOperationHistoryList(
                state.getMemoryHistory().getOperationHistoryList().stream().map(Converter::convertToOperationHistoryDto).collect(Collectors.toList())
        );
        tree.setMemoryHistory(memoryHistoryDto);

        return tree;
    }
}
