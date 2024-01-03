package com.STTFV.laundryAPI.services;

import com.STTFV.laundryAPI.dto.requests.*;
import com.STTFV.laundryAPI.dto.responses.JwtAuthenticationResponse;
import com.STTFV.laundryAPI.dto.responses.UserMachineDetails;
import com.STTFV.laundryAPI.entities.PasswordResetToken;
import com.STTFV.laundryAPI.entities.RefreshToken;
import com.STTFV.laundryAPI.entities.User;
import com.STTFV.laundryAPI.exceptions.BadRequestException;
import com.STTFV.laundryAPI.exceptions.ResourceNotFoundException;
import com.STTFV.laundryAPI.exceptions.TokenRefreshException;
import com.STTFV.laundryAPI.repositories.PasswordTokenRepository;
import com.STTFV.laundryAPI.repositories.UserRepository;
import com.STTFV.laundryAPI.securities.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final PasswordTokenRepository passwordTokenRepository;

    public JwtAuthenticationResponse signUp(SignUpRequest request, UserMachineDetails userMachineDetails) {
        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName()).email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).build();
        user = userRepository.save(user);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        System.out.println(userMachineDetails.getOperatingSystem());
        System.out.println(userMachineDetails.getBrowser());
        System.out.println(userMachineDetails.getIpAddress());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken((User) authentication.getPrincipal());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUserId(), userMachineDetails);
        return JwtAuthenticationResponse.builder().token(jwt).user(user).refreshToken(refreshToken.getToken()).build();
    }

    public JwtAuthenticationResponse createUser(SignUpRequest request, UserMachineDetails userMachineDetails) {
        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName()).email(request.getEmail()).build();
        user = userRepository.save(user);

        String jwt = jwtUtils.generateJwtToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUserId(), userMachineDetails);
        return JwtAuthenticationResponse.builder().token(jwt).user(user).refreshToken(refreshToken.getToken()).build();

    }

    public JwtAuthenticationResponse signIn(SigninRequest request, UserMachineDetails userMachineDetails) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        var jwt = jwtUtils.generateJwtToken((User) authentication.getPrincipal());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUserId(), userMachineDetails);
        return JwtAuthenticationResponse.builder().token(jwt).user(user).refreshToken(refreshToken.getToken()).build();
    }

    public JwtAuthenticationResponse refreshToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map((user) -> {
                    String token = jwtUtils.generateTokenFromEmail(user.getEmail());
                    return JwtAuthenticationResponse.builder().token(token).user(user).refreshToken(requestRefreshToken).build();
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }

    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (authentication instanceof AnonymousAuthenticationToken || principal == null) {
            throw new BadRequestException("You are not logged in.");
        }

        User userDetails = (User) principal;
        refreshTokenService.deleteByUserId(userDetails.getUserId());
    }


    public PasswordResetToken forgotPassword(String email) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("This user does not exist");
        }
        String token = UUID.randomUUID().toString();
        return passwordTokenRepository.save(PasswordResetToken.builder().token(token).user(userOptional.get()).expiryDate(LocalDateTime.now().plusMinutes(PasswordResetToken.EXPIRATION)).build());
    }

    public boolean isResetTokenValid(String token) {
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);

        return isTokenFound(passToken) && (!isTokenExpired(passToken));
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        return passToken.getExpiryDate().isBefore(LocalDateTime.now());
    }

    public User resetPassword(ForgotPassword forgotPassword) {

        User user = passwordTokenRepository.findByToken(forgotPassword.getToken()).getUser();

        user.setPassword(passwordEncoder.encode(forgotPassword.getPassword()));

        return userRepository.save(user);

    }

    public User modifyPassword(PasswordReset passwordReset, User user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), passwordReset.getOldPassword()));
        user.setPassword(passwordEncoder.encode(passwordReset.getPassword()));
        User updatedUser = userRepository.save(user);
        this.logout();
        return updatedUser;
    }

}
