package com.STTFV.laundryAPI.dto.responses;

import com.STTFV.laundryAPI.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    @Schema(description = "Current user")
    private User user;
    @Schema(description = "New token generated")
    private String token;
    @Schema(description = "Token to generate others access tokens")
    private String refreshToken;
    @Schema(description = "Type of token")
    private String tokenType = "Bearer";
}