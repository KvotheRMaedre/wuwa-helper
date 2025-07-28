package com.wuwa.helper.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "material")
public class Material {

    @Id
    @Column(name = "material_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "description")
    private String description;

    @Column(name = "level_material")
    private int level;

    @Column(name = "is_craftable")
    private Boolean isCraftable;

    @Column(name = "material_type")
    private String materialType;

    @OneToOne
    @JoinColumn(name = "source_material_id", referencedColumnName = "material_id")
    private Material sourceMaterial;

    public Material() {
    }

    public Material(UUID id, String description, int level, Boolean isCraftable, String materialType, Material sourceMaterial) {
        this.id = id;
        this.description = description;
        this.level = level;
        this.isCraftable = isCraftable;
        this.materialType = materialType;
        this.sourceMaterial = sourceMaterial;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Boolean getCraftable() {
        return isCraftable;
    }

    public void setCraftable(Boolean craftable) {
        isCraftable = craftable;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public Material getSourceMaterial() {
        return sourceMaterial;
    }

    public void setSourceMaterial(Material sourceMaterial) {
        this.sourceMaterial = sourceMaterial;
    }
}
