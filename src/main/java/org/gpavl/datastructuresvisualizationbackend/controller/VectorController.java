package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.vector.VectorType;
import org.gpavl.datastructuresvisualizationbackend.service.vector.ArrayVectorService;
import org.gpavl.datastructuresvisualizationbackend.service.vector.LinkedListVectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vector")
@AllArgsConstructor
public class VectorController {

    private ArrayVectorService arrayVectorService;
    private LinkedListVectorService linkedListVectorService;

    @PostMapping("/create/{type}/{name}")
    public ResponseEntity<Response> createVector(
            @PathVariable VectorType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> arrayVectorService.create(name);
            case LinkedList -> linkedListVectorService.create(name);
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{type}/{name}")
    public ResponseEntity<Response> findVector(
            @PathVariable VectorType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> arrayVectorService.findByName(name);
            case LinkedList -> linkedListVectorService.findByName(name);
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/size/{type}/{name}")
    public ResponseEntity<Response> size(
            @PathVariable VectorType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> arrayVectorService.size(name);
            case LinkedList -> linkedListVectorService.size(name);
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/is-empty/{type}/{name}")
    public ResponseEntity<Response> isEmpty(
            @PathVariable VectorType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> arrayVectorService.isEmpty(name);
            case LinkedList -> linkedListVectorService.isEmpty(name);
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/clear/{type}/{name}")
    public ResponseEntity<Response> clear(
            @PathVariable VectorType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> arrayVectorService.clear(name);
            case LinkedList -> linkedListVectorService.clear(name);
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{type}/{name}/{index}")
    public ResponseEntity<Response> get(
            @PathVariable VectorType type,
            @PathVariable String name,
            @PathVariable int index
    ) {
        Response response = switch (type) {
            case Array -> arrayVectorService.get(name, index);
            case LinkedList -> linkedListVectorService.get(name, index);
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/set/{type}/{name}/{index}/{element}")
    public ResponseEntity<Response> set(
            @PathVariable VectorType type,
            @PathVariable String name,
            @PathVariable int index,
            @PathVariable String element
    ) {
        Response response = switch (type) {
            case Array -> arrayVectorService.set(name, index, element);
            case LinkedList -> linkedListVectorService.set(name, index, element);
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping ("/add/{type}/{name}/{element}")
    public ResponseEntity<Response> add(
            @PathVariable VectorType type,
            @PathVariable String name,
            @PathVariable String element
    ) {
        Response response = switch (type) {
            case Array -> arrayVectorService.add(name, element);
            case LinkedList -> linkedListVectorService.add(name, element);
        };

        return ResponseEntity.ok(response);
        //TODO limit value length
    }

    @PatchMapping ("/insert-at/{type}/{name}/{index}/{element}")
    public ResponseEntity<Response> insertAt(
            @PathVariable VectorType type,
            @PathVariable String name,
            @PathVariable int index,
            @PathVariable String element
    ) {
        Response response = switch (type) {
            case Array -> arrayVectorService.insertAt(name, index, element);
            case LinkedList -> linkedListVectorService.insertAt(name, index, element);
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping ("/remove-at/{type}/{name}/{index}")
    public ResponseEntity<Response> removeAt(
            @PathVariable VectorType type,
            @PathVariable String name,
            @PathVariable int index
    ) {
        Response response = switch (type) {
            case Array -> arrayVectorService.removeAt(name, index);
            case LinkedList -> linkedListVectorService.removeAt(name, index);
        };

        return ResponseEntity.ok(response);
    }
}
