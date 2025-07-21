package com.wuwa.helper.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "weapon_type")
public class WeaponType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;

    @CreationTimestamp
    private Instant creationTimestamp;

    @UpdateTimestamp
    private Instant updatedTimestamp;

    @OneToOne(mappedBy = "weaponType", cascade = CascadeType.ALL)
    private Resonator resonator;

    public WeaponType() {
    }

    public WeaponType(UUID id, String name, Instant creationTimestamp, Instant updateTimestamp) {
        this.id = id;
        this.name = name;
        this.creationTimestamp = creationTimestamp;
        this.updatedTimestamp = updateTimestamp;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Instant creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public Instant getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    public void setUpdatedTimestamp(Instant updateTimestamp) {
        this.updatedTimestamp = updateTimestamp;
    }
}
