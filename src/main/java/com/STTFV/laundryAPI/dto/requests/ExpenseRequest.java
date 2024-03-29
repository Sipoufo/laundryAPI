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
    @NotNull(message = "Invalid price value: Price is NULL")
    private int price;

    @NotNull(message = "Invalid deliver value: Price is NULL")
    private boolean isDelivered;

    @NotNull(message = "Invalid category value: Price is NULL")
    private Long categoryId;

    @NotBlank(message = "Title must not be blank")
    @NotNull(message = "Invalid title value: Price is NULL")
    public String title;
}
