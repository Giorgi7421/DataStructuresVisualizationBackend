package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.queue.ArrayQueue;
import org.gpavl.datastructuresvisualizationbackend.model.queue.LinkedListQueue;
import org.gpavl.datastructuresvisualizationbackend.model.queue.QueueType;
import org.gpavl.datastructuresvisualizationbackend.service.QueueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queue")
@AllArgsConstructor
public class QueueController {

    private QueueService queueService;

    @PostMapping("/create/{type}/{name}")
    public ResponseEntity<Response> create(
            @PathVariable QueueType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> queueService.create(
                    Type.ARRAY_QUEUE,
                    name,
                    new ArrayQueue()
            );

            case LINKED_LIST -> queueService.create(
                    Type.LINKED_LIST_QUEUE,
                    name,
                    new LinkedListQueue()
            );
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{type}/{name}")
    public ResponseEntity<Response> find(
            @PathVariable QueueType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> queueService.findByName(
                    Type.ARRAY_QUEUE,
                    name
            );

            case LINKED_LIST -> queueService.findByName(
                    Type.LINKED_LIST_QUEUE,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/size/{type}/{name}")
    public ResponseEntity<Response> size(
            @PathVariable QueueType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> queueService.size(
                    Type.ARRAY_QUEUE,
                    name
            );

            case LINKED_LIST -> queueService.size(
                    Type.LINKED_LIST_QUEUE,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/is-empty/{type}/{name}")
    public ResponseEntity<Response> isEmpty(
            @PathVariable QueueType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> queueService.isEmpty(
                    Type.ARRAY_QUEUE,
                    name
            );

            case LINKED_LIST -> queueService.isEmpty(
                    Type.LINKED_LIST_QUEUE,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/clear/{type}/{name}")
    public ResponseEntity<Response> clear(
            @PathVariable QueueType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> queueService.clear(
                    Type.ARRAY_QUEUE,
                    name
            );

            case LINKED_LIST -> queueService.clear(
                    Type.LINKED_LIST_QUEUE,
                    name
            );
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
            case ARRAY -> queueService.enqueue(
                    Type.ARRAY_QUEUE,
                    name,
                    element
            );

            case LINKED_LIST -> queueService.enqueue(
                    Type.LINKED_LIST_QUEUE,
                    name,
                    element
            );
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/dequeue/{type}/{name}")
    public ResponseEntity<Response> dequeue(
            @PathVariable QueueType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> queueService.dequeue(
                    Type.ARRAY_QUEUE,
                    name
            );

            case LINKED_LIST -> queueService.dequeue(
                    Type.LINKED_LIST_QUEUE,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/peek/{type}/{name}")
    public ResponseEntity<Response> peek(
            @PathVariable QueueType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case ARRAY -> queueService.peek(
                    Type.ARRAY_QUEUE,
                    name
            );

            case LINKED_LIST -> queueService.peek(
                    Type.LINKED_LIST_QUEUE,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }
}
