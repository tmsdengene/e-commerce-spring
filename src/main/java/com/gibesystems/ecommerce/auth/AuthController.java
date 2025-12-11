package com.gibesystems.ecommerce.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.gibesystems.ecommerce.auth.dto.AuthResponseDTO;
import com.gibesystems.ecommerce.auth.dto.LoginRequestDTO;
import com.gibesystems.ecommerce.auth.dto.RegisterRequestDTO;
import com.gibesystems.ecommerce.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication operations")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Authenticate user credentials",
        description = "Authenticates the user with email and password, and returns a JWT token upon success.",
        operationId = "loginUser",
        tags = {"Authentication"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Login request with user credentials",
            required = true,
            content = @Content(
                schema = @Schema(implementation = LoginRequestDTO.class)
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Authentication successful. JWT token returned.",
                content = @Content(
                    schema = @Schema(implementation = AuthResponseDTO.class)
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Invalid credentials",
                content = @Content
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = @Content
            )
        }
    )
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO request) {
        var response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
        summary = "Register a new user",
        description = "Registers a new user.",
        operationId = "registerUser",
        tags = {"Authentication"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Registration request containing user details",
            required = true,
            content = @Content(
                schema = @Schema(implementation = RegisterRequestDTO.class)
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Registration successful. User created."
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid input data",
                content = @Content
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = @Content
            )
        }
    )
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDTO request) {
        var userDTO = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @PostMapping(value = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Refresh JWT token",
        description = "Refreshes the JWT token using a valid refresh token.",
        operationId = "refreshToken",
        tags = {"Authentication"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Refresh token string",
            required = true,
            content = @Content(
                schema = @Schema(type = "string")
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Token refreshed successfully."
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid refresh token",
                content = @Content
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = @Content
            )
        }
    )
    public ResponseEntity<?> refreshToken(@RequestBody String refreshToken) {
        var token = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(token);
    }

    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Logout user",
        description = "Logs out the user and invalidates the token.",
        operationId = "logoutUser",
        tags = {"Authentication"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "JWT token to invalidate",
            required = true,
            content = @Content(
                schema = @Schema(type = "string")
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Logout successful."
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = @Content
            )
        }
    )
    public ResponseEntity<?> logout(@RequestBody String token) {
        authService.logout(token);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/validate-token", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Validate JWT token",
        description = "Validates the JWT token.",
        operationId = "validateToken",
        tags = {"Authentication"},
        parameters = {
            @Parameter(
                name = "token",
                description = "JWT token to validate",
                required = true
            )
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Token validation result."
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid token",
                content = @Content
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = @Content
            )
        }
    )
    public ResponseEntity<?> validateToken(@RequestParam String token) {
        boolean valid = authService.validateToken(token);
        return ResponseEntity.ok(valid);
    }

    @GetMapping(value = "/user-id", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Extract user ID from JWT token",
        description = "Extracts the user ID from the JWT token.",
        operationId = "extractUserId",
        tags = {"Authentication"},
        parameters = {
            @Parameter(
                name = "token",
                description = "JWT token",
                required = true
            )
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "User ID extracted successfully."
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid token",
                content = @Content
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = @Content
            )
        }
    )
    public ResponseEntity<?> extractUserId(@RequestParam String token) {
        String userId = authService.extractUserIdFromToken(token);
        return ResponseEntity.ok(userId);
    }
}