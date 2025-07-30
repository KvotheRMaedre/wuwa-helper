package com.wuwa.helper.controller;

import com.wuwa.helper.dto.AscensionMaterialsDTO;
import com.wuwa.helper.entity.AscensionMaterials;
import com.wuwa.helper.services.AscensionMaterialsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/ascension-materials")
public class AscensionMaterialsController {

    private final AscensionMaterialsService ascensionMaterialsService;

    public AscensionMaterialsController(AscensionMaterialsService ascensionMaterialsService) {
        this.ascensionMaterialsService = ascensionMaterialsService;
    }

    @PostMapping
    public ResponseEntity<AscensionMaterials> createAscensionMaterial(@RequestBody AscensionMaterialsDTO request){
        var ascensionMaterials = ascensionMaterialsService.createAscensionMaterials(request);

        return ResponseEntity.created(URI.create("/v1/ascension-materials/" + ascensionMaterials.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<AscensionMaterials>> getAllAscensionMaterials(){
        var listAscensionMaterials = ascensionMaterialsService.getAllAscensionMaterials();
        return ResponseEntity.ok(listAscensionMaterials);
    }
}
