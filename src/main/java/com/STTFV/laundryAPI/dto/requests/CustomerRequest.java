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
    @NotBlank(message = "First name must not be blank")
    @NotNull(message = "Invalid price value: first name is NULL")
    private String firstname;
    @NotBlank(message = "Last name must not be blank")
    @NotNull(message = "Invalid price value: last name is NULL")
    private String lastname;
    @NotBlank(message = "Phone must not be blank")
    @NotNull(message = "Invalid price value: phone is NULL")
    private String phone;
    @NotBlank(message = "Address must not be blank")
    @NotNull(message = "Invalid price value: address is NULL")
    private String address;
}
