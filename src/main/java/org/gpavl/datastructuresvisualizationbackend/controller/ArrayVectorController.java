package org.gpavl.datastructuresvisualizationbackend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.arrayvector.ArrayVectorCreateRequest;
import org.gpavl.datastructuresvisualizationbackend.model.arrayvector.ArrayVectorStateResponse;
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
        ArrayVectorStateResponse response = arrayVectorService.createArrayVector(arrayVectorCreationRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<ArrayVectorStateResponse> findArrayVector(@PathVariable String name) {
        ArrayVectorStateResponse response = arrayVectorService.findByName(name);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/add/{name}/{value}")
    public ResponseEntity<ArrayVectorStateResponse> add(@PathVariable String name, @PathVariable String value) {
        ArrayVectorStateResponse response = arrayVectorService.add(name, value);
        return ResponseEntity.ok(response);
    }
}
