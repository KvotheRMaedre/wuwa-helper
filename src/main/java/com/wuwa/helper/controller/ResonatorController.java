package com.wuwa.helper.controller;

import com.wuwa.helper.dto.ResonatorDTO;
import com.wuwa.helper.entity.Resonator;
import com.wuwa.helper.services.ResonatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{resonatorId}")
    public ResponseEntity<Resonator> getResonatorById(@PathVariable("resonatorId") String resonatorId){
        var resonator = resonatorService.getResonatorById(resonatorId);
        return resonator.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping()
    public ResponseEntity<List<Resonator>> getAllResonators(){
        var listResonators = resonatorService.getAllResonators();
        return ResponseEntity.ok(listResonators);
    }

    @DeleteMapping({"/{resonatorId}"})
    public ResponseEntity<Void> deleteResonatorById(@PathVariable("resonatorId") String resonatorId){
        resonatorService.deleteById(resonatorId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping({"/{resonatorId}"})
    public ResponseEntity<Resonator> updateResonatorById(@PathVariable("resonatorId") String resonatorId,
                                                         @RequestBody ResonatorDTO request){
        try {
            if(resonatorService.resonatorExists(resonatorId)){
                var resonator = resonatorService.updateResonatorById(resonatorId, request);
                return ResponseEntity.ok(resonator);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
