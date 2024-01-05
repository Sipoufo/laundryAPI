package com.STTFV.laundryAPI.services;


import com.STTFV.laundryAPI.dto.requests.ExpenseRequest;
import com.STTFV.laundryAPI.dto.requests.ImageRequest;
import com.STTFV.laundryAPI.entities.Expense;
import com.STTFV.laundryAPI.entities.Image;
import com.STTFV.laundryAPI.exceptions.ResourceNotFoundException;
import com.STTFV.laundryAPI.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Autowired

    private ImageRepository  imageRepository;


    public Image saveImage(ImageRequest imageRequest){
        Image image = Image.builder().imageUrl(imageRequest.getImageUrl()).build();
        return imageRepository.save(image);
    }

    public Optional<Image> getImage(Long imageId) {
        return imageRepository.findById(imageId);
    }

    public List<Image> getAllImage() {
        return imageRepository.findAll();
    }

    public  Image updateImage(ImageRequest imageRequest, Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);

        if (image.isEmpty()) {
            throw new ResourceNotFoundException("Image not found.");
        }

        image.get().setImageUrl(imageRequest.getImageUrl());

        return imageRepository.save(image.get());
    }

    public void deleteImage(Long imageId) {
        Optional<Image> image = imageRepository.findById(imageId);

        if (image.isEmpty()) {
            throw new ResourceNotFoundException("Image not found.");
        }

        imageRepository.deleteById(imageId);
    }

}
