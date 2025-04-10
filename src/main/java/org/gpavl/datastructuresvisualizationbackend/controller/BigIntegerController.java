package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.BigInteger;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.service.BigIntegerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/big-integer")
@AllArgsConstructor
public class BigIntegerController {

    private BigIntegerService bigIntegerService;

    @PostMapping("/create/{name}/{number}")
    public ResponseEntity<Response> create(@PathVariable String name, @PathVariable int number) {
        Response response = bigIntegerService.create(Type.BIG_INTEGER, name, new BigInteger(number));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<Response> find(@PathVariable String name) {
        Response response = bigIntegerService.findByName(Type.BIG_INTEGER, name);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/add/{name}/{number}")
    public ResponseEntity<Response> add(@PathVariable String name, @PathVariable String number) {
        Response response = bigIntegerService.add(name, number);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/isGreaterThan/{name}/{number}")
    public ResponseEntity<Response> isGreaterThan(@PathVariable String name, @PathVariable String number) {
        Response response = bigIntegerService.isGreaterThan(name, number);
        return ResponseEntity.ok(response);
    }
}
