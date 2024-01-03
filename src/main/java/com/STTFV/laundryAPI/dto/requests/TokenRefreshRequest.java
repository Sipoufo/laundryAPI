package com.STTFV.laundryAPI.dto.requests;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TokenRefreshRequest {
    @NotBlank(message = "refreshToken must not be blank")
    private String refreshToken;

}
