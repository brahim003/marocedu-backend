package org.example.service;

import org.example.model.entity.Supply;
import org.example.model.DTO.SupplyDTO;
import org.example.model.DTO.OptionDTO;
import org.example.repository.SupplyRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplyService {

    private final SupplyRepository supplyRepository;
    private final OptionService optionService;

    // Injection via Constructeur
    public SupplyService(SupplyRepository supplyRepository, OptionService optionService) {
        this.supplyRepository = supplyRepository;
        this.optionService = optionService;
    }


    // --- A. Méthode de Conversion Kbira (Supply -> SupplyDTO) ---

    private SupplyDTO convertToSupplyDTO(Supply supply) {
        SupplyDTO dto = new SupplyDTO();

        // 1. Champs de base
        dto.setId(supply.getId());
        dto.setName(supply.getName());
        dto.setPrice(supply.getPrice());
        dto.setCurrency(supply.getCurrency());
        dto.setInStock(supply.getInStock());

        // Ziyadat les champs jdad (Supply)
        dto.setMarque(supply.getMarque());
        dto.setPosition(supply.getPosition());

        // 2. Traduction dial les Slugs
        dto.setLevel(supply.getLevel().getSlug());
        dto.setSchool(supply.getLevel().getSchool().getSlug());

        // 3. Assemblage dial Options (incluant les images)
        List<OptionDTO> optionDTOs = supply.getOptions().stream()
                .map(optionService::convertToOptionDTO) // Utilisation de OptionService (qui utilise ImageService)
                .collect(Collectors.toList());

        dto.setOptions(optionDTOs);

        return dto;
    }

    // --- B. Méthode 1: Récupération de la Liste (Déjà existante) ---

    public List<SupplyDTO> getSuppliesBySchoolAndLevel(String schoolSlug, String levelSlug) {

        List<Supply> supplies = supplyRepository.findByLevelSchoolSlugAndLevelSlug(schoolSlug, levelSlug);

        return supplies.stream()
                .map(this::convertToSupplyDTO)
                .collect(Collectors.toList());
    }

    // --- C. NOUVELLE MÉTHODE 2: Récupération d'un Produit Unique (Pour la Page de Détail) ---

    /**
     * Règle: Trouve un produit par son ID et le convertit en DTO complet.
     */
    public SupplyDTO getSupplyById(Long id) {
        // 1. Appel au Repository (recherche par ID)
        Supply supply = supplyRepository.findById(id)
                // Si l'ID n'existe pas, on lève une exception
                .orElseThrow(() -> new RuntimeException("Supply not found with id: " + id));

        // 2. Conversion de l'Entité unique en DTO
        // Cette étape ramène toutes les Options et Images liées.
        return convertToSupplyDTO(supply);
    }
}