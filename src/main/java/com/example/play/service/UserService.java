package com.example.play.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.play.dto.Response;
import com.example.play.dto.UserResponse;
import com.example.play.model.User;
import com.example.play.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity<Response<List<UserResponse>>> getAllUsers() {

        List<UserResponse> users = userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

        return ResponseEntity.ok(
                Response.success(users, "Successfully fetched users")
        );
    }

    public ResponseEntity<Response<UserResponse>> getUserById(String id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Response.error(null, "User not found"));
        }

        return ResponseEntity.ok(
                Response.success(
                        mapToResponse(optionalUser.get()),
                        "Successfully fetched user"
                )
        );
    }

    public ResponseEntity<Response<Void>> deleteUser(String id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Response.error(null, "User not found"));
        }

        userRepository.delete(optionalUser.get());

        return ResponseEntity.ok(
                Response.success(null, "User deleted successfully")
        );
    }

    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}