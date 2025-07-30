package com.wuwa.helper.services;

import com.wuwa.helper.dto.CalcMaterialsResponseDTO;
import com.wuwa.helper.dto.ResonatorDTO;
import com.wuwa.helper.entity.AscensionMaterials;
import com.wuwa.helper.entity.Resonator;
import com.wuwa.helper.repository.AscensionMaterialsRepository;
import com.wuwa.helper.repository.MaterialRepository;
import com.wuwa.helper.repository.ResonatorRepository;
import com.wuwa.helper.repository.WeaponTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResonatorService {

    private final ResonatorRepository resonatorRepository;
    private final WeaponTypeRepository weaponTypeRepository;
    private final MaterialRepository materialRepository;
    private final AscensionMaterialsRepository ascensionMaterialsRepository;

    public ResonatorService(ResonatorRepository repository, WeaponTypeRepository weaponTypeRepository, MaterialRepository materialRepository, AscensionMaterialsRepository ascensionMaterialsRepository) {
        this.resonatorRepository = repository;
        this.weaponTypeRepository = weaponTypeRepository;
        this.materialRepository = materialRepository;
        this.ascensionMaterialsRepository = ascensionMaterialsRepository;
    }

    public Resonator createResonator(ResonatorDTO resonatorDTO){
        var weaponType = weaponTypeRepository
                .findById(UUID.fromString(resonatorDTO.weaponTypeId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var collectable = materialRepository
                .findById(UUID.fromString(resonatorDTO.collectableId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var bossMaterial = materialRepository
                .findById(UUID.fromString(resonatorDTO.bossMaterialId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var weeklyMaterial = materialRepository
                .findById(UUID.fromString(resonatorDTO.weeklyMaterialId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var resonator = new Resonator(
                null,
                resonatorDTO.name(),
                resonatorDTO.attribute(),
                resonatorDTO.region(),
                resonatorDTO.materialDropGroup(),
                resonatorDTO.materialForgeryGroup(),
                collectable,
                bossMaterial,
                weeklyMaterial,
                weaponType
        );

        return resonatorRepository.save(resonator);
    }

    public Optional<Resonator> getResonatorById(String resonatorId) {
        try{
            return resonatorRepository.findById(UUID.fromString(resonatorId));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public List<Resonator> getAllResonators() {
        return resonatorRepository.findAll();
    }

    public boolean resonatorExists(String resonatorId){
        var resonator = resonatorRepository.findById(UUID.fromString(resonatorId));
        return resonator.isPresent();
    }

    public void deleteById(String resonatorId) {
        if (resonatorExists(resonatorId)){
            resonatorRepository.deleteById(UUID.fromString(resonatorId));
        }
    }

    public Resonator updateResonatorById(String resonatorId, ResonatorDTO resonatorDTO) {
        var resonator = resonatorRepository
                .findById(UUID.fromString(resonatorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var weaponType = weaponTypeRepository
                .findById(UUID.fromString(resonatorDTO.weaponTypeId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        resonator.setName(resonatorDTO.name());
        resonator.setAttribute(resonatorDTO.attribute());
        resonator.setRegion(resonatorDTO.region());
        resonator.setWeaponType(weaponType);

        return resonatorRepository.save(resonator);
    }

    public CalcMaterialsResponseDTO calcTotalMaterialsNeedRankAscension(){
        var listAscensionMaterials = ascensionMaterialsRepository.findAll();
        var listTotalForgeryMaterials = getTotalForgeryMaterials(listAscensionMaterials);
        var totalCollectable = listAscensionMaterials.stream().mapToInt(AscensionMaterials::getQuantityCollectable).sum();
        var totalBossMaterial = listAscensionMaterials.stream().mapToInt(AscensionMaterials::getQuantityBoss).sum();
        var totalShellCredits = listAscensionMaterials.stream().mapToInt(AscensionMaterials::getShellCredits).sum();

        return new CalcMaterialsResponseDTO(listTotalForgeryMaterials, totalCollectable, totalBossMaterial, totalShellCredits);
    }

    public ArrayList<Integer> getTotalForgeryMaterials(List<AscensionMaterials> ascensionMaterialsList){
        var listForgeryMaterials = new ArrayList<Integer>();

        for (var lvl = 1; lvl <= 4; lvl++){
            int finalLvl = lvl;
            var quantityForgeryMaterials = ascensionMaterialsList.stream().filter(ascensionMaterials -> ascensionMaterials.getLevelDrop() == finalLvl).mapToInt(AscensionMaterials::getQuantityDrop).sum();
            listForgeryMaterials.add(quantityForgeryMaterials);
        }

        return listForgeryMaterials;
    }

    public CalcMaterialsResponseDTO calcNeededMaterialsNeedRankAscension(int quantityLevelOneDrop,
                                                       int quantityLevelTwoDrop,
                                                       int quantityLevelThreeDrop,
                                                       int quantityLevelFourDrop,
                                                       int quantityCollectable,
                                                       int quantityBossMaterial,
                                                       int quantityShellCredits) {

        var listTotalMaterials = calcTotalMaterialsNeedRankAscension();

        var neededLevelOneDrop = listTotalMaterials.totalForgeryMaterialsByLevel().get(0);
        var neededLevelTwoDrop = listTotalMaterials.totalForgeryMaterialsByLevel().get(1);
        var neededLevelThreeDrop = listTotalMaterials.totalForgeryMaterialsByLevel().get(2);
        var neededLevelFourDrop = listTotalMaterials.totalForgeryMaterialsByLevel().get(3);

        if (quantityLevelOneDrop >= neededLevelOneDrop){
            quantityLevelOneDrop -= neededLevelOneDrop;
            neededLevelOneDrop = 0;
            quantityLevelTwoDrop += quantityLevelOneDrop / 3;
        } else {
            neededLevelOneDrop -= quantityLevelOneDrop;
        }

        if (quantityLevelTwoDrop >= neededLevelTwoDrop){
            quantityLevelTwoDrop -= neededLevelTwoDrop;
            neededLevelTwoDrop = 0;
            quantityLevelThreeDrop += quantityLevelTwoDrop / 3;
        } else {
            neededLevelTwoDrop -= quantityLevelTwoDrop;
        }

        if (quantityLevelThreeDrop >= neededLevelThreeDrop){
            quantityLevelThreeDrop -= neededLevelThreeDrop;
            neededLevelThreeDrop = 0;
            quantityLevelFourDrop += quantityLevelThreeDrop / 3;
        } else {
            neededLevelThreeDrop -= quantityLevelThreeDrop;
        }

        if (quantityLevelFourDrop >= neededLevelFourDrop){
            quantityLevelFourDrop -= neededLevelFourDrop;
            neededLevelFourDrop = 0;
        } else {
            neededLevelFourDrop -= quantityLevelFourDrop;
        }

        var listForgeryMaterials = new ArrayList<Integer>();
        listForgeryMaterials.add(neededLevelOneDrop);
        listForgeryMaterials.add(neededLevelTwoDrop);
        listForgeryMaterials.add(neededLevelThreeDrop);
        listForgeryMaterials.add(neededLevelFourDrop);

        var neededCollectable =
                (quantityCollectable >= listTotalMaterials.totalCollectable()) ? 0 : listTotalMaterials.totalCollectable() - quantityCollectable;
        var neededBoss =
                (quantityBossMaterial >= listTotalMaterials.totalBossMaterial()) ? 0 : listTotalMaterials.totalBossMaterial() - quantityBossMaterial;
        var neededShellCredit =
                (quantityShellCredits >= listTotalMaterials.totalShellCredits()) ? 0 : listTotalMaterials.totalShellCredits() - quantityShellCredits;
        return new CalcMaterialsResponseDTO(
                listForgeryMaterials,
                neededCollectable,
                neededBoss,
                neededShellCredit
        );
    }
}
