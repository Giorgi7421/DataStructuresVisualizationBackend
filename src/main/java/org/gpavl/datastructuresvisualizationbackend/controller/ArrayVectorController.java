package org.gpavl.datastructuresvisualizationbackend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.vector.VectorCreateRequest;
import org.gpavl.datastructuresvisualizationbackend.service.ArrayVectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/array-vector")
@AllArgsConstructor
public class ArrayVectorController {

    private ArrayVectorService arrayVectorService;

    @PostMapping("/create")
    public ResponseEntity<Response> createArrayVector(
            @Valid @RequestBody VectorCreateRequest arrayVectorCreationRequest
    ) {
        //TODO limit value length
        Response response = arrayVectorService.createArrayVector(arrayVectorCreationRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<Response> findArrayVector(@PathVariable String name) {
        Response response = arrayVectorService.findByName(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/size/{name}")
    public ResponseEntity<Response> size(@PathVariable String name) {
        Response response = arrayVectorService.size(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/is-empty/{name}")
    public ResponseEntity<Response> isEmpty(@PathVariable String name) {
        Response response = arrayVectorService.isEmpty(name);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/clear/{name}")
    public ResponseEntity<Response> clear(@PathVariable String name) {
        Response response = arrayVectorService.clear(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{name}/{index}")
    public ResponseEntity<Response> get(@PathVariable String name, @PathVariable int index) {
        Response response = arrayVectorService.get(name, index);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/set/{name}/{index}/{element}")
    public ResponseEntity<Response> set(@PathVariable String name,
                                                        @PathVariable int index,
                                                        @PathVariable String element) {
        Response response = arrayVectorService.set(name, index, element);
        return ResponseEntity.ok(response);
    }

    @PatchMapping ("/add/{name}/{element}")
    public ResponseEntity<Response> add(@PathVariable String name, @PathVariable String element) {
        Response response = arrayVectorService.add(name, element);
        return ResponseEntity.ok(response);
        //TODO limit value length
    }

    @PatchMapping ("/insert-at/{name}/{index}/{element}")
    public ResponseEntity<Response> insertAt(@PathVariable String name,
                                                             @PathVariable int index,
                                                             @PathVariable String element) {
        Response response = arrayVectorService.insertAt(name, index, element);
        return ResponseEntity.ok(response);
    }

    @PatchMapping ("/remove-at/{name}/{index}")
    public ResponseEntity<Response> removeAt(@PathVariable String name,
                                                             @PathVariable int index) {
        Response response = arrayVectorService.removeAt(name, index);
        return ResponseEntity.ok(response);
    }
}

//TODO think about api parameters vs request body
//TODO ? add from what value the variable got assigned from in memory steps