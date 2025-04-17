package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.queue.*;
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

            case UNSORTED_VECTOR_PRIORITY -> queueService.create(
                    Type.UNSORTED_VECTOR_PRIORITY_QUEUE,
                    name,
                    new UnsortedVectorPriorityQueue()
            );

            case SORTED_LINKED_LIST_PRIORITY -> queueService.create(
                    Type.SORTED_LINKED_LIST_PRIORITY_QUEUE,
                    name,
                    new SortedLinkedListPriorityQueue()
            );

            case UNSORTED_DOUBLY_LINKED_LIST_PRIORITY -> queueService.create(
                    Type.UNSORTED_DOUBLY_LINKED_LIST_PRIORITY_QUEUE,
                    name,
                    new UnsortedDoublyLinkedListPriorityQueue()
            );

            case BINARY_HEAP_PRIORITY -> queueService.create(
                    Type.BINARY_HEAP_PRIORITY_QUEUE,
                    name,
                    new BinaryHeapPriorityQueue()
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

            case UNSORTED_VECTOR_PRIORITY -> queueService.findByName(
                    Type.UNSORTED_VECTOR_PRIORITY_QUEUE,
                    name
            );

            case SORTED_LINKED_LIST_PRIORITY -> queueService.findByName(
                    Type.SORTED_LINKED_LIST_PRIORITY_QUEUE,
                    name
            );

            case UNSORTED_DOUBLY_LINKED_LIST_PRIORITY -> queueService.findByName(
                    Type.UNSORTED_DOUBLY_LINKED_LIST_PRIORITY_QUEUE,
                    name
            );

            case BINARY_HEAP_PRIORITY -> queueService.findByName(
                    Type.BINARY_HEAP_PRIORITY_QUEUE,
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

            case UNSORTED_VECTOR_PRIORITY -> queueService.size(
                    Type.UNSORTED_VECTOR_PRIORITY_QUEUE,
                    name
            );

            case SORTED_LINKED_LIST_PRIORITY -> queueService.size(
                    Type.SORTED_LINKED_LIST_PRIORITY_QUEUE,
                    name
            );

            case UNSORTED_DOUBLY_LINKED_LIST_PRIORITY -> queueService.size(
                    Type.UNSORTED_DOUBLY_LINKED_LIST_PRIORITY_QUEUE,
                    name
            );

            case BINARY_HEAP_PRIORITY -> queueService.size(
                    Type.BINARY_HEAP_PRIORITY_QUEUE,
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

            case UNSORTED_VECTOR_PRIORITY -> queueService.isEmpty(
                    Type.UNSORTED_VECTOR_PRIORITY_QUEUE,
                    name
            );

            case SORTED_LINKED_LIST_PRIORITY -> queueService.isEmpty(
                    Type.SORTED_LINKED_LIST_PRIORITY_QUEUE,
                    name
            );

            case UNSORTED_DOUBLY_LINKED_LIST_PRIORITY -> queueService.isEmpty(
                    Type.UNSORTED_DOUBLY_LINKED_LIST_PRIORITY_QUEUE,
                    name
            );

            case BINARY_HEAP_PRIORITY -> queueService.isEmpty(
                    Type.BINARY_HEAP_PRIORITY_QUEUE,
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

            case UNSORTED_VECTOR_PRIORITY,
                 UNSORTED_DOUBLY_LINKED_LIST_PRIORITY,
                 SORTED_LINKED_LIST_PRIORITY,
                 BINARY_HEAP_PRIORITY -> throw new IllegalArgumentException("Not implemented");
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

            case UNSORTED_VECTOR_PRIORITY -> queueService.enqueue(
                    Type.UNSORTED_VECTOR_PRIORITY_QUEUE,
                    name,
                    element
            );

            case SORTED_LINKED_LIST_PRIORITY -> queueService.enqueue(
                    Type.SORTED_LINKED_LIST_PRIORITY_QUEUE,
                    name,
                    element
            );

            case UNSORTED_DOUBLY_LINKED_LIST_PRIORITY -> queueService.enqueue(
                    Type.UNSORTED_DOUBLY_LINKED_LIST_PRIORITY_QUEUE,
                    name,
                    element
            );

            case BINARY_HEAP_PRIORITY -> queueService.enqueue(
                    Type.BINARY_HEAP_PRIORITY_QUEUE,
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

            case UNSORTED_VECTOR_PRIORITY -> queueService.dequeue(
                    Type.UNSORTED_VECTOR_PRIORITY_QUEUE,
                    name
            );

            case SORTED_LINKED_LIST_PRIORITY -> queueService.dequeue(
                    Type.SORTED_LINKED_LIST_PRIORITY_QUEUE,
                    name
            );

            case UNSORTED_DOUBLY_LINKED_LIST_PRIORITY -> queueService.dequeue(
                    Type.UNSORTED_DOUBLY_LINKED_LIST_PRIORITY_QUEUE,
                    name
            );

            case BINARY_HEAP_PRIORITY -> queueService.dequeue(
                    Type.BINARY_HEAP_PRIORITY_QUEUE,
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

            case UNSORTED_VECTOR_PRIORITY -> queueService.peek(
                    Type.UNSORTED_VECTOR_PRIORITY_QUEUE,
                    name
            );

            case SORTED_LINKED_LIST_PRIORITY -> queueService.peek(
                    Type.SORTED_LINKED_LIST_PRIORITY_QUEUE,
                    name
            );

            case UNSORTED_DOUBLY_LINKED_LIST_PRIORITY -> queueService.peek(
                    Type.UNSORTED_DOUBLY_LINKED_LIST_PRIORITY_QUEUE,
                    name
            );

            case BINARY_HEAP_PRIORITY -> queueService.peek(
                    Type.BINARY_HEAP_PRIORITY_QUEUE,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }
}
