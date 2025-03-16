package org.gpavl.datastructuresvisualizationbackend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.arrayvector.ArrayVectorCreateRequest;
import org.gpavl.datastructuresvisualizationbackend.model.arrayvector.ArrayVectorStateResponse;
import org.gpavl.datastructuresvisualizationbackend.service.ArrayVectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/array-vector")
@AllArgsConstructor
public class ArrayVectorController {

    private ArrayVectorService arrayVectorService;

    @PostMapping("/create")
    public <T> ResponseEntity<ArrayVectorStateResponse<T>> createArrayVector(
            @Valid @RequestBody ArrayVectorCreateRequest<T> arrayVectorCreationRequest
    ) {
        ArrayVectorStateResponse<T> response = arrayVectorService.createArrayVector(arrayVectorCreationRequest);
        return ResponseEntity.ok(response);
    }
}
