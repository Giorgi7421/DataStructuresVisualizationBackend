package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.map.ArrayMap;
import org.gpavl.datastructuresvisualizationbackend.model.map.HashMap;
import org.gpavl.datastructuresvisualizationbackend.model.map.MapType;
import org.gpavl.datastructuresvisualizationbackend.model.map.TreeMap;
import org.gpavl.datastructuresvisualizationbackend.model.tree.AVLTree;
import org.gpavl.datastructuresvisualizationbackend.model.tree.BSTree;
import org.gpavl.datastructuresvisualizationbackend.model.tree.TreeType;
import org.gpavl.datastructuresvisualizationbackend.service.MapService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/map")
@AllArgsConstructor
public class MapController {

    private MapService mapService;

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @PostMapping("/create/{type}/{name}")
    public ResponseEntity<Response> create(
            @PathVariable MapType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case HASH -> mapService.create(
                    Type.HASH_MAP,
                    name,
                    new HashMap()
            );

            case ARRAY -> mapService.create(
                    Type.ARRAY_MAP,
                    name,
                    new ArrayMap()
            );

            case TREE -> mapService.create(
                    Type.TREE_MAP,
                    name,
                    new TreeMap()
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/find/{type}/{name}")
    public ResponseEntity<Response> findByName(
            @PathVariable MapType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case HASH -> mapService.findByName(
                    Type.HASH_MAP,
                    name
            );

            case ARRAY -> mapService.findByName(
                    Type.ARRAY_MAP,
                    name
            );

            case TREE -> mapService.findByName(
                    Type.TREE_MAP,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/size/{type}/{name}")
    public ResponseEntity<Response> size(
            @PathVariable MapType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case HASH -> mapService.size(
                    Type.HASH_MAP,
                    name
            );

            case ARRAY -> mapService.size(
                    Type.ARRAY_MAP,
                    name
            );

            case TREE -> mapService.size(
                    Type.TREE_MAP,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/is-empty/{type}/{name}")
    public ResponseEntity<Response> isEmpty(
            @PathVariable MapType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case HASH -> mapService.isEmpty(
                    Type.HASH_MAP,
                    name
            );

            case ARRAY -> mapService.isEmpty(
                    Type.ARRAY_MAP,
                    name
            );

            case TREE -> mapService.isEmpty(
                    Type.TREE_MAP,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @PatchMapping("/clear/{type}/{name}")
    public ResponseEntity<Response> clear(
            @PathVariable MapType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case HASH -> mapService.clear(
                    Type.HASH_MAP,
                    name
            );

            case ARRAY -> mapService.clear(
                    Type.ARRAY_MAP,
                    name
            );

            case TREE -> mapService.clear(
                    Type.TREE_MAP,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @PatchMapping("/put/{type}/{name}/{key}/{value}")
    public ResponseEntity<Response> put(
            @PathVariable MapType type,
            @PathVariable String name,
            @PathVariable String key,
            @PathVariable String value
    ) {
        Response response = switch (type) {
            case HASH -> mapService.put(
                    Type.HASH_MAP,
                    name,
                    key,
                    value
            );

            case ARRAY -> mapService.put(
                    Type.ARRAY_MAP,
                    name,
                    key,
                    value
            );

            case TREE -> mapService.put(
                    Type.TREE_MAP,
                    name,
                    key,
                    value
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/get/{type}/{name}/{key}")
    public ResponseEntity<Response> get(
            @PathVariable MapType type,
            @PathVariable String name,
            @PathVariable String key
    ) {
        Response response = switch (type) {
            case HASH -> mapService.get(
                    Type.HASH_MAP,
                    name,
                    key
            );

            case ARRAY -> mapService.get(
                    Type.ARRAY_MAP,
                    name,
                    key
            );

            case TREE -> mapService.get(
                    Type.TREE_MAP,
                    name,
                    key
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/contains-key/{type}/{name}/{key}")
    public ResponseEntity<Response> containsKey(
            @PathVariable MapType type,
            @PathVariable String name,
            @PathVariable String key
    ) {
        Response response = switch (type) {
            case HASH -> mapService.containsKey(
                    Type.HASH_MAP,
                    name,
                    key
            );

            case ARRAY -> mapService.containsKey(
                    Type.ARRAY_MAP,
                    name,
                    key
            );

            case TREE -> mapService.containsKey(
                    Type.TREE_MAP,
                    name,
                    key
            );
        };

        return ResponseEntity.ok(response);
    }
}
