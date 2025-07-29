package com.wuwa.helper.services;

import com.wuwa.helper.dto.ResonatorDTO;
import com.wuwa.helper.entity.Resonator;
import com.wuwa.helper.repository.MaterialRepository;
import com.wuwa.helper.repository.ResonatorRepository;
import com.wuwa.helper.repository.WeaponTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResonatorService {

    private final ResonatorRepository resonatorRepository;
    private final WeaponTypeRepository weaponTypeRepository;
    private final MaterialRepository materialRepository;

    public ResonatorService(ResonatorRepository repository, WeaponTypeRepository weaponTypeRepository, MaterialRepository materialRepository) {
        this.resonatorRepository = repository;
        this.weaponTypeRepository = weaponTypeRepository;
        this.materialRepository = materialRepository;
    }

    public Resonator createResonator(ResonatorDTO resonatorDTO){
        var weaponType = weaponTypeRepository
                .findById(UUID.fromString(resonatorDTO.weaponTypeId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var collectable = materialRepository
                .findById(UUID.fromString(resonatorDTO.collectableId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var bossMaterial = materialRepository
                .findById(UUID.fromString(resonatorDTO.bossMaterialId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var weeklyMaterial = materialRepository
                .findById(UUID.fromString(resonatorDTO.weeklyMaterialId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var resonator = new Resonator(
                null,
                resonatorDTO.name(),
                resonatorDTO.attribute(),
                resonatorDTO.region(),
                resonatorDTO.materialDropGroup(),
                resonatorDTO.materialForgeryGroup(),
                collectable,
                bossMaterial,
                weeklyMaterial,
                weaponType
        );

        return resonatorRepository.save(resonator);
    }

    public Optional<Resonator> getResonatorById(String resonatorId) {
        try{
            return resonatorRepository.findById(UUID.fromString(resonatorId));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public List<Resonator> getAllResonators() {
        return resonatorRepository.findAll();
    }

    public boolean resonatorExists(String resonatorId){
        var resonator = resonatorRepository.findById(UUID.fromString(resonatorId));
        return resonator.isPresent();
    }

    public void deleteById(String resonatorId) {
        if (resonatorExists(resonatorId)){
            resonatorRepository.deleteById(UUID.fromString(resonatorId));
        }
    }

    public Resonator updateResonatorById(String resonatorId, ResonatorDTO resonatorDTO) {
        var resonator = resonatorRepository
                .findById(UUID.fromString(resonatorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var weaponType = weaponTypeRepository
                .findById(UUID.fromString(resonatorDTO.weaponTypeId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        resonator.setName(resonatorDTO.name());
        resonator.setAttribute(resonatorDTO.attribute());
        resonator.setRegion(resonatorDTO.region());
        resonator.setWeaponType(weaponType);

        return resonatorRepository.save(resonator);
    }
}
