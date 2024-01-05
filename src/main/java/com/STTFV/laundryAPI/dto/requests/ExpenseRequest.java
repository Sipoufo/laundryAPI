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
public class ExpenseRequest {


    @NotBlank(message = "Expense num must not be blank")
    @NotNull(message = "Invalid expense num: Num is NULL")

    private String num;
}
