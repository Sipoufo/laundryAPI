package com.STTFV.laundryAPI.dto.requests;


import com.STTFV.laundryAPI.entities.Image;
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
public class ProductRequest {
    @NotBlank(message = "Name must not be blank")
    @NotNull(message = "Invalid price value: Name is NULL")
    private String Name;
    @NotNull(message = "Invalid price value: normalIroning is NULL")
    private int normalIroning;
    @NotNull(message = "Invalid price value: fastIroning is NULL")
    private int fastIroning;
    @NotNull(message = "Invalid price value: normalDetergent is NULL")
    private int normalDetergent;
    @NotNull(message = "Invalid price value: fastDetergent is NULL")
    private int fastDetergent;
    @NotNull(message = "Invalid price value: normalGloriousPressing is NULL")
    private int normalGloriousPressing;
    @NotNull(message = "Invalid price value: fastGloriousPressing is NULL")
    private int fastGloriousPressing;
}
