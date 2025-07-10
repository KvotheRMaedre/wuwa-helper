package com.wuwa.helper.controller;

import com.wuwa.helper.dto.UserDTO;
import com.wuwa.helper.dto.WeaponTypeDTO;
import com.wuwa.helper.entity.User;
import com.wuwa.helper.entity.WeaponType;
import com.wuwa.helper.services.WeaponTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
}
