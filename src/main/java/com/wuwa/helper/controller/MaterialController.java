package com.wuwa.helper.controller;

import com.wuwa.helper.dto.MaterialDTO;
import com.wuwa.helper.dto.MaterialResponseDTO;
import com.wuwa.helper.entity.Material;
import com.wuwa.helper.services.MaterialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/material")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping
    public ResponseEntity<Material> createMaterial(@RequestBody MaterialDTO request){
        var material = materialService.createMaterial(request);

        return ResponseEntity.created(URI.create("/v1/material/" + material.getId())).build();
    }

    @GetMapping("/{materialId}")
    public ResponseEntity<MaterialResponseDTO> getMaterialById(@PathVariable("materialId") String materialId){
        var material = materialService.getMaterialById(materialId);

        return ResponseEntity.ok(material);
    }

    @GetMapping("/description")
    public ResponseEntity<MaterialResponseDTO> getMaterialByDescription(@RequestParam("description") String materialDescription){
        var material = materialService.getMaterialByDescription(materialDescription);

        return ResponseEntity.ok(material);
    }

    @GetMapping
    public ResponseEntity<List<Material>> getAllMaterials(){
        var listMaterial = materialService.getAllMaterials();

        return ResponseEntity.ok(listMaterial);
    }
}
