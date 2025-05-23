package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.WebBrowser;
import org.gpavl.datastructuresvisualizationbackend.service.WebBrowserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/web-browser")
@AllArgsConstructor
public class WebBrowserController {

    private WebBrowserService webBrowserService;

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @PostMapping("/create/{name}")
    public ResponseEntity<Response> create(@PathVariable String name) {
        Response response = webBrowserService.create(Type.WEB_BROWSER, name, new WebBrowser());
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/find/{name}")
    public ResponseEntity<Response> find(@PathVariable String name) {
        Response response = webBrowserService.findByName(Type.WEB_BROWSER, name);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @PatchMapping("/visit/{name}/{url}")
    public ResponseEntity<Response> visit(@PathVariable String name, @PathVariable String url) {
        Response response = webBrowserService.visit(name, url);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @PatchMapping("/back/{name}")
    public ResponseEntity<Response> back(@PathVariable String name) {
        Response response = webBrowserService.back(name);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @PatchMapping("/forward/{name}")
    public ResponseEntity<Response> visit(@PathVariable String name) {
        Response response = webBrowserService.forward(name);
        return ResponseEntity.ok(response);
    }
}
