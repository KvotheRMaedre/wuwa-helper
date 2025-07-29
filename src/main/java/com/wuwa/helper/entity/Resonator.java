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

    @Column(name = "material_drop_group")
    private String materialDropGroup;

    @Column(name = "material_forgery_group")
    private String materialForgeryGroup;

    @ManyToOne
    @JoinColumn(name = "collectable_id")
    private Material collectable;

    @ManyToOne
    @JoinColumn(name = "boss_material_id")
    private Material bossMaterial;

    @ManyToOne
    @JoinColumn(name = "weekly_material_id")
    private Material weeklyMaterial;

    @ManyToOne
    @JoinColumn(name = "weapon_type_id")
    private WeaponType weaponType;

    public Resonator() {
    }

    public Resonator(UUID resonatorId, String name, String attribute, String region, String materialDropGroup, String materialForgeryGroup, Material collectable, Material bossMaterial, Material weeklyMaterial, WeaponType weaponType) {
        this.resonatorId = resonatorId;
        this.name = name;
        this.attribute = attribute;
        this.region = region;
        this.materialDropGroup = materialDropGroup;
        this.materialForgeryGroup = materialForgeryGroup;
        this.collectable = collectable;
        this.bossMaterial = bossMaterial;
        this.weeklyMaterial = weeklyMaterial;
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

    public String getMaterialDropGroup() {
        return materialDropGroup;
    }

    public void setMaterialDropGroup(String materialDropGroup) {
        this.materialDropGroup = materialDropGroup;
    }

    public String getMaterialForgeryGroup() {
        return materialForgeryGroup;
    }

    public void setMaterialForgeryGroup(String materialForgeryGroup) {
        this.materialForgeryGroup = materialForgeryGroup;
    }

    public Material getCollectable() {
        return collectable;
    }

    public void setCollectable(Material collectable) {
        this.collectable = collectable;
    }

    public Material getBossMaterial() {
        return bossMaterial;
    }

    public void setBossMaterial(Material bossMaterial) {
        this.bossMaterial = bossMaterial;
    }

    public Material getWeeklyMaterial() {
        return weeklyMaterial;
    }

    public void setWeeklyMaterial(Material weeklyMaterial) {
        this.weeklyMaterial = weeklyMaterial;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }
}
