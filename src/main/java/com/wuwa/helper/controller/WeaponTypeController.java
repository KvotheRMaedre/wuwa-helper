package com.wuwa.helper.controller;

import com.wuwa.helper.dto.WeaponTypeDTO;
import com.wuwa.helper.entity.WeaponType;
import com.wuwa.helper.services.WeaponTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/weaponType")
public class WeaponTypeController {

    private final WeaponTypeService weaponTypeService;

    public WeaponTypeController(WeaponTypeService weaponTypeService) {
        this.weaponTypeService = weaponTypeService;
    }

    @PostMapping
    public ResponseEntity<WeaponType> createWeaponType(@RequestBody WeaponTypeDTO request){
        var weaponTypeId = weaponTypeService.createWeaponType(request);
        return ResponseEntity.created(URI.create("/v1/weaponType/" + weaponTypeId.toString())).build();
    }

    @GetMapping({"/{weaponTypeId}"})
    public ResponseEntity<WeaponType> getWeaponTypeById(@PathVariable String weaponTypeId){
        try{
            var weaponType = weaponTypeService.getWeaponTypeById(weaponTypeId);
            return weaponType.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException exception){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<WeaponType>> getAllWeaponType(){
        var weaponTypeList = weaponTypeService.getAllWeaponType();
        return ResponseEntity.ok(weaponTypeList);
    }

    @DeleteMapping("{weaponTypeId}")
    public ResponseEntity<Void> deleteWeaponTypeById(@PathVariable String weaponTypeId){
        if(weaponTypeService.weaponTypeExists(weaponTypeId)){
            weaponTypeService.deleteWeaponTypeById(weaponTypeId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping({"/{weaponTypeId}"})
    public ResponseEntity<WeaponType> updateWeaponTypeById(@PathVariable String weaponTypeId, @RequestBody WeaponTypeDTO request){
        try{
            if (weaponTypeService.weaponTypeExists(weaponTypeId)) {
                var weaponType = weaponTypeService.updateWeaponType(weaponTypeId,request);
                return ResponseEntity.ok(weaponType);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException exception){
            return ResponseEntity.notFound().build();
        }
    }
}
