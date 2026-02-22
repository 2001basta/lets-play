package com.example.play.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.play.dto.Response;
import com.example.play.dto.UserResponse;
import com.example.play.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Response<List<UserResponse>>>  getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<UserResponse>>  getUserById(@PathVariable String id){
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }
}
