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
    public ResponseEntity<Response> createStack(
            @PathVariable StackType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> stackService.create(
                    Type.ARRAY_STACK,
                    name,
                    new ArrayStack()
            );

            case LinkedList -> stackService.create(
                    Type.LINKED_LIST_STACK,
                    name,
                    new LinkedListStack()
            );
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{type}/{name}")
    public ResponseEntity<Response> findStack(
            @PathVariable StackType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> stackService.findByName(
                    Type.ARRAY_STACK,
                    name
            );

            case LinkedList -> stackService.findByName(
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
            case Array -> stackService.size(
                    Type.ARRAY_STACK,
                    name
            );

            case LinkedList -> stackService.size(
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
            case Array -> stackService.isEmpty(
                    Type.ARRAY_STACK,
                    name
            );

            case LinkedList -> stackService.isEmpty(
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
            case Array -> stackService.clear(
                    Type.ARRAY_STACK,
                    name
            );

            case LinkedList -> stackService.clear(
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
            case Array -> stackService.push(
                    Type.ARRAY_STACK,
                    name,
                    element
            );

            case LinkedList -> stackService.push(
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
            case Array -> stackService.pop(
                    Type.ARRAY_STACK,
                    name
            );

            case LinkedList -> stackService.pop(
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
            case Array -> stackService.peek(
                    Type.ARRAY_STACK,
                    name
            );

            case LinkedList -> stackService.peek(
                    Type.LINKED_LIST_STACK,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }
}

