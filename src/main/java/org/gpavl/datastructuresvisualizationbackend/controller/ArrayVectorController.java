package org.gpavl.datastructuresvisualizationbackend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.vector.arrayvector.ArrayVectorCreateRequest;
import org.gpavl.datastructuresvisualizationbackend.model.vector.arrayvector.ArrayVectorStateResponse;
import org.gpavl.datastructuresvisualizationbackend.service.ArrayVectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/array-vector")
@AllArgsConstructor
public class ArrayVectorController {

    private ArrayVectorService arrayVectorService;

    @PostMapping("/create")
    public ResponseEntity<ArrayVectorStateResponse> createArrayVector(
            @Valid @RequestBody ArrayVectorCreateRequest arrayVectorCreationRequest
    ) {
        //TODO limit value length
        ArrayVectorStateResponse response = arrayVectorService.createArrayVector(arrayVectorCreationRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<ArrayVectorStateResponse> findArrayVector(@PathVariable String name) {
        ArrayVectorStateResponse response = arrayVectorService.findByName(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/size/{name}")
    public ResponseEntity<Integer> size(@PathVariable String name) {
        int response = arrayVectorService.size(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/is-empty/{name}")
    public ResponseEntity<Boolean> isEmpty(@PathVariable String name) {
        boolean response = arrayVectorService.isEmpty(name);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/clear/{name}")
    public ResponseEntity<ArrayVectorStateResponse> clear(@PathVariable String name) {
        ArrayVectorStateResponse response = arrayVectorService.clear(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{name}/{index}")
    public ResponseEntity<String> get(@PathVariable String name, @PathVariable int index) {
        String response = arrayVectorService.get(name, index);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/set/{name}/{index}/{element}")
    public ResponseEntity<ArrayVectorStateResponse> set(@PathVariable String name,
                                                        @PathVariable int index,
                                                        @PathVariable String element) {
        ArrayVectorStateResponse response = arrayVectorService.set(name, index, element);
        return ResponseEntity.ok(response);
    }

    @PatchMapping ("/add/{name}/{element}")
    public ResponseEntity<ArrayVectorStateResponse> add(@PathVariable String name, @PathVariable String element) {
        ArrayVectorStateResponse response = arrayVectorService.add(name, element);
        return ResponseEntity.ok(response);
        //TODO limit value length
    }

    @PatchMapping ("/insert-at/{name}/{index}/{element}")
    public ResponseEntity<ArrayVectorStateResponse> insertAt(@PathVariable String name,
                                                             @PathVariable int index,
                                                             @PathVariable String element) {
        ArrayVectorStateResponse response = arrayVectorService.insertAt(name, index, element);
        return ResponseEntity.ok(response);
    }

    @PatchMapping ("/remove-at/{name}/{index}")
    public ResponseEntity<ArrayVectorStateResponse> removeAt(@PathVariable String name,
                                                             @PathVariable int index) {
        ArrayVectorStateResponse response = arrayVectorService.removeAt(name, index);
        return ResponseEntity.ok(response);
    }
}

//TODO think about api parameters vs request body
//TODO ? add from what value the variable got assigned from in memory steps