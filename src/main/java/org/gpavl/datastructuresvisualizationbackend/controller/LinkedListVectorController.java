package org.gpavl.datastructuresvisualizationbackend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.vector.VectorCreateRequest;
import org.gpavl.datastructuresvisualizationbackend.service.vector.LinkedListVectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/linked-list-vector")
@AllArgsConstructor
public class LinkedListVectorController {

    private LinkedListVectorService linkedListService;

    @PostMapping("/create")
    public ResponseEntity<Response> createLinkedListVector(
            @Valid @RequestBody VectorCreateRequest arrayVectorCreationRequest
    ) {
        Response response = linkedListService.create(arrayVectorCreationRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<Response> findLinkedListVector(@PathVariable String name) {
        Response response = linkedListService.findByName(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/size/{name}")
    public ResponseEntity<Response> size(@PathVariable String name) {
        Response response = linkedListService.size(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/is-empty/{name}")
    public ResponseEntity<Response> isEmpty(@PathVariable String name) {
        Response response = linkedListService.isEmpty(name);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/clear/{name}")
    public ResponseEntity<Response> clear(@PathVariable String name) {
        Response response = linkedListService.clear(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{name}/{index}")
    public ResponseEntity<Response> get(@PathVariable String name, @PathVariable int index) {
        Response response = linkedListService.get(name, index);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/set/{name}/{index}/{element}")
    public ResponseEntity<Response> set(@PathVariable String name,
                                        @PathVariable int index,
                                        @PathVariable String element) {
        Response response = linkedListService.set(name, index, element);
        return ResponseEntity.ok(response);
    }

    @PatchMapping ("/add/{name}/{element}")
    public ResponseEntity<Response> add(@PathVariable String name, @PathVariable String element) {
        Response response = linkedListService.add(name, element);
        return ResponseEntity.ok(response);
    }

    @PatchMapping ("/insert-at/{name}/{index}/{element}")
    public ResponseEntity<Response> insertAt(@PathVariable String name,
                                             @PathVariable int index,
                                             @PathVariable String element) {
        Response response = linkedListService.insertAt(name, index, element);
        return ResponseEntity.ok(response);
    }

    @PatchMapping ("/remove-at/{name}/{index}")
    public ResponseEntity<Response> removeAt(@PathVariable String name,
                                             @PathVariable int index) {
        Response response = linkedListService.removeAt(name, index);
        return ResponseEntity.ok(response);
    }
}
