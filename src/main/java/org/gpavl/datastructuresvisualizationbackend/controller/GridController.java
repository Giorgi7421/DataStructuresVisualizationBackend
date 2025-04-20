package org.gpavl.datastructuresvisualizationbackend.controller;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.model.Grid;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.service.GridService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/grid")
@AllArgsConstructor
public class GridController {

    private GridService gridService;

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @PostMapping("/create/{name}/{numRows}/{numColumns}")
    public ResponseEntity<Response> create(
            @PathVariable String name,
            @PathVariable int numRows,
            @PathVariable int numColumns
    ) {
        Response response = gridService.create(Type.GRID, name, new Grid(numRows, numColumns));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/find/{name}")
    public ResponseEntity<Response> find(@PathVariable String name) {
        Response response = gridService.findByName(Type.GRID, name);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/inBounds/{name}/{row}/{column}")
    public ResponseEntity<Response> inBounds(
            @PathVariable String name,
            @PathVariable int row,
            @PathVariable int column
    ) {
        Response response = gridService.inBounds(name, row, column);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/numRows/{name}")
    public ResponseEntity<Response> numRows(@PathVariable String name) {
        Response response = gridService.numRows(name);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/numColumns/{name}")
    public ResponseEntity<Response> numColumns(@PathVariable String name) {
        Response response = gridService.numColumns(name);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/get/{name}/{row}/{column}")
    public ResponseEntity<Response> get(
            @PathVariable String name,
            @PathVariable int row,
            @PathVariable int column
    ) {
        Response response = gridService.get(name, row, column);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/set/{name}/{row}/{column}/{element}")
    public ResponseEntity<Response> set(
            @PathVariable String name,
            @PathVariable int row,
            @PathVariable int column,
            @PathVariable String element
    ) {
        Response response = gridService.set(name, row, column, element);
        return ResponseEntity.ok(response);
    }
}
