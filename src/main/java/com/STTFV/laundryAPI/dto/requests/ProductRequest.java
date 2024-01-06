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


    @NotBlank(message = "Product ProductId must not be blank")
    @NotNull(message = "Invalid product productId: Name is NULL")
    public String Name;
    public Image Image;

    public int normalIroning;

    public int fastIroning;

    public int getnormalIroning;
    public int normalGloriousPressing;

    public int fastDetergent;
}
