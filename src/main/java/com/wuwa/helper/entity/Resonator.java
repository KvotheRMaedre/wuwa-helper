package com.wuwa.helper.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "resonator")
public class Resonator {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "resonator_id")
    private UUID resonatorId;

    @Column(name = "name")
    private String name;

    @Column(name = "attribute")
    private String attribute;

    @Column(name = "region")
    private String region;

    @ManyToOne
    @JoinColumn(name = "weapon_type_id")
    private WeaponType weaponType;

    public Resonator() {
    }

    public Resonator(UUID resonatorId, String name, String attribute, String region, WeaponType weaponType) {
        this.resonatorId = resonatorId;
        this.name = name;
        this.attribute = attribute;
        this.region = region;
        this.weaponType = weaponType;
    }

    public UUID getResonatorId() {
        return resonatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }
}
