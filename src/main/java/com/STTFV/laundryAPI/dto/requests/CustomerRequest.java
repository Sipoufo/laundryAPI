package com.STTFV.laundryAPI.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CustomerRequest {
    private String num;
    private String firstname;
    private String lastname;
    private String phone;
    private String address;
    private int transaction;
    private int paid;
    private int remains;
}
