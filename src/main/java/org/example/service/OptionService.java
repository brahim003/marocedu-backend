package org.example.service;

import org.example.model.entity.Option;
import org.example.model.DTO.OptionDTO;
import org.example.model.DTO.ImageDTO;
import org.example.repository.OptionRepository;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class OptionService {

    private final OptionRepository optionRepository;
    private final ImageService imageService; //  Instance injectée

    // Injection via Constructeur
    public OptionService(OptionRepository optionRepository, ImageService imageService) {
        this.optionRepository = optionRepository;
        this.imageService = imageService;
    }

    // ✅ LA MÉTHODE DE CONVERSION (Traduction)
    public OptionDTO convertToOptionDTO(Option option) {
        if (option == null) {
            return null;
        }

        OptionDTO dto = new OptionDTO();

        // Champs de base
        dto.setId(option.getId());
        dto.setImage(option.getImage());
        dto.setName(option.getName());
        dto.setPrice(option.getPrice());
        dto.setCurrency(option.getCurrency());

        // Champs supplémentaires
        dto.setMarque(option.getMarque());
        dto.setPosition(option.getPosition());


        List<ImageDTO> imageDTOs = option.getImages().stream()
                .map(imageService::convertToImageDTO)
                .collect(Collectors.toList());

        dto.setImages(imageDTOs); // On attache la liste d'ImageDTOs

        return dto;
    }

    // ... (Les autres méthodes restent inchangées) ...
}