package org.example.service;

import org.example.model.DTO.OptionDTO;
import org.example.model.DTO.OptionRequestDTO; // ✅ Import DTO Input
import org.example.model.DTO.SupplyDTO;
import org.example.model.DTO.SupplyRequestDTO; // ✅ Import DTO Input
import org.example.model.entity.Level;
import org.example.model.entity.Option;
import org.example.model.entity.School;
import org.example.model.entity.Supply;
import org.example.repository.LevelRepository;
import org.example.repository.OptionRepository; // ✅ Pour enregistrer les options
import org.example.repository.SchoolRepository; // ✅ Pour trouver l'école
import org.example.repository.SupplyRepository;
import org.example.util.FileUploadUtil;         // ✅ Pour l'upload image
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplyService {

    private final SupplyRepository supplyRepository;
    private final OptionService optionService;

    // ✅ Dependencies Jdad (Pour le POST)
    private final SchoolRepository schoolRepository;
    private final LevelRepository levelRepository;
    private final OptionRepository optionRepository;

    // Injection via Constructeur (Mise à jour avec les nouveaux Repos)
    public SupplyService(SupplyRepository supplyRepository,
                         OptionService optionService,
                         SchoolRepository schoolRepository,
                         LevelRepository levelRepository,
                         OptionRepository optionRepository) {
        this.supplyRepository = supplyRepository;
        this.optionService = optionService;
        this.schoolRepository = schoolRepository;
        this.levelRepository = levelRepository;
        this.optionRepository = optionRepository;
    }

    // =================================================================
    // 🆕 PARTIE ÉCRITURE : AJOUTER UNE SL3A (POST)
    // =================================================================

    @Transactional
    public Supply createSupplyWithImage(SupplyRequestDTO request, MultipartFile imageFile) throws IOException {

        // 1. Vérification des Relations (Level & School)
        Level level = levelRepository.findById(request.getLevelId())
                .orElseThrow(() -> new RuntimeException("Level not found ID: " + request.getLevelId()));

        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new RuntimeException("School not found ID: " + request.getSchoolId()));

        // 2. Gestion de l'Image (Upload)
        String imageFileName = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imageFileName = FileUploadUtil.saveFile(imageFile);
        }

        // 3. Mapping (DTO -> Entity)
        Supply supply = new Supply();
        supply.setName(request.getName());
        supply.setDescription(request.getDescription());
        supply.setPrice(request.getPrice());
        supply.setImage(imageFileName); // ✅ On stocke le nom du fichier

        // Champs supplémentaires
        supply.setIsbn(request.getIsbn());
        supply.setMarque(request.getMarque());
        supply.setPosition(request.getPosition());
        supply.setCurrency(request.getCurrency() != null ? request.getCurrency() : "MAD");
        supply.setIsBook(request.getIsBook() != null ? request.getIsBook() : false);
        supply.setStockQuantity(request.getStockQuantity() != null ? request.getStockQuantity() : 0);

        // ✅ Linking Relations
        supply.setLevel(level);
        supply.setSchool(school);

        // Calcul initial du stock (si pas d'options)
        supply.setInStock(supply.getStockQuantity() > 0);

        // 4. Sauvegarde Initiale (Pour générer l'ID)
        supply = supplyRepository.save(supply);

        // 5. Gestion des Options (S'il y en a)
        if (request.getOptions() != null && !request.getOptions().isEmpty()) {
            for (OptionRequestDTO optDto : request.getOptions()) {
                Option option = new Option();
                option.setName(optDto.getOptionName());
                option.setStockQuantity(optDto.getStockQuantity());
                option.setSupply(supply); // ✅ Liaison avec le parent

                optionRepository.save(option);
            }
            // Mise à jour du statut inStock car on a ajouté des options
            supply.setInStock(true);
            supplyRepository.save(supply);
        }

        return supply;
    }

    // =================================================================
    // 📖 PARTIE LECTURE (GET) - Logic existant
    // =================================================================

    // --- A. Méthode de Conversion (Entity -> DTO) ---
    private SupplyDTO convertToSupplyDTO(Supply supply) {
        SupplyDTO dto = new SupplyDTO();

        // 1. Champs de base
        dto.setId(supply.getId());
        dto.setName(supply.getName());
        dto.setPrice(supply.getPrice());
        dto.setCurrency(supply.getCurrency());
        dto.setInStock(supply.getInStock());

        // Champs supplémentaires
        dto.setMarque(supply.getMarque());
        dto.setPosition(supply.getPosition());
        dto.setIsBook(supply.getIsBook());

        // 2. Traduction des Slugs
        // ✅ Mise à jour : On récupère le slug de l'école directement depuis la relation Supply -> School
        if (supply.getSchool() != null) {
            dto.setSchool(supply.getSchool().getSlug());
        } else {
            // Fallback (Au cas où)
            dto.setSchool(supply.getLevel().getSchool().getSlug());
        }

        if (supply.getLevel() != null) {
            dto.setLevel(supply.getLevel().getSlug());
        }

        // 3. Assemblage des Options
        List<OptionDTO> optionDTOs = supply.getOptions().stream()
                .map(optionService::convertToOptionDTO)
                .collect(Collectors.toList());

        dto.setOptions(optionDTOs);

        return dto;
    }

    // --- B. Récupération par Liste (School & Level) ---
    public List<SupplyDTO> getSuppliesBySchoolAndLevel(String schoolSlug, String levelSlug) {
        List<Supply> supplies = supplyRepository.findByLevelSchoolSlugAndLevelSlug(schoolSlug, levelSlug);
        return supplies.stream()
                .map(this::convertToSupplyDTO)
                .collect(Collectors.toList());
    }

    // --- C. Récupération par ID Unique ---
    public SupplyDTO getSupplyById(Long id) {
        Supply supply = supplyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supply not found with id: " + id));
        return convertToSupplyDTO(supply);
    }
}