package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.service.queue.ArrayQueueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/array-queue")
@AllArgsConstructor
public class ArrayQueueController {

    private ArrayQueueService arrayQueueService;

    @PostMapping("/create/{name}")
    public ResponseEntity<Response> createArrayVector(@PathVariable String name) {
        Response response = arrayQueueService.create(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<Response> findArrayVector(@PathVariable String name) {
        Response response = arrayQueueService.findByName(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/size/{name}")
    public ResponseEntity<Response> size(@PathVariable String name) {
        Response response = arrayQueueService.size(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/is-empty/{name}")
    public ResponseEntity<Response> isEmpty(@PathVariable String name) {
        Response response = arrayQueueService.isEmpty(name);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/clear/{name}")
    public ResponseEntity<Response> clear(@PathVariable String name) {
        Response response = arrayQueueService.clear(name);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/enqueue/{name}/{element}")
    public ResponseEntity<Response> enqueue(@PathVariable String name, @PathVariable String element) {
        Response response = arrayQueueService.enqueue(name, element);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/dequeue/{name}")
    public ResponseEntity<Response> dequeue(@PathVariable String name) {
        Response response = arrayQueueService.dequeue(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/peek/{name}")
    public ResponseEntity<Response> peek(@PathVariable String name) {
        Response response = arrayQueueService.peek(name);
        return ResponseEntity.ok(response);
    }
}
