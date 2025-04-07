package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.queue.QueueType;
import org.gpavl.datastructuresvisualizationbackend.service.queue.ArrayQueueService;
import org.gpavl.datastructuresvisualizationbackend.service.queue.LinkedListQueueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queue")
@AllArgsConstructor
public class QueueController {

    private ArrayQueueService arrayQueueService;
    private LinkedListQueueService linkedListQueueService;

    @PostMapping("/create/{type}/{name}")
    public ResponseEntity<Response> createQueue(
            @PathVariable QueueType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> arrayQueueService.create(name);
            case LinkedList -> linkedListQueueService.create(name);
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{type}/{name}")
    public ResponseEntity<Response> findQueue(
            @PathVariable QueueType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> arrayQueueService.findByName(name);
            case LinkedList -> linkedListQueueService.findByName(name);
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/size/{type}/{name}")
    public ResponseEntity<Response> size(
            @PathVariable QueueType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> arrayQueueService.size(name);
            case LinkedList -> linkedListQueueService.size(name);
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/is-empty/{type}/{name}")
    public ResponseEntity<Response> isEmpty(
            @PathVariable QueueType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> arrayQueueService.isEmpty(name);
            case LinkedList -> linkedListQueueService.isEmpty(name);
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/clear/{type}/{name}")
    public ResponseEntity<Response> clear(
            @PathVariable QueueType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> arrayQueueService.clear(name);
            case LinkedList -> linkedListQueueService.clear(name);
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/enqueue/{type}/{name}/{element}")
    public ResponseEntity<Response> enqueue(
            @PathVariable QueueType type,
            @PathVariable String name,
            @PathVariable String element
    ) {
        Response response = switch (type) {
            case Array -> arrayQueueService.enqueue(name, element);
            case LinkedList -> linkedListQueueService.enqueue(name, element);
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/dequeue/{type}/{name}")
    public ResponseEntity<Response> dequeue(
            @PathVariable QueueType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> arrayQueueService.dequeue(name);
            case LinkedList -> linkedListQueueService.dequeue(name);
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/peek/{type}/{name}")
    public ResponseEntity<Response> peek(
            @PathVariable QueueType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case Array -> arrayQueueService.peek(name);
            case LinkedList -> linkedListQueueService.peek(name);
        };

        return ResponseEntity.ok(response);
    }
}
