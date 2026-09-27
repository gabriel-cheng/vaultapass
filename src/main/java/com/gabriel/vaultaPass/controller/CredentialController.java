package com.gabriel.vaultaPass.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.vaultaPass.domain.credential.Credential;
import com.gabriel.vaultaPass.dto.request.credential.CredentialRequestDTO;
import com.gabriel.vaultaPass.dto.request.credential.UpdateCredentialDescriptionRequestDTO;
import com.gabriel.vaultaPass.dto.request.credential.UpdateCredentialEmailRequestDTO;
import com.gabriel.vaultaPass.dto.request.credential.UpdateCredentialLinkRequestDTO;
import com.gabriel.vaultaPass.dto.request.credential.UpdateCredentialLoginRequestDTO;
import com.gabriel.vaultaPass.dto.request.credential.UpdateCredentialPasswordRequestDTO;
import com.gabriel.vaultaPass.dto.request.credential.UpdateCredentialPlatformNameRequestDTO;
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

    @PutMapping("/{id}/platform-name")
    public ResponseEntity<CredentialResponseDTO> updatePlatformName(
        @AuthenticationPrincipal  AuthenticatedUser currentUser,
        @PathVariable String id,
        @RequestBody @Valid UpdateCredentialPlatformNameRequestDTO request
    ) {
        Credential updated = credentialService.updatePlatformName(
            id, currentUser.getDomainUser().getId(), request.platformName()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CredentialResponseDTO.fromDomain(updated));
    }

    @PutMapping("/{id}/login")
    public ResponseEntity<CredentialResponseDTO> updateLogin(
        @AuthenticationPrincipal AuthenticatedUser currentUser,
        @PathVariable String id,
        @RequestBody @Valid UpdateCredentialLoginRequestDTO request
    ) {
        Credential updated = credentialService.updateLogin(
            id, currentUser.getDomainUser().getId(), request.login()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CredentialResponseDTO.fromDomain(updated));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<CredentialResponseDTO> updatePassword(
        @AuthenticationPrincipal AuthenticatedUser currentUser,
        @PathVariable String id,
        @RequestBody @Valid UpdateCredentialPasswordRequestDTO request
    ) {
        Credential updated = credentialService.updatePassword(
            id, currentUser.getDomainUser().getId(), request.password()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CredentialResponseDTO.fromDomain(updated));
    }

    @PutMapping("/{id}/email")
    public ResponseEntity<CredentialResponseDTO> updateEmail(
        @AuthenticationPrincipal AuthenticatedUser currentUser,
        @PathVariable String id,
        @RequestBody @Valid UpdateCredentialEmailRequestDTO request
    ) {
        Credential updated = credentialService.updateEmail(
            id, currentUser.getDomainUser().getId(), request.email()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CredentialResponseDTO.fromDomain(updated));
    }

    @PutMapping("/{id}/link")
    public ResponseEntity<CredentialResponseDTO> updateLink(
        @AuthenticationPrincipal AuthenticatedUser currentUser,
        @PathVariable String id,
        @RequestBody @Valid UpdateCredentialLinkRequestDTO request
    ) {
        Credential updated = credentialService.updateLink(
            id, currentUser.getDomainUser().getId(), request.link()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CredentialResponseDTO.fromDomain(updated));
    }

    @PutMapping("/{id}/description")
    public ResponseEntity<CredentialResponseDTO> updateDescription(
        @AuthenticationPrincipal AuthenticatedUser currentUser,
        @PathVariable String id,
        @RequestBody @Valid UpdateCredentialDescriptionRequestDTO request
    ) {
        Credential updated = credentialService.updateDescription(
            id, currentUser.getDomainUser().getId(), request.description()
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
