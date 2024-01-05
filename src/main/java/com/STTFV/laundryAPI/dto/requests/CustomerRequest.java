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

    @NotBlank(message = "Customer phone must not be blank")
    @NotNull(message = "Invalid customer phone: Name is NULL")

    private String phone;
}
