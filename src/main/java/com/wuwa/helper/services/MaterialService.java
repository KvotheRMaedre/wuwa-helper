package com.wuwa.helper.services;

import com.wuwa.helper.dto.MaterialDTO;
import com.wuwa.helper.dto.MaterialResponseDTO;
import com.wuwa.helper.entity.Material;
import com.wuwa.helper.repository.MaterialRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public Material createMaterial(MaterialDTO materialDTO){
        Material sourceMaterial = null;
        var isCraftable = false;
        if(materialDTO.sourceMaterialId() != null){
            isCraftable = true;
            sourceMaterial = materialRepository.findById(UUID.fromString(materialDTO.sourceMaterialId()))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        }

        var material = new Material(
                null,
                materialDTO.description(),
                materialDTO.level(),
                isCraftable,
                materialDTO.materialType(),
                sourceMaterial
        );

        return materialRepository.save(material);
    }

    public MaterialResponseDTO getMaterialById(String materialId){
        try {
            MaterialResponseDTO materialResponseDTO;
            var material = materialRepository.findById(UUID.fromString(materialId))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            if(material.getCraftable()){
                materialResponseDTO = new MaterialResponseDTO(
                        material.getId(),
                        material.getDescription(),
                        material.getLevel(),
                        material.getMaterialType(),
                        material.getCraftable(),
                        material.getSourceMaterial().getId(),
                        material.getSourceMaterial().getDescription()
                );
            } else {
                materialResponseDTO = new MaterialResponseDTO(
                        material.getId(),
                        material.getDescription(),
                        material.getLevel(),
                        material.getMaterialType(),
                        material.getCraftable(),
                        null,
                        null
                );
            }

            return materialResponseDTO;

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public MaterialResponseDTO getMaterialByDescription(String description){
        try {
            MaterialResponseDTO materialResponseDTO;
            var material = materialRepository.findByDescription(description)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            if(material.getCraftable()){
                materialResponseDTO = new MaterialResponseDTO(
                        material.getId(),
                        material.getDescription(),
                        material.getLevel(),
                        material.getMaterialType(),
                        material.getCraftable(),
                        material.getSourceMaterial().getId(),
                        material.getSourceMaterial().getDescription()
                );
            } else {
                materialResponseDTO = new MaterialResponseDTO(
                        material.getId(),
                        material.getDescription(),
                        material.getLevel(),
                        material.getMaterialType(),
                        material.getCraftable(),
                        null,
                        null
                );
            }

            return materialResponseDTO;
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public List<Material> getAllMaterials(){
        return materialRepository.findAll();
    }
}
