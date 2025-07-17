package com.wuwa.helper.services;

import com.wuwa.helper.dto.ResonatorDTO;
import com.wuwa.helper.entity.Resonator;
import com.wuwa.helper.repository.ResonatorRepository;
import com.wuwa.helper.repository.WeaponTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class ResonatorService {

    private final ResonatorRepository resonatorRepository;
    private final WeaponTypeRepository weaponTypeRepository;

    public ResonatorService(ResonatorRepository repository, WeaponTypeRepository weaponTypeRepository) {
        this.resonatorRepository = repository;
        this.weaponTypeRepository = weaponTypeRepository;
    }

    public Resonator createResonator(ResonatorDTO resonatorDTO){
        var weaponType = weaponTypeRepository.findById(UUID.fromString(resonatorDTO.weaponTypeId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var resonator = new Resonator(
                null,
                resonatorDTO.name(),
                resonatorDTO.attribute(),
                resonatorDTO.region(),
                weaponType
        );

        return resonatorRepository.save(resonator);
    }
}
