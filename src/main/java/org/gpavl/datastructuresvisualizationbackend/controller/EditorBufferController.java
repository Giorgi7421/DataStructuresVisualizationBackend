package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.editorbuffer.*;
import org.gpavl.datastructuresvisualizationbackend.service.EditorBufferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/editor-buffer")
@AllArgsConstructor
public class EditorBufferController {

    private EditorBufferService editorBufferService;

    @PostMapping("/create/{type}/{name}")
    public ResponseEntity<Response> create(
            @PathVariable EditorBufferType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> editorBufferService.create(
                    Type.ARRAY_EDITOR_BUFFER,
                    name,
                    new ArrayEditorBuffer()
            );

            case TWO_STACK -> editorBufferService.create(
                    Type.TWO_STACK_EDITOR_BUFFER,
                    name,
                    new TwoStackEditorBuffer()
            );

            case LINKED_LIST -> editorBufferService.create(
                    Type.LINKED_LIST_EDITOR_BUFFER,
                    name,
                    new LinkedListEditorBuffer()
            );

            case DOUBLY_LINKED_LIST -> editorBufferService.create(
                    Type.DOUBLY_LINKED_LIST_EDITOR_BUFFER,
                    name,
                    new DoublyLinkedListEditorBuffer()
            );
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{type}/{name}")
    public ResponseEntity<Response> find(
            @PathVariable EditorBufferType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> editorBufferService.findByName(
                    Type.ARRAY_EDITOR_BUFFER,
                    name
            );

            case TWO_STACK -> editorBufferService.findByName(
                    Type.TWO_STACK_EDITOR_BUFFER,
                    name
            );

            case LINKED_LIST -> editorBufferService.findByName(
                    Type.LINKED_LIST_EDITOR_BUFFER,
                    name
            );

            case DOUBLY_LINKED_LIST -> editorBufferService.findByName(
                    Type.DOUBLY_LINKED_LIST_EDITOR_BUFFER,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/moveCursorForward/{type}/{name}")
    public ResponseEntity<Response> moveCursorForward(
            @PathVariable EditorBufferType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> editorBufferService.moveCursorForward(
                    Type.ARRAY_EDITOR_BUFFER,
                    name
            );

            case TWO_STACK -> editorBufferService.moveCursorForward(
                    Type.TWO_STACK_EDITOR_BUFFER,
                    name
            );

            case LINKED_LIST -> editorBufferService.moveCursorForward(
                    Type.LINKED_LIST_EDITOR_BUFFER,
                    name
            );

            case DOUBLY_LINKED_LIST -> editorBufferService.moveCursorForward(
                    Type.DOUBLY_LINKED_LIST_EDITOR_BUFFER,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/moveCursorBackward/{type}/{name}")
    public ResponseEntity<Response> moveCursorBackward(
            @PathVariable EditorBufferType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> editorBufferService.moveCursorBackward(
                    Type.ARRAY_EDITOR_BUFFER,
                    name
            );

            case TWO_STACK -> editorBufferService.moveCursorBackward(
                    Type.TWO_STACK_EDITOR_BUFFER,
                    name
            );

            case LINKED_LIST -> editorBufferService.moveCursorBackward(
                    Type.LINKED_LIST_EDITOR_BUFFER,
                    name
            );

            case DOUBLY_LINKED_LIST -> editorBufferService.moveCursorBackward(
                    Type.DOUBLY_LINKED_LIST_EDITOR_BUFFER,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/moveCursorToStart/{type}/{name}")
    public ResponseEntity<Response> moveCursorToStart(
            @PathVariable EditorBufferType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> editorBufferService.moveCursorToStart(
                    Type.ARRAY_EDITOR_BUFFER,
                    name
            );

            case TWO_STACK -> editorBufferService.moveCursorToStart(
                    Type.TWO_STACK_EDITOR_BUFFER,
                    name
            );

            case LINKED_LIST -> editorBufferService.moveCursorToStart(
                    Type.LINKED_LIST_EDITOR_BUFFER,
                    name
            );

            case DOUBLY_LINKED_LIST -> editorBufferService.moveCursorToStart(
                    Type.DOUBLY_LINKED_LIST_EDITOR_BUFFER,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/moveCursorToEnd/{type}/{name}")
    public ResponseEntity<Response> moveCursorToEnd(
            @PathVariable EditorBufferType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> editorBufferService.moveCursorToEnd(
                    Type.ARRAY_EDITOR_BUFFER,
                    name
            );

            case TWO_STACK -> editorBufferService.moveCursorToEnd(
                    Type.TWO_STACK_EDITOR_BUFFER,
                    name
            );

            case LINKED_LIST -> editorBufferService.moveCursorToEnd(
                    Type.LINKED_LIST_EDITOR_BUFFER,
                    name
            );

            case DOUBLY_LINKED_LIST -> editorBufferService.moveCursorToEnd(
                    Type.DOUBLY_LINKED_LIST_EDITOR_BUFFER,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/insertCharacter/{type}/{name}/{character}")
    public ResponseEntity<Response> insertCharacter(
            @PathVariable EditorBufferType type,
            @PathVariable String name,
            @PathVariable char character
    ) {
        Response response = switch (type) {
            case ARRAY -> editorBufferService.insertCharacter(
                    Type.ARRAY_EDITOR_BUFFER,
                    name,
                    character
            );

            case TWO_STACK -> editorBufferService.insertCharacter(
                    Type.TWO_STACK_EDITOR_BUFFER,
                    name,
                    character
            );

            case LINKED_LIST -> editorBufferService.insertCharacter(
                    Type.LINKED_LIST_EDITOR_BUFFER,
                    name,
                    character
            );

            case DOUBLY_LINKED_LIST -> editorBufferService.insertCharacter(
                    Type.DOUBLY_LINKED_LIST_EDITOR_BUFFER,
                    name,
                    character
            );
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/deleteCharacter/{type}/{name}")
    public ResponseEntity<Response> deleteCharacter(
            @PathVariable EditorBufferType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> editorBufferService.deleteCharacter(
                    Type.ARRAY_EDITOR_BUFFER,
                    name
            );

            case TWO_STACK -> editorBufferService.deleteCharacter(
                    Type.TWO_STACK_EDITOR_BUFFER,
                    name
            );

            case LINKED_LIST -> editorBufferService.deleteCharacter(
                    Type.LINKED_LIST_EDITOR_BUFFER,
                    name
            );

            case DOUBLY_LINKED_LIST -> editorBufferService.deleteCharacter(
                    Type.DOUBLY_LINKED_LIST_EDITOR_BUFFER,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }
}
