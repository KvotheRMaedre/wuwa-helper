package com.wuwa.helper.repository;

import com.wuwa.helper.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MaterialRepository extends JpaRepository<Material, UUID> {
    Optional<Material> findByDescription(String description);
}
