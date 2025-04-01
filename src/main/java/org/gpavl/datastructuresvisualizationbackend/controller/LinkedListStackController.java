package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/linked-list-stack")
@AllArgsConstructor
public class LinkedListStackController {

    private LinkedListStackService linkedListStackService;

    @PostMapping("/create/{name}")
    public ResponseEntity<Response> createArrayVector(@PathVariable String name) {
        Response response = linkedListStackService.create(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<Response> findArrayVector(@PathVariable String name) {
        Response response = linkedListStackService.findByName(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/size/{name}")
    public ResponseEntity<Response> size(@PathVariable String name) {
        Response response = linkedListStackService.size(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/is-empty/{name}")
    public ResponseEntity<Response> isEmpty(@PathVariable String name) {
        Response response = linkedListStackService.isEmpty(name);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/clear/{name}")
    public ResponseEntity<Response> clear(@PathVariable String name) {
        Response response = linkedListStackService.clear(name);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/push/{name}/{element}")
    public ResponseEntity<Response> push(@PathVariable String name, @PathVariable String element) {
        Response response = linkedListStackService.push(name, element);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/pop/{name}")
    public ResponseEntity<Response> pop(@PathVariable String name) {
        Response response = linkedListStackService.pop(name);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/peek/{name}")
    public ResponseEntity<Response> peek(@PathVariable String name) {
        Response response = linkedListStackService.peek(name);
        return ResponseEntity.ok(response);
    }
}
