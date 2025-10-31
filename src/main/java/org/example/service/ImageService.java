package org.example.service;

import org.example.model.DTO.ImageDTO;
import org.example.model.entity.Image;
import org.example.repository.ImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ImageDTO convertToImageDTO(Image image) {
        if (image == null) {
            return null;
        }

        ImageDTO dto = new ImageDTO();
        dto.setId(image.getId());
        dto.setPath(image.getPath());

        return dto;
    }
}