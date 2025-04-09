package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.vector.VectorType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/web-browser")
@AllArgsConstructor
public class WebBrowserController {

    private WebBrowserService webBrowserService;

    @PostMapping("/create/{name}")
    public ResponseEntity<Response> create(@PathVariable String name) {
        Response response = webBrowserService.create(name);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<Response> find(@PathVariable String name) {
        Response response = webBrowserService.find(name);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/visit/{name}/{url}")
    public ResponseEntity<Response> visit(@PathVariable String name, @PathVariable String url) {
        Response response = webBrowserService.visit(name, url);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/back/{name}")
    public ResponseEntity<Response> back(@PathVariable String name) {
        Response response = webBrowserService.back(name);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/forward/{name}")
    public ResponseEntity<Response> visit(@PathVariable String name) {
        Response response = webBrowserService.forward(name);
        return ResponseEntity.ok(response);
    }
}
