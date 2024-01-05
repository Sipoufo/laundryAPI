package com.STTFV.laundryAPI.controllers;


import com.STTFV.laundryAPI.dto.requests.ExpenseRequest;
import com.STTFV.laundryAPI.dto.requests.ImageRequest;
import com.STTFV.laundryAPI.entities.Expense;
import com.STTFV.laundryAPI.entities.Image;
import com.STTFV.laundryAPI.exceptions.ResourceNotFoundException;
import com.STTFV.laundryAPI.services.ExpenseService;
import com.STTFV.laundryAPI.services.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*" ,maxAge = 3600)
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/image")
public class ImageController {


    @Autowired

    private ImageService imageService;

    @PostMapping("")
    @Operation(summary = "Create image", tags = "Image")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Image Created"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })

    private ResponseEntity<Image> createImage (@RequestBody @Valid ImageRequest imageRequest) {
        return new ResponseEntity<>(imageService.saveImage(imageRequest), HttpStatus.CREATED);
    }


    @GetMapping("")
    @Operation(summary = "Get the list of images", tags = "Image")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Images fetched"),
            @ApiResponse(responseCode = "204", description = "Images are empty"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })

    private ResponseEntity<List<Image>> getImages () {
        List<Image> images = imageService.getAllImage();

        if (images.isEmpty()) {
            return new ResponseEntity<>(images, HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(images, HttpStatus.OK);
        }
    }

    @GetMapping("/{imageId}")
    @Operation(summary = "Get an image", tags = "Image")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Image fetched"),
            @ApiResponse(responseCode = "404", description = "Image not found"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    private ResponseEntity<Image> getImage (@PathVariable Long imageId) {
        Optional<Image> image = imageService.getImage(imageId);

        if (image.isPresent()) {
            return new ResponseEntity<>(image.get(), HttpStatus.OK);
        }
        else {
            throw new ResourceNotFoundException("Image not found");
        }
    }

    @PutMapping("/{imageId}")
    @Operation(summary = "Get an Image", tags = "Image")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Image updated"),
            @ApiResponse(responseCode = "404", description = "Image not found"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    private ResponseEntity<Image> updateImage (@RequestBody @Valid ImageRequest imageRequest, @PathVariable Long imageId) {
        return new ResponseEntity<>(imageService.updateImage(imageRequest, imageId), HttpStatus.OK);
    }

    @DeleteMapping("/{imageId}")
    @Operation(summary = "Delete a image", tags = "Image")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Image deleted"),
            @ApiResponse(responseCode = "404", description = "Image not found"),
            @ApiResponse(responseCode = "500", description = "When server error"),
            @ApiResponse(responseCode = "401", description = "User is not authenticated")
    })
    private ResponseEntity<Void> deleteImage (@PathVariable Long imageId) {
        imageService.deleteImage(imageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
