package com.wuwa.helper.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class UserResonatorId {

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "resonatorId")
    private UUID resonatorId;

    public UserResonatorId() {
    }

    public UserResonatorId(UUID userId, UUID resonatorId) {
        this.userId = userId;
        this.resonatorId = resonatorId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getResonatorId() {
        return resonatorId;
    }

    public void setResonatorId(UUID resonatorId) {
        this.resonatorId = resonatorId;
    }
}
