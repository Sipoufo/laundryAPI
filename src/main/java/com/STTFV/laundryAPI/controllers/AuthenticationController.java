package com.STTFV.laundryAPI.controllers;

import com.STTFV.laundryAPI.dto.requests.*;
import com.STTFV.laundryAPI.dto.responses.JwtAuthenticationResponse;
import com.STTFV.laundryAPI.dto.responses.UserMachineDetails;
import com.STTFV.laundryAPI.entities.PasswordResetToken;
import com.STTFV.laundryAPI.entities.User;
import com.STTFV.laundryAPI.exceptions.BadRequestException;
import com.STTFV.laundryAPI.services.AuthenticationService;
import com.STTFV.laundryAPI.services.EmailService;
import com.STTFV.laundryAPI.utils.HttpUtils;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final EmailService emailService;

    private final Environment env;

    @Value("${backend.base.url}")
    private String baseUrl;

    @PostMapping("/signup")
    @Operation(summary = "User sign up", tags = "Auth")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User Created"),
            @ApiResponse(responseCode = "400", description = "When email already exists"),
            @ApiResponse(responseCode = "500", description = "When server error")
    })
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid SignUpRequest signUpRequest, HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String ipAddress = HttpUtils.getClientIp();

        UserMachineDetails userMachineDetails = UserMachineDetails
                .builder()
                .ipAddress(ipAddress)
                .browser(userAgent.getBrowser().getName())
                .operatingSystem(userAgent.getOperatingSystem().getName())
                .build();
        return new ResponseEntity<>(authenticationService.signUp(signUpRequest, userMachineDetails), HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    @Operation(summary = "User sign in", tags = "Auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "When user not found or incorrect password"),
            @ApiResponse(responseCode = "500", description = "When server error")
    })
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SigninRequest loginRequest, HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String ipAddress = HttpUtils.getClientIp();
        UserMachineDetails userMachineDetails = UserMachineDetails
                .builder()
                .ipAddress(ipAddress)
                .browser(userAgent.getBrowser().getName())
                .operatingSystem(userAgent.getOperatingSystem().getName())
                .build();
        return ResponseEntity.ok(authenticationService.signIn(loginRequest, userMachineDetails));
    }

    @PostMapping("/refresh")
    @Operation(summary = "User refresh token", tags = "Auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "403", description = "When refresh token don't exists"),
            @ApiResponse(responseCode = "500", description = "When server error")
    })
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Send forgot password email", tags = "Auth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "404", description = "When user with provided email does not exist"),
            @ApiResponse(responseCode = "500", description = "When server error")
    })
    public ResponseEntity<Void> forgotPassword(@RequestParam String email) {

        PasswordResetToken password = authenticationService.forgotPassword(email);
        ForgotPasswordEmail forgotPasswordEmail = ForgotPasswordEmail.builder().to(password.getUser().getEmail()).subject("Forgot password email").url(env.getProperty("frontend.url") + "/user/resetPassword?token=" + password.getToken()).baseUrl(baseUrl).build();
        Context context = new Context();
        context.setVariable("data", forgotPasswordEmail);
        emailService.sendEmailWithHtmlTemplate(forgotPasswordEmail, "emails/auth/forgot-password", context);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/reset-password/{token}")
    @Operation(summary = "Validate reset token", tags = "Auth")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "When the reset token does not exist or is expired"),
            @ApiResponse(responseCode = "500", description = "When server error")
    })
    public ResponseEntity<Void> validateResetToken(@PathVariable String token) {
        final boolean valid = authenticationService.isResetTokenValid(token);
        if (valid) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new BadRequestException("Reset token invalid");
        }
    }

    @PatchMapping("/reset-password")
    @Operation(summary = "Reset user password", tags = "Auth")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "When payload is invalid"),
            @ApiResponse(responseCode = "500", description = "When server error")
    })
    public ResponseEntity<User> resetPassword(@Valid @RequestBody ForgotPassword forgotPassword) {
        final boolean valid = authenticationService.isResetTokenValid(forgotPassword.getToken());
        if (valid) {
            return ResponseEntity.ok(authenticationService.resetPassword(forgotPassword));
        } else {
            throw new BadRequestException("token invalid");
        }

    }
}