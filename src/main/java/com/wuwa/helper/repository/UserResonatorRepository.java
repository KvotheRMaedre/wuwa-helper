package com.wuwa.helper.repository;

import com.wuwa.helper.entity.UserResonator;
import com.wuwa.helper.entity.UserResonatorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserResonatorRepository extends JpaRepository<UserResonator, UserResonatorId> {
}
