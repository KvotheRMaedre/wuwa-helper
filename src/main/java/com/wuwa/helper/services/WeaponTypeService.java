package com.wuwa.helper.services;

import com.wuwa.helper.dto.WeaponTypeDTO;
import com.wuwa.helper.entity.WeaponType;
import com.wuwa.helper.repository.WeaponTypeRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
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

    public Optional<WeaponType> getWeaponTypeById(String weaponTypeId){
        return weaponTypeRepository.findById(UUID.fromString(weaponTypeId));
    }

    public List<WeaponType> getAllWeaponType(){
        return weaponTypeRepository.findAll();
    }

    public boolean weaponTypeExists(String weaponTypeId){
        return weaponTypeRepository.existsById(UUID.fromString(weaponTypeId));
    }

    public void deleteWeaponTypeById(String weaponTypeId){
        weaponTypeRepository.deleteById(UUID.fromString(weaponTypeId));
    }

    public WeaponType updateWeaponType(String weaponTypeId, WeaponTypeDTO weaponTypeDTO){
        var weaponType = weaponTypeRepository.findById(UUID.fromString(weaponTypeId));
        if(weaponType.isPresent()){
            var weaponTypeToBeUpdated = weaponType.get();
            weaponTypeToBeUpdated.setName(weaponTypeDTO.name());
            return weaponTypeRepository.save(weaponTypeToBeUpdated);
        }
        return null;
    }
}
