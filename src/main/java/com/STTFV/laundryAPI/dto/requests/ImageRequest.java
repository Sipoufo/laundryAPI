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
public class ImageRequest {

    @NotBlank(message = "Image ImageId must not be blank")
    @NotNull(message = "Invalid image imageId: imageId is NULL")


    public String ImageUrl;
}
