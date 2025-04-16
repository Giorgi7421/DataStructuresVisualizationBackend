package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.priorityqueue.*;
import org.gpavl.datastructuresvisualizationbackend.model.queue.ArrayQueue;
import org.gpavl.datastructuresvisualizationbackend.model.queue.LinkedListQueue;
import org.gpavl.datastructuresvisualizationbackend.model.queue.QueueType;
import org.gpavl.datastructuresvisualizationbackend.service.PriorityQueueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/priority-queue")
@AllArgsConstructor
public class PriorityQueueController {

    private PriorityQueueService priorityQueueService;

    @PostMapping("/create/{type}/{name}")
    public ResponseEntity<Response> create(
            @PathVariable PriorityQueueType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case UNSORTED_VECTOR -> priorityQueueService.create(
                    Type.UNSORTED_VECTOR_PRIORITY_QUEUE,
                    name,
                    new UnsortedVectorPriorityQueue()
            );

            case SORTED_LINKED_LIST -> priorityQueueService.create(
                    Type.SORTED_LINKED_LIST_PRIORITY_QUEUE,
                    name,
                    new SortedLinkedListPriorityQueue()
            );

            case UNSORTED_DOUBLY_LINKED_LIST -> priorityQueueService.create(
                    Type.UNSORTED_DOUBLY_LINKED_LIST_PRIORITY_QUEUE,
                    name,
                    new UnsortedDoublyLinkedListPriorityQueue()
            );

            case BINARY_HEAP -> priorityQueueService.create(
                    Type.BINARY_HEAP_PRIORITY_QUEUE,
                    name,
                    new BinaryHeapPriorityQueue()
            );
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{type}/{name}")
    public ResponseEntity<Response> find(
            @PathVariable PriorityQueueType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case UNSORTED_VECTOR -> priorityQueueService.findByName(
                    Type.UNSORTED_VECTOR_PRIORITY_QUEUE,
                    name
            );

            case SORTED_LINKED_LIST -> priorityQueueService.findByName(
                    Type.SORTED_LINKED_LIST_PRIORITY_QUEUE,
                    name
            );

            case UNSORTED_DOUBLY_LINKED_LIST -> priorityQueueService.findByName(
                    Type.UNSORTED_DOUBLY_LINKED_LIST_PRIORITY_QUEUE,
                    name
            );

            case BINARY_HEAP -> priorityQueueService.findByName(
                    Type.BINARY_HEAP_PRIORITY_QUEUE,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/size/{type}/{name}")
    public ResponseEntity<Response> size(
            @PathVariable PriorityQueueType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case UNSORTED_VECTOR -> priorityQueueService.size(
                    Type.UNSORTED_VECTOR_PRIORITY_QUEUE,
                    name
            );

            case SORTED_LINKED_LIST -> priorityQueueService.size(
                    Type.SORTED_LINKED_LIST_PRIORITY_QUEUE,
                    name
            );

            case UNSORTED_DOUBLY_LINKED_LIST -> priorityQueueService.size(
                    Type.UNSORTED_DOUBLY_LINKED_LIST_PRIORITY_QUEUE,
                    name
            );

            case BINARY_HEAP -> priorityQueueService.size(
                    Type.BINARY_HEAP_PRIORITY_QUEUE,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/is-empty/{type}/{name}")
    public ResponseEntity<Response> isEmpty(
            @PathVariable PriorityQueueType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case UNSORTED_VECTOR -> priorityQueueService.isEmpty(
                    Type.UNSORTED_VECTOR_PRIORITY_QUEUE,
                    name
            );

            case SORTED_LINKED_LIST -> priorityQueueService.isEmpty(
                    Type.SORTED_LINKED_LIST_PRIORITY_QUEUE,
                    name
            );

            case UNSORTED_DOUBLY_LINKED_LIST -> priorityQueueService.isEmpty(
                    Type.UNSORTED_DOUBLY_LINKED_LIST_PRIORITY_QUEUE,
                    name
            );

            case BINARY_HEAP -> priorityQueueService.isEmpty(
                    Type.BINARY_HEAP_PRIORITY_QUEUE,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/enqueue/{type}/{name}/{element}/{priority}")
    public ResponseEntity<Response> enqueue(
            @PathVariable PriorityQueueType type,
            @PathVariable String name,
            @PathVariable String element,
            @PathVariable int priority
    ) {
        Response response = switch (type) {
            case UNSORTED_VECTOR -> priorityQueueService.enqueue(
                    Type.UNSORTED_VECTOR_PRIORITY_QUEUE,
                    name,
                    element,
                    priority
            );

            case SORTED_LINKED_LIST -> priorityQueueService.enqueue(
                    Type.SORTED_LINKED_LIST_PRIORITY_QUEUE,
                    name,
                    element,
                    priority
            );

            case UNSORTED_DOUBLY_LINKED_LIST -> priorityQueueService.enqueue(
                    Type.UNSORTED_DOUBLY_LINKED_LIST_PRIORITY_QUEUE,
                    name,
                    element,
                    priority
            );

            case BINARY_HEAP -> priorityQueueService.enqueue(
                    Type.BINARY_HEAP_PRIORITY_QUEUE,
                    name,
                    element,
                    priority
            );
        };

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/dequeue/{type}/{name}")
    public ResponseEntity<Response> dequeue(
            @PathVariable PriorityQueueType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case UNSORTED_VECTOR -> priorityQueueService.dequeue(
                    Type.UNSORTED_VECTOR_PRIORITY_QUEUE,
                    name
            );

            case SORTED_LINKED_LIST -> priorityQueueService.dequeue(
                    Type.SORTED_LINKED_LIST_PRIORITY_QUEUE,
                    name
            );

            case UNSORTED_DOUBLY_LINKED_LIST -> priorityQueueService.dequeue(
                    Type.UNSORTED_DOUBLY_LINKED_LIST_PRIORITY_QUEUE,
                    name
            );

            case BINARY_HEAP -> priorityQueueService.dequeue(
                    Type.BINARY_HEAP_PRIORITY_QUEUE,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }

    @GetMapping("/peek/{type}/{name}")
    public ResponseEntity<Response> peek(
            @PathVariable PriorityQueueType type,
            @PathVariable String name
    ) {
        Response response = switch (type) {
            case UNSORTED_VECTOR -> priorityQueueService.peek(
                    Type.UNSORTED_VECTOR_PRIORITY_QUEUE,
                    name
            );

            case SORTED_LINKED_LIST -> priorityQueueService.peek(
                    Type.SORTED_LINKED_LIST_PRIORITY_QUEUE,
                    name
            );

            case UNSORTED_DOUBLY_LINKED_LIST -> priorityQueueService.peek(
                    Type.UNSORTED_DOUBLY_LINKED_LIST_PRIORITY_QUEUE,
                    name
            );

            case BINARY_HEAP -> priorityQueueService.peek(
                    Type.BINARY_HEAP_PRIORITY_QUEUE,
                    name
            );
        };

        return ResponseEntity.ok(response);
    }
}
