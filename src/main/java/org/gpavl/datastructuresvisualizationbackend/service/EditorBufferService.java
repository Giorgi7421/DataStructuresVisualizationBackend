package org.gpavl.datastructuresvisualizationbackend.service;

import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.editorbuffer.ArrayEditorBuffer;
import org.gpavl.datastructuresvisualizationbackend.model.editorbuffer.EditorBuffer;
import org.gpavl.datastructuresvisualizationbackend.model.editorbuffer.TwoStackEditorBuffer;
import org.gpavl.datastructuresvisualizationbackend.repository.DataStructureRepository;
import org.gpavl.datastructuresvisualizationbackend.util.Converter;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;
import org.gpavl.datastructuresvisualizationbackend.util.OperationUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class EditorBufferService extends DataStructureService {

    private final OperationUtils operationUtils;
    private final Map<Type, Supplier<EditorBuffer>> typeMap;

    public EditorBufferService(
            DataStructureRepository dataStructureRepository,
            MemoryUtils memoryUtils,
            OperationUtils operationUtils
    ) {
        super(dataStructureRepository, memoryUtils);
        this.operationUtils = operationUtils;

        typeMap = Map.of(
                Type.ARRAY_EDITOR_BUFFER, ArrayEditorBuffer::new,
                Type.TWO_STACK_EDITOR_BUFFER, TwoStackEditorBuffer::new
                //Type.LINKED_LIST_EDITOR_BUFFER, LinkedListEditorBuffer::new,
                //Type.DOUBLY_LINKED_LIST_EDITOR_BUFFER, DoublyLinkedListEditorBuffer::new
        );
    }

    public Response moveCursorForward(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        EditorBuffer editorBuffer = convertToEditorBuffer(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                editorBuffer,
                EditorBuffer::moveCursorForward
        );
    }

    public Response moveCursorBackward(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        EditorBuffer editorBuffer = convertToEditorBuffer(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                editorBuffer,
                EditorBuffer::moveCursorBackward
        );
    }

    public Response moveCursorToStart(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        EditorBuffer editorBuffer = convertToEditorBuffer(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                editorBuffer,
                EditorBuffer::moveCursorToStart
        );
    }

    public Response moveCursorToEnd(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        EditorBuffer editorBuffer = convertToEditorBuffer(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                editorBuffer,
                EditorBuffer::moveCursorToEnd
        );
    }

    public Response insertCharacter(Type type, String name, char character) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        EditorBuffer editorBuffer = convertToEditorBuffer(state, type);
        return operationUtils.executeOneArgumentOperation(
                state,
                editorBuffer,
                EditorBuffer::insertCharacter,
                character
        );
    }

    public Response deleteCharacter(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        EditorBuffer editorBuffer = convertToEditorBuffer(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                editorBuffer,
                EditorBuffer::deleteCharacter
        );
    }

    private EditorBuffer convertToEditorBuffer(DataStructureState state, Type type) {
        EditorBuffer editorBuffer = typeMap.get(type).get();
        MemoryHistoryDto memoryHistoryDto = new MemoryHistoryDto();
        memoryHistoryDto.setOperationHistoryList(
                state.getMemoryHistory().getOperationHistoryList().stream().map(Converter::convertToOperationHistoryDto).collect(Collectors.toList())
        );
        editorBuffer.setMemoryHistory(memoryHistoryDto);

        return editorBuffer;
    }
}
