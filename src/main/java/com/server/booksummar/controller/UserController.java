package com.server.booksummar.controller;

import com.server.booksummar.dto.request.UserRequest;
import com.server.booksummar.dto.response.UserResponse;
import com.server.booksummar.repository.UserRepository;
import com.server.booksummar.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest userRequest) {
        UserResponse userResponse = userService.create(userRequest);
        return ResponseEntity.created(URI.create("/user" + userResponse.getId())).body(userResponse);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<UserResponse> update(@RequestBody @Valid UserRequest userRequest, @PathVariable UUID id) {
        return ResponseEntity.ok(userService.update(userRequest, id));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable UUID id) {
        userService.delete(id);
        ResponseEntity.ok();
    }
}