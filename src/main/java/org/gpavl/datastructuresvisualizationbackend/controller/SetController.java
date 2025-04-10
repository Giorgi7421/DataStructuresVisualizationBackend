package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.set.*;
import org.gpavl.datastructuresvisualizationbackend.model.vector.ArrayVector;
import org.gpavl.datastructuresvisualizationbackend.model.vector.LinkedListVector;
import org.gpavl.datastructuresvisualizationbackend.model.vector.VectorType;
import org.gpavl.datastructuresvisualizationbackend.service.SetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/set")
@AllArgsConstructor
public class SetController {

    private SetService setService;

    @PostMapping("/create/{type}/{name}")
    public ResponseEntity<Response> create(
            @PathVariable SetType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case HASH -> setService.create(
                    Type.HASH_SET,
                    name,
                    new HashSet()
            );

            case TREE -> setService.create(
                    Type.TREE_SET,
                    name,
                    new TreeSet()
            );

            case MOVE_TO_FRONT -> setService.create(
                    Type.MOVE_TO_FRONT_SET,
                    name,
                    new MoveToFrontSet()
            );

            case SMALL_INT -> setService.create(
                    Type.SMALL_INT_SET,
                    name,
                    new SmallIntSet()
            );
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{type}/{name}")
    public ResponseEntity<Response> find(
            @PathVariable SetType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case HASH -> setService.findByName(
                    Type.HASH_SET,
                    name
            );

            case TREE -> setService.findByName(
                    Type.TREE_SET,
                    name
            );

            case MOVE_TO_FRONT -> setService.findByName(
                    Type.MOVE_TO_FRONT_SET,
                    name
            );

            case SMALL_INT -> setService.findByName(
                    Type.SMALL_INT_SET,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/size/{type}/{name}")
    public ResponseEntity<Response> size(
            @PathVariable SetType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case HASH -> setService.size(
                    Type.HASH_SET,
                    name
            );

            case TREE -> setService.size(
                    Type.TREE_SET,
                    name
            );

            case MOVE_TO_FRONT -> setService.size(
                    Type.MOVE_TO_FRONT_SET,
                    name
            );

            case SMALL_INT -> setService.size(
                    Type.SMALL_INT_SET,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/is-empty/{type}/{name}")
    public ResponseEntity<Response> isEmpty(
            @PathVariable SetType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case HASH -> setService.isEmpty(
                    Type.HASH_SET,
                    name
            );

            case TREE -> setService.isEmpty(
                    Type.TREE_SET,
                    name
            );

            case MOVE_TO_FRONT -> setService.isEmpty(
                    Type.MOVE_TO_FRONT_SET,
                    name
            );

            case SMALL_INT -> setService.isEmpty(
                    Type.SMALL_INT_SET,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/clear/{type}/{name}")
    public ResponseEntity<Response> clear(
            @PathVariable SetType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case HASH -> setService.clear(
                    Type.HASH_SET,
                    name
            );

            case TREE -> setService.clear(
                    Type.TREE_SET,
                    name
            );

            case MOVE_TO_FRONT -> setService.clear(
                    Type.MOVE_TO_FRONT_SET,
                    name
            );

            case SMALL_INT -> setService.clear(
                    Type.SMALL_INT_SET,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping ("/add/{type}/{name}/{element}")
    public ResponseEntity<Response> add(
            @PathVariable SetType type,
            @PathVariable String name,
            @PathVariable String element
    ) {
        Response response = switch (type) {
            case HASH -> setService.add(
                    Type.HASH_SET,
                    name,
                    element
            );

            case TREE -> setService.add(
                    Type.TREE_SET,
                    name,
                    element
            );

            case MOVE_TO_FRONT -> setService.add(
                    Type.MOVE_TO_FRONT_SET,
                    name,
                    element
            );

            case SMALL_INT -> setService.add(
                    Type.SMALL_INT_SET,
                    name,
                    element
            );
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping ("/remove/{type}/{name}/{element}")
    public ResponseEntity<Response> remove(
            @PathVariable SetType type,
            @PathVariable String name,
            @PathVariable String element
    ) {
        Response response = switch (type) {
            case HASH -> setService.remove(
                    Type.HASH_SET,
                    name,
                    element
            );

            case TREE -> setService.remove(
                    Type.TREE_SET,
                    name,
                    element
            );

            case MOVE_TO_FRONT -> setService.remove(
                    Type.MOVE_TO_FRONT_SET,
                    name,
                    element
            );

            case SMALL_INT -> setService.remove(
                    Type.SMALL_INT_SET,
                    name,
                    element
            );
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping ("/contains/{type}/{name}/{element}")
    public ResponseEntity<Response> contains(
            @PathVariable SetType type,
            @PathVariable String name,
            @PathVariable String element
    ) {
        Response response = switch (type) {
            case HASH -> setService.contains(
                    Type.HASH_SET,
                    name,
                    element
            );

            case TREE -> setService.contains(
                    Type.TREE_SET,
                    name,
                    element
            );

            case MOVE_TO_FRONT -> setService.contains(
                    Type.MOVE_TO_FRONT_SET,
                    name,
                    element
            );

            case SMALL_INT -> setService.contains(
                    Type.SMALL_INT_SET,
                    name,
                    element
            );
        };

        return ResponseEntity.ok(response);
    }
}
