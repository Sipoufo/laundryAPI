package com.STTFV.laundryAPI.dto.requests;

import com.STTFV.laundryAPI.annotations.UniqueEmail;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignUpRequest {

    @UniqueEmail
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Name must not be blank")
    @NotNull(message = "Invalid Name: Name is NULL")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String firstName;

    @NotBlank(message = "Name must not be blank")
    @NotNull(message = "Invalid Name: Name is NULL")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String lastName;

    @NotBlank(message = "Password must not be blank")
    @NotNull(message = "Password must not be null")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",message = "Le mot de passe ne correspond pas à aux exigeances")
    private String password;
}