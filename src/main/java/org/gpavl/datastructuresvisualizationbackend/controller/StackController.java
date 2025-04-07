package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.stack.StackType;
import org.gpavl.datastructuresvisualizationbackend.service.stack.ArrayStackService;
import org.gpavl.datastructuresvisualizationbackend.service.stack.LinkedListStackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stack")
@AllArgsConstructor
public class StackController {

    private ArrayStackService arrayStackService;
    private LinkedListStackService linkedListStackService;

    @PostMapping("/create/{type}/{name}")
    public ResponseEntity<Response> createStack(
            @PathVariable StackType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> arrayStackService.create(name);
            case LinkedList -> linkedListStackService.create(name);
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{type}/{name}")
    public ResponseEntity<Response> findStack(
            @PathVariable StackType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> arrayStackService.findByName(name);
            case LinkedList -> linkedListStackService.findByName(name);
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/size/{type}/{name}")
    public ResponseEntity<Response> size(
            @PathVariable StackType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> arrayStackService.size(name);
            case LinkedList -> linkedListStackService.size(name);
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/is-empty/{type}/{name}")
    public ResponseEntity<Response> isEmpty(
            @PathVariable StackType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> arrayStackService.isEmpty(name);
            case LinkedList -> linkedListStackService.isEmpty(name);
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/clear/{type}/{name}")
    public ResponseEntity<Response> clear(
            @PathVariable StackType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> arrayStackService.clear(name);
            case LinkedList -> linkedListStackService.clear(name);
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
            case Array -> arrayStackService.push(name, element);
            case LinkedList -> linkedListStackService.push(name, element);
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/pop/{type}/{name}")
    public ResponseEntity<Response> pop(
            @PathVariable StackType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> arrayStackService.pop(name);
            case LinkedList -> linkedListStackService.pop(name);
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/peek/{type}/{name}")
    public ResponseEntity<Response> peek(
            @PathVariable StackType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> arrayStackService.peek(name);
            case LinkedList -> linkedListStackService.peek(name);
        };

        return ResponseEntity.ok(response);
    }
}

