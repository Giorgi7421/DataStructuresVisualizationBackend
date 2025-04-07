package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.vector.ArrayVector;
import org.gpavl.datastructuresvisualizationbackend.model.vector.LinkedListVector;
import org.gpavl.datastructuresvisualizationbackend.model.vector.VectorType;
import org.gpavl.datastructuresvisualizationbackend.service.VectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vector")
@AllArgsConstructor
public class VectorController {

    private VectorService vectorService;

    @PostMapping("/create/{type}/{name}")
    public ResponseEntity<Response> createVector(
            @PathVariable VectorType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> vectorService.create(
                    Type.ARRAY_VECTOR,
                    name,
                    new ArrayVector()
            );

            case LinkedList -> vectorService.create(
                    Type.LINKED_LIST_VECTOR,
                    name,
                    new LinkedListVector()
            );
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{type}/{name}")
    public ResponseEntity<Response> findVector(
            @PathVariable VectorType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> vectorService.findByName(
                    Type.ARRAY_VECTOR,
                    name
            );

            case LinkedList -> vectorService.findByName(
                    Type.LINKED_LIST_VECTOR,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/size/{type}/{name}")
    public ResponseEntity<Response> size(
            @PathVariable VectorType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> vectorService.size(
                    Type.ARRAY_VECTOR,
                    name
            );

            case LinkedList -> vectorService.size(
                    Type.LINKED_LIST_VECTOR,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/is-empty/{type}/{name}")
    public ResponseEntity<Response> isEmpty(
            @PathVariable VectorType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> vectorService.isEmpty(
                    Type.ARRAY_VECTOR,
                    name
            );

            case LinkedList -> vectorService.isEmpty(
                    Type.LINKED_LIST_VECTOR,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/clear/{type}/{name}")
    public ResponseEntity<Response> clear(
            @PathVariable VectorType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> vectorService.clear(
                    Type.ARRAY_VECTOR,
                    name
            );

            case LinkedList -> vectorService.clear(
                    Type.LINKED_LIST_VECTOR,
                    name
            );
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
            case Array -> vectorService.get(
                    Type.ARRAY_VECTOR,
                    name,
                    index
            );

            case LinkedList -> vectorService.get(
                    Type.LINKED_LIST_VECTOR,
                    name,
                    index
            );
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
            case Array -> vectorService.set(
                    Type.ARRAY_VECTOR,
                    name,
                    index,
                    element
            );

            case LinkedList -> vectorService.set(
                    Type.LINKED_LIST_VECTOR,
                    name,
                    index,
                    element
            );
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
            case Array -> vectorService.add(
                    Type.ARRAY_VECTOR,
                    name,
                    element
            );

            case LinkedList -> vectorService.add(
                    Type.LINKED_LIST_VECTOR,
                    name,
                    element
            );
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
            case Array -> vectorService.insertAt(
                    Type.ARRAY_VECTOR,
                    name,
                    index,
                    element
            );

            case LinkedList -> vectorService.insertAt(
                    Type.LINKED_LIST_VECTOR,
                    name,
                    index,
                    element
            );
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
            case Array -> vectorService.removeAt(
                    Type.ARRAY_VECTOR,
                    name,
                    index
            );
            case LinkedList -> vectorService.removeAt(
                    Type.LINKED_LIST_VECTOR,
                    name,
                    index
            );
        };

        return ResponseEntity.ok(response);
    }
}
