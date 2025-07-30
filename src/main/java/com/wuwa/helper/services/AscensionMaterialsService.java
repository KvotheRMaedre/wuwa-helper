package com.wuwa.helper.services;

import com.wuwa.helper.dto.AscensionMaterialsDTO;
import com.wuwa.helper.entity.AscensionMaterials;
import com.wuwa.helper.repository.AscensionMaterialsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AscensionMaterialsService {

    private final AscensionMaterialsRepository ascensionMaterialsRepository;

    public AscensionMaterialsService(AscensionMaterialsRepository ascensionMaterialsRepository) {
        this.ascensionMaterialsRepository = ascensionMaterialsRepository;
    }

    public AscensionMaterials createAscensionMaterials(AscensionMaterialsDTO ascensionMaterialsDTO){
        var ascensionMaterial = new AscensionMaterials(
                null,
                ascensionMaterialsDTO.rankAscensionLevel(),
                ascensionMaterialsDTO.quantityCollectable(),
                ascensionMaterialsDTO.levelDrop(),
                ascensionMaterialsDTO.quantityDrop(),
                ascensionMaterialsDTO.quantityBoss(),
                ascensionMaterialsDTO.shellCredits()
        );

        return ascensionMaterialsRepository.save(ascensionMaterial);
    }

    public List<AscensionMaterials> getAllAscensionMaterials(){
        return ascensionMaterialsRepository.findAll();
    }
}
