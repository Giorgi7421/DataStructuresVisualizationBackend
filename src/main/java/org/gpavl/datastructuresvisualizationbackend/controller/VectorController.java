package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.vector.ArrayVector;
import org.gpavl.datastructuresvisualizationbackend.model.vector.LinkedListVector;
import org.gpavl.datastructuresvisualizationbackend.model.vector.VectorType;
import org.gpavl.datastructuresvisualizationbackend.service.VectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vector")
@AllArgsConstructor
public class VectorController {

    private VectorService vectorService;

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @PostMapping("/create/{type}/{name}")
    public ResponseEntity<Response> create(
            @PathVariable VectorType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> vectorService.create(
                    Type.ARRAY_VECTOR,
                    name,
                    new ArrayVector()
            );

            case LINKED_LIST -> vectorService.create(
                    Type.LINKED_LIST_VECTOR,
                    name,
                    new LinkedListVector()
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/find/{type}/{name}")
    public ResponseEntity<Response> find(
            @PathVariable VectorType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> vectorService.findByName(
                    Type.ARRAY_VECTOR,
                    name
            );

            case LINKED_LIST -> vectorService.findByName(
                    Type.LINKED_LIST_VECTOR,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/size/{type}/{name}")
    public ResponseEntity<Response> size(
            @PathVariable VectorType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> vectorService.size(
                    Type.ARRAY_VECTOR,
                    name
            );

            case LINKED_LIST -> vectorService.size(
                    Type.LINKED_LIST_VECTOR,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/is-empty/{type}/{name}")
    public ResponseEntity<Response> isEmpty(
            @PathVariable VectorType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> vectorService.isEmpty(
                    Type.ARRAY_VECTOR,
                    name
            );

            case LINKED_LIST -> vectorService.isEmpty(
                    Type.LINKED_LIST_VECTOR,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @PatchMapping("/clear/{type}/{name}")
    public ResponseEntity<Response> clear(
            @PathVariable VectorType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> vectorService.clear(
                    Type.ARRAY_VECTOR,
                    name
            );

            case LINKED_LIST -> vectorService.clear(
                    Type.LINKED_LIST_VECTOR,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/get/{type}/{name}/{index}")
    public ResponseEntity<Response> get(
            @PathVariable VectorType type,
            @PathVariable String name,
            @PathVariable int index
    ) {
        Response response = switch (type) {
            case ARRAY -> vectorService.get(
                    Type.ARRAY_VECTOR,
                    name,
                    index
            );

            case LINKED_LIST -> vectorService.get(
                    Type.LINKED_LIST_VECTOR,
                    name,
                    index
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @PatchMapping("/set/{type}/{name}/{index}/{element}")
    public ResponseEntity<Response> set(
            @PathVariable VectorType type,
            @PathVariable String name,
            @PathVariable int index,
            @PathVariable String element
    ) {
        Response response = switch (type) {
            case ARRAY -> vectorService.set(
                    Type.ARRAY_VECTOR,
                    name,
                    index,
                    element
            );

            case LINKED_LIST -> vectorService.set(
                    Type.LINKED_LIST_VECTOR,
                    name,
                    index,
                    element
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @PatchMapping ("/add/{type}/{name}/{element}")
    public ResponseEntity<Response> add(
            @PathVariable VectorType type,
            @PathVariable String name,
            @PathVariable String element
    ) {
        Response response = switch (type) {
            case ARRAY -> vectorService.add(
                    Type.ARRAY_VECTOR,
                    name,
                    element
            );

            case LINKED_LIST -> vectorService.add(
                    Type.LINKED_LIST_VECTOR,
                    name,
                    element
            );
        };

        return ResponseEntity.ok(response);
        //TODO limit value length
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @PatchMapping ("/insert-at/{type}/{name}/{index}/{element}")
    public ResponseEntity<Response> insertAt(
            @PathVariable VectorType type,
            @PathVariable String name,
            @PathVariable int index,
            @PathVariable String element
    ) {
        Response response = switch (type) {
            case ARRAY -> vectorService.insertAt(
                    Type.ARRAY_VECTOR,
                    name,
                    index,
                    element
            );

            case LINKED_LIST -> vectorService.insertAt(
                    Type.LINKED_LIST_VECTOR,
                    name,
                    index,
                    element
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @PatchMapping ("/remove-at/{type}/{name}/{index}")
    public ResponseEntity<Response> removeAt(
            @PathVariable VectorType type,
            @PathVariable String name,
            @PathVariable int index
    ) {
        Response response = switch (type) {
            case ARRAY -> vectorService.removeAt(
                    Type.ARRAY_VECTOR,
                    name,
                    index
            );
            case LINKED_LIST -> vectorService.removeAt(
                    Type.LINKED_LIST_VECTOR,
                    name,
                    index
            );
        };

        return ResponseEntity.ok(response);
    }
}
