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
public class SaleRequest {


    @NotBlank(message = "Sale Num must not be blank")
    @NotNull(message = "Invalid sale num: Num is NULL")

    public String Num ;



}
