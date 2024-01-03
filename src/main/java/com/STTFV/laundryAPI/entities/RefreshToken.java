package com.STTFV.laundryAPI.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Entity(name = "refreshtoken")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Setter
@Getter
public class RefreshToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long RefreshTokenId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @Schema(description = "Owner of token")
    private User user;

    @Column(nullable = false, unique = true)
    @Schema(description = "Token value")
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    @Schema(description = "Browser that requested the token")
    private String browser;

    @Schema(description = "Operating System that requested the token")
    private String operatingSystem;

    @Schema(description = "Ip Address that requested the token")
    private String ipAddress;

}
