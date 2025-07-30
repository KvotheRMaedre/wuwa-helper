package com.wuwa.helper.repository;

import com.wuwa.helper.entity.AscensionMaterials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AscensionMaterialsRepository extends JpaRepository<AscensionMaterials, UUID> {
}
