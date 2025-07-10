package com.wuwa.helper.services;

import com.wuwa.helper.dto.WeaponTypeDTO;
import com.wuwa.helper.entity.WeaponType;
import com.wuwa.helper.repository.WeaponTypeRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class WeaponTypeService {

    private final WeaponTypeRepository weaponTypeRepository;

    public WeaponTypeService(WeaponTypeRepository weaponTypeRepository) {
        this.weaponTypeRepository = weaponTypeRepository;
    }

    public UUID createWeaponType(WeaponTypeDTO weaponTypeDTO){
        var weaponType = new WeaponType(
                null,
                weaponTypeDTO.name(),
                Instant.now(),
                null
        );
        var savedWeaponType = weaponTypeRepository.save(weaponType);
        return savedWeaponType.getId();
    }
}
