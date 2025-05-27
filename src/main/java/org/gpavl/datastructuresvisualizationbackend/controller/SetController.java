package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.set.*;
import org.gpavl.datastructuresvisualizationbackend.service.SetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/set")
@AllArgsConstructor
public class SetController {

    private SetService setService;

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
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

            case MOVE_TO_FRONT -> setService.create(
                    Type.MOVE_TO_FRONT_SET,
                    name,
                    new MoveToFrontSet()
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
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

            case MOVE_TO_FRONT -> setService.findByName(
                    Type.MOVE_TO_FRONT_SET,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
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

            case MOVE_TO_FRONT -> setService.size(
                    Type.MOVE_TO_FRONT_SET,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
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

            case MOVE_TO_FRONT -> setService.isEmpty(
                    Type.MOVE_TO_FRONT_SET,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
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

            case MOVE_TO_FRONT -> setService.clear(
                    Type.MOVE_TO_FRONT_SET,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
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

            case MOVE_TO_FRONT -> setService.add(
                    Type.MOVE_TO_FRONT_SET,
                    name,
                    element
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
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

            case MOVE_TO_FRONT -> setService.remove(
                    Type.MOVE_TO_FRONT_SET,
                    name,
                    element
            );
        };

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
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

            case MOVE_TO_FRONT -> setService.contains(
                    Type.MOVE_TO_FRONT_SET,
                    name,
                    element
            );
        };

        return ResponseEntity.ok(response);
    }
}
