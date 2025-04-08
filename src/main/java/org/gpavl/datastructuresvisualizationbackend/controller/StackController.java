package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.stack.ArrayStack;
import org.gpavl.datastructuresvisualizationbackend.model.stack.LinkedListStack;
import org.gpavl.datastructuresvisualizationbackend.model.stack.StackType;
import org.gpavl.datastructuresvisualizationbackend.service.StackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stack")
@AllArgsConstructor
public class StackController {

    private StackService stackService;

    @PostMapping("/create/{type}/{name}")
    public ResponseEntity<Response> create(
            @PathVariable StackType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> stackService.create(
                    Type.ARRAY_STACK,
                    name,
                    new ArrayStack()
            );

            case LINKED_LIST -> stackService.create(
                    Type.LINKED_LIST_STACK,
                    name,
                    new LinkedListStack()
            );
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{type}/{name}")
    public ResponseEntity<Response> find(
            @PathVariable StackType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> stackService.findByName(
                    Type.ARRAY_STACK,
                    name
            );

            case LINKED_LIST -> stackService.findByName(
                    Type.LINKED_LIST_STACK,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/size/{type}/{name}")
    public ResponseEntity<Response> size(
            @PathVariable StackType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> stackService.size(
                    Type.ARRAY_STACK,
                    name
            );

            case LINKED_LIST -> stackService.size(
                    Type.LINKED_LIST_STACK,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/is-empty/{type}/{name}")
    public ResponseEntity<Response> isEmpty(
            @PathVariable StackType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> stackService.isEmpty(
                    Type.ARRAY_STACK,
                    name
            );

            case LINKED_LIST -> stackService.isEmpty(
                    Type.LINKED_LIST_STACK,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/clear/{type}/{name}")
    public ResponseEntity<Response> clear(
            @PathVariable StackType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> stackService.clear(
                    Type.ARRAY_STACK,
                    name
            );

            case LINKED_LIST -> stackService.clear(
                    Type.LINKED_LIST_STACK,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/push/{type}/{name}/{element}")
    public ResponseEntity<Response> push(
            @PathVariable StackType type,
            @PathVariable String name,
            @PathVariable String element
    ) {
        Response response = switch (type) {
            case ARRAY -> stackService.push(
                    Type.ARRAY_STACK,
                    name,
                    element
            );

            case LINKED_LIST -> stackService.push(
                    Type.LINKED_LIST_STACK,
                    name,
                    element
            );
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/pop/{type}/{name}")
    public ResponseEntity<Response> pop(
            @PathVariable StackType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> stackService.pop(
                    Type.ARRAY_STACK,
                    name
            );

            case LINKED_LIST -> stackService.pop(
                    Type.LINKED_LIST_STACK,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/peek/{type}/{name}")
    public ResponseEntity<Response> peek(
            @PathVariable StackType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> stackService.peek(
                    Type.ARRAY_STACK,
                    name
            );

            case LINKED_LIST -> stackService.peek(
                    Type.LINKED_LIST_STACK,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }
}

