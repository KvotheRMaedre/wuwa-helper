package com.wuwa.helper.repository;

import com.wuwa.helper.entity.WeaponType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WeaponTypeRepository extends JpaRepository<WeaponType, UUID> {
}
