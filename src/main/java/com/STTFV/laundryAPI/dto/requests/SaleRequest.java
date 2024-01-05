package com.STTFV.laundryAPI.dto.requests;

import com.STTFV.laundryAPI.entities.Customer;
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
    private String num;  // Utilisation du nom de variable en minuscules

    private Customer customer;
    private String status;
    private int tax;
    private int discount;
    private String near;
    private int numberService;
    private boolean delivered;  // Utilisation d'un nom de variable plus explicite

    // Ajout d'un getter pour le champ "delivered"
    public boolean getDelivered() {
        return delivered;
    }
}
