package com.gabriel.vaultaPass.controller;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gabriel.vaultaPass.domain.user.User;
import com.gabriel.vaultaPass.dto.request.UpdateEmailRequestDTO;
import com.gabriel.vaultaPass.dto.request.UpdateLastnameRequestDTO;
import com.gabriel.vaultaPass.dto.request.UpdateNameRequestDTO;
import com.gabriel.vaultaPass.dto.request.UpdatePasswordRequestDTO;
import com.gabriel.vaultaPass.dto.request.UpdateUsernameRequestDTO;
import com.gabriel.vaultaPass.dto.request.UserRequestDTO;
import com.gabriel.vaultaPass.dto.response.UserResponseDTO;
import com.gabriel.vaultaPass.infra.security.AuthenticatedUser;
import com.gabriel.vaultaPass.infra.security.JwtUtil;
import com.gabriel.vaultaPass.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
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
                .body(userService.toResponse(user));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me(
        @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        User user = userService.findById(currentUser.getDomainUser().getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.toResponse(user));
    }

    @PutMapping("/me/name")
    public ResponseEntity<UserResponseDTO> updateName(
        @AuthenticationPrincipal AuthenticatedUser currentUser,
        @RequestBody @Valid UpdateNameRequestDTO request
    ) {
        User updated = userService.updateName(currentUser.getDomainUser().getId(), request.name());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.toResponse(updated));
    }

    @PutMapping("/me/lastname")
    public ResponseEntity<UserResponseDTO> updateLastname(
        @AuthenticationPrincipal AuthenticatedUser currentUser,
        @RequestBody @Valid UpdateLastnameRequestDTO request
    ) {
        User updated = userService.updateLastname(currentUser.getDomainUser().getId(), request.lastname());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.toResponse(updated));
    }

    @PutMapping("/me/username")
    public ResponseEntity<UserResponseDTO> updateUsername(
        @AuthenticationPrincipal AuthenticatedUser currentUser,
        @RequestBody @Valid UpdateUsernameRequestDTO request
    ) {
        User updated = userService.updateUsername(currentUser.getDomainUser().getId(), request.username());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.toResponse(updated));
    }

    @PutMapping("/me/email")
    public ResponseEntity<UserResponseDTO> updateEmail(
        @AuthenticationPrincipal AuthenticatedUser currentUser,
        @RequestBody @Valid UpdateEmailRequestDTO request
    ) {
        User updated = userService.updateEmail(
            currentUser.getDomainUser().getId(),
            request.email(),
            request.currentPassword()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.toResponse(updated));
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> updatePassword(
        @AuthenticationPrincipal AuthenticatedUser currentUser,
        @RequestBody @Valid UpdatePasswordRequestDTO request,
        HttpServletResponse response
    ) {
        User updated = userService.updatePassword(
            currentUser.getDomainUser().getId(),
            request.currentPassword(),
            request.newPassword()
        );

        reissueCookie(updated, response);
        return ResponseEntity.noContent().build();
}

    @PutMapping(value = "/me/profile-photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponseDTO> updateProfilePhoto(
        @AuthenticationPrincipal AuthenticatedUser currentUser,
        @RequestParam("file") MultipartFile file
    ) {
        User updated = userService.updateProfilePhoto(currentUser.getDomainUser().getId(), file);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.toResponse(updated));
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

    private void reissueCookie(User user, HttpServletResponse response) {
        String token = jwtUtil.generateToken(new AuthenticatedUser(user));

        ResponseCookie cookie = ResponseCookie.from("auth_token", token)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(Duration.ofHours(10))
            .sameSite("Strict")
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

}
