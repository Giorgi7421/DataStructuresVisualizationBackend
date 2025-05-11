package org.gpavl.datastructuresvisualizationbackend.controller;

import org.gpavl.datastructuresvisualizationbackend.model.DataStructureInfo;
import org.gpavl.datastructuresvisualizationbackend.model.UserInfo;
import org.gpavl.datastructuresvisualizationbackend.security.RegisterRequest;
import org.gpavl.datastructuresvisualizationbackend.security.UserService;
import org.gpavl.datastructuresvisualizationbackend.service.DataStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DataStructureService dataStructureService;

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/get-all-data-structures")
    public ResponseEntity<List<DataStructureInfo>> getAllDataStructures() {
        List<DataStructureInfo> dataStructureInfoList = userService.getAllUserDataStructures();
        return ResponseEntity.ok(dataStructureInfoList);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/me")
    public ResponseEntity<UserInfo> registerUser() {
        UserInfo userInfo = userService.me();
        return ResponseEntity.ok(userInfo);
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Void> deleteDataStructure(@PathVariable String name) {
        dataStructureService.deleteDataStructure(name);
        return ResponseEntity.noContent().build();
    }
}
