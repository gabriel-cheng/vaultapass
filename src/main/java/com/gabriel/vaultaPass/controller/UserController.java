package com.gabriel.vaultaPass.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.vaultaPass.domain.user.User;
import com.gabriel.vaultaPass.dto.request.UpdateEmailRequestDTO;
import com.gabriel.vaultaPass.dto.request.UpdatePasswordRequestDTO;
import com.gabriel.vaultaPass.dto.request.UserRequestDTO;
import com.gabriel.vaultaPass.dto.response.UserResponseDTO;
import com.gabriel.vaultaPass.infra.security.AuthenticatedUser;
import com.gabriel.vaultaPass.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> register(
        @RequestBody @Valid UserRequestDTO request
    ) {
        User user = userService.register(
            request.name(),
            request.lastname(),
            request.username(),
            request.email(),
            request.password()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponseDTO.fromDomain(user));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me(
        @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        User user = userService.findById(currentUser.getDomainUser().getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserResponseDTO.fromDomain(user));
    }

    @PatchMapping("/me/email")
    public ResponseEntity<UserResponseDTO> updateEmail(
        @AuthenticationPrincipal AuthenticatedUser currentUser,
        @RequestBody @Valid UpdateEmailRequestDTO request
    ) {
        User updated = userService.updateEmail(currentUser.getDomainUser().getId(), request.email());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserResponseDTO.fromDomain(updated));
    }

    @PatchMapping("/me/password")
    public ResponseEntity<Void> updatePassword(
        @AuthenticationPrincipal AuthenticatedUser currentUser,
        @RequestBody @Valid UpdatePasswordRequestDTO request
    ) {
        userService.updatePassword(currentUser.getDomainUser().getId(), request.password());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUser(
        @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        userService.delete(currentUser.getDomainUser().getId());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
