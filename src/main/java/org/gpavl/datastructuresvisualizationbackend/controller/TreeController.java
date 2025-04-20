package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.tree.AVLTree;
import org.gpavl.datastructuresvisualizationbackend.model.tree.BSTree;
import org.gpavl.datastructuresvisualizationbackend.model.tree.TreeType;
import org.gpavl.datastructuresvisualizationbackend.service.TreeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/tree")
@AllArgsConstructor
public class TreeController {

    private TreeService treeService;

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @PostMapping("/create/{type}/{name}")
    public ResponseEntity<Response> create(
            @PathVariable TreeType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case BS -> treeService.create(
                    Type.BS_TREE,
                    name,
                    new BSTree()
            );

            case AVL -> treeService.create(
                    Type.AVL_TREE,
                    name,
                    new AVLTree()
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/find/{type}/{name}")
    public ResponseEntity<Response> find(
            @PathVariable TreeType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case BS -> treeService.findByName(
                    Type.BS_TREE,
                    name
            );

            case AVL -> treeService.findByName(
                    Type.AVL_TREE,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @PatchMapping ("/insert/{type}/{name}/{element}")
    public ResponseEntity<Response> insert(
            @PathVariable TreeType type,
            @PathVariable String name,
            @PathVariable String element
    ) {
        Response response = switch (type) {
            case BS -> treeService.insert(
                    Type.BS_TREE,
                    name,
                    element
            );

            case AVL -> treeService.insert(
                    Type.AVL_TREE,
                    name,
                    element
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @PatchMapping ("/remove/{type}/{name}/{element}")
    public ResponseEntity<Response> remove(
            @PathVariable TreeType type,
            @PathVariable String name,
            @PathVariable String element
    ) {
        Response response = switch (type) {
            case BS -> treeService.remove(
                    Type.BS_TREE,
                    name,
                    element
            );

            case AVL -> treeService.remove(
                    Type.AVL_TREE,
                    name,
                    element
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping ("/search/{type}/{name}/{element}")
    public ResponseEntity<Response> search(
            @PathVariable TreeType type,
            @PathVariable String name,
            @PathVariable String element
    ) {
        Response response = switch (type) {
            case BS -> treeService.search(
                    Type.BS_TREE,
                    name,
                    element
            );

            case AVL -> treeService.search(
                    Type.AVL_TREE,
                    name,
                    element
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @PatchMapping ("/clear/{type}/{name}")
    public ResponseEntity<Response> clear(
            @PathVariable TreeType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case BS -> treeService.clear(
                    Type.BS_TREE,
                    name
            );

            case AVL -> treeService.clear(
                    Type.AVL_TREE,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }
}
