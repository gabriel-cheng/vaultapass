package com.gabriel.vaultaPass.controller;

import java.time.Duration;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.vaultaPass.domain.user.User;
import com.gabriel.vaultaPass.dto.request.LoginRequestDTO;
import com.gabriel.vaultaPass.dto.response.UserResponseDTO;
import com.gabriel.vaultaPass.exception.AuthenticationFailedException;
import com.gabriel.vaultaPass.exception.ErrorMessageEnum;
import com.gabriel.vaultaPass.infra.security.AuthenticatedUser;
import com.gabriel.vaultaPass.infra.security.JwtUtil;
import com.gabriel.vaultaPass.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public AuthController(
        AuthenticationManager authenticationManager,
        JwtUtil jwtUtil,
        UserDetailsService userDetailsService,
        UserService userService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me(
        @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        User user = currentUser.getDomainUser();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.toResponse(user));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(
        @RequestBody @Valid LoginRequestDTO request,
        HttpServletResponse response
    ) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
        }catch(BadCredentialsException ex) {
            throw new AuthenticationFailedException(ErrorMessageEnum.AUTHENTICATION_FAILED.getMessage());
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        String token = jwtUtil.generateToken(userDetails);

        ResponseCookie cookie = ResponseCookie.from("auth_token", token)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(Duration.ofHours(10))
            .sameSite("Strict")
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        AuthenticatedUser authenticatedUser = (AuthenticatedUser) userDetails;
        User user = authenticatedUser.getDomainUser();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.toResponse(user));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("authe_token", "")
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(0)
            .sameSite("Strict")
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
