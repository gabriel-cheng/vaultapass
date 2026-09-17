package com.gabriel.vaultaPass.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.vaultaPass.domain.credential.Credential;
import com.gabriel.vaultaPass.dto.request.CredentialRequestDTO;
import com.gabriel.vaultaPass.dto.request.CredentialUpdateRequestDTO;
import com.gabriel.vaultaPass.dto.request.UpdateEmailRequestDTO;
import com.gabriel.vaultaPass.dto.response.CredentialResponseDTO;
import com.gabriel.vaultaPass.dto.response.RevealedPasswordResponseDTO;
import com.gabriel.vaultaPass.infra.security.AuthenticatedUser;
import com.gabriel.vaultaPass.service.CredentialService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/credentials")
public class CredentialController {

    private final CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping
    public ResponseEntity<CredentialResponseDTO> register(
        @AuthenticationPrincipal AuthenticatedUser currentUser,
        @RequestBody @Valid CredentialRequestDTO request
    ) {
        Credential credential = credentialService.register(
            currentUser.getDomainUser().getId(),
            request.platformName(),
            request.login(),
            request.password(),
            request.email(),
            request.link(),
            request.description()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CredentialResponseDTO.fromDomain(credential));
    }

    @GetMapping
    public ResponseEntity<List<CredentialResponseDTO>> listAll(
        @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        List<CredentialResponseDTO> credentials = credentialService
            .listByUser(currentUser.getDomainUser().getId())
            .stream()
            .map(CredentialResponseDTO::fromDomain)
            .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(credentials);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CredentialResponseDTO> findById(
        @AuthenticationPrincipal AuthenticatedUser currentUser,
        @PathVariable String id
    ) {
        Credential credential = credentialService.findByIdAndUser(id, currentUser.getDomainUser().getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CredentialResponseDTO.fromDomain(credential));
    }

    @GetMapping("/{id}/password")
    public ResponseEntity<RevealedPasswordResponseDTO> revealPassword(
        @AuthenticationPrincipal AuthenticatedUser currentUser,
        @PathVariable String id
    ) {
        String password = credentialService.revealPassword(id, currentUser.getDomainUser().getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new RevealedPasswordResponseDTO(password));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CredentialResponseDTO> updateCredential(
        @AuthenticationPrincipal AuthenticatedUser currentUser,
        @PathVariable String id,
        @RequestBody CredentialUpdateRequestDTO request
    ) {
        Credential updated = credentialService.update(
            id,
            currentUser.getDomainUser().getId(),
            request.login(),
            request.password(),
            request.email(),
            request.link(),
            request.description()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CredentialResponseDTO.fromDomain(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCredential(
        @AuthenticationPrincipal AuthenticatedUser currentUser,
        @PathVariable String id
    ) {
        credentialService.delete(id, currentUser.getDomainUser().getId());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
