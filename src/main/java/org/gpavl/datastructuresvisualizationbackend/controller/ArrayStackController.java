package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.service.stack.ArrayStackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/array-stack")
@AllArgsConstructor
public class ArrayStackController {

    private ArrayStackService arrayStackService;

    @PostMapping("/create/{name}")
    public ResponseEntity<Response> createArrayVector(@PathVariable String name) {
        Response response = arrayStackService.create(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<Response> findArrayVector(@PathVariable String name) {
        Response response = arrayStackService.findByName(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/size/{name}")
    public ResponseEntity<Response> size(@PathVariable String name) {
        Response response = arrayStackService.size(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/is-empty/{name}")
    public ResponseEntity<Response> isEmpty(@PathVariable String name) {
        Response response = arrayStackService.isEmpty(name);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/clear/{name}")
    public ResponseEntity<Response> clear(@PathVariable String name) {
        Response response = arrayStackService.clear(name);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/push/{name}/{element}")
    public ResponseEntity<Response> push(@PathVariable String name, @PathVariable String element) {
        Response response = arrayStackService.push(name, element);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/pop/{name}")
    public ResponseEntity<Response> pop(@PathVariable String name) {
        Response response = arrayStackService.pop(name);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/peek/{name}")
    public ResponseEntity<Response> peek(@PathVariable String name) {
        Response response = arrayStackService.peek(name);
        return ResponseEntity.ok(response);
    }
}
