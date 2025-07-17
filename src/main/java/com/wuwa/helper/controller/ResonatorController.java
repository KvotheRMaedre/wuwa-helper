package com.wuwa.helper.controller;

import com.wuwa.helper.dto.ResonatorDTO;
import com.wuwa.helper.entity.Resonator;
import com.wuwa.helper.services.ResonatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/resonator")
public class ResonatorController {

    private final ResonatorService resonatorService;

    public ResonatorController(ResonatorService resonatorService) {
        this.resonatorService = resonatorService;
    }

    @PostMapping
    public ResponseEntity<Resonator> createResonator(@RequestBody ResonatorDTO request){
        System.out.println(request);
        var resonator = resonatorService.createResonator(request);
        return ResponseEntity.ok(resonator);
    }
}
