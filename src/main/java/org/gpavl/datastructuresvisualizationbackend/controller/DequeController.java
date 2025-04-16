package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Deque;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.service.DequeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deque")
@AllArgsConstructor
public class DequeController {

    private DequeService dequeService;

    @PostMapping("/create/{name}")
    public ResponseEntity<Response> create(@PathVariable String name) {
        Response response = dequeService.create(Type.DEQUEUE, name, new Deque());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<Response> find(@PathVariable String name) {
        Response response = dequeService.findByName(Type.DEQUEUE, name);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/push-back/{name}/{element}")
    public ResponseEntity<Response> pushBack(@PathVariable String name, @PathVariable String element) {
        Response response = dequeService.pushBack(name, element);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/pop-back/{name}")
    public ResponseEntity<Response> popBack(@PathVariable String name) {
        Response response = dequeService.popBack(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-back/{name}")
    public ResponseEntity<Response> getBack(@PathVariable String name) {
        Response response = dequeService.getBack(name);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/push-front/{name}/{element}")
    public ResponseEntity<Response> pushFront(@PathVariable String name, @PathVariable String element) {
        Response response = dequeService.pushFront(name, element);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/pop-front/{name}")
    public ResponseEntity<Response> popFront(@PathVariable String name) {
        Response response = dequeService.popFront(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-front/{name}")
    public ResponseEntity<Response> getFront(@PathVariable String name) {
        Response response = dequeService.getFront(name);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/clear/{name}")
    public ResponseEntity<Response> clear(@PathVariable String name) {
        Response response = dequeService.clear(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/size/{name}")
    public ResponseEntity<Response> size(@PathVariable String name) {
        Response response = dequeService.size(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/is-empty/{name}")
    public ResponseEntity<Response> isEmpty(@PathVariable String name) {
        Response response = dequeService.isEmpty(name);
        return ResponseEntity.ok(response);
    }
}
