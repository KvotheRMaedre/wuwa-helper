package com.wuwa.helper.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "ascension_material")
public class AscensionMaterials {

    @Id
    @Column(name = "material_rankup_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "rankAscensionLevel")
    private int rankAscensionLevel;

    @Column(name = "quantity_collectable")
    private int quantityCollectable;

    @Column(name = "level_drop")
    private int levelDrop;

    @Column(name = "quantity_drop")
    private int quantityDrop;

    @Column(name = "quantity_boss")
    private int quantityBoss;

    @Column(name = "shell_credits")
    private int shellCredits;

    public AscensionMaterials() {
    }

    public AscensionMaterials(UUID id, int rankAscensionLevel, int quantityCollectable, int levelDrop, int quantityDrop, int quantityBoss, int shellCredits) {
        this.id = id;
        this.rankAscensionLevel = rankAscensionLevel;
        this.quantityCollectable = quantityCollectable;
        this.levelDrop = levelDrop;
        this.quantityDrop = quantityDrop;
        this.quantityBoss = quantityBoss;
        this.shellCredits = shellCredits;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getRankAscensionLevel() {
        return rankAscensionLevel;
    }

    public void setRankAscensionLevel(int rankAscensionLevel) {
        this.rankAscensionLevel = rankAscensionLevel;
    }

    public int getQuantityCollectable() {
        return quantityCollectable;
    }

    public void setQuantityCollectable(int quantityCollectable) {
        this.quantityCollectable = quantityCollectable;
    }

    public int getLevelDrop() {
        return levelDrop;
    }

    public void setLevelDrop(int levelDrop) {
        this.levelDrop = levelDrop;
    }

    public int getQuantityDrop() {
        return quantityDrop;
    }

    public void setQuantityDrop(int quantityDrop) {
        this.quantityDrop = quantityDrop;
    }

    public int getQuantityBoss() {
        return quantityBoss;
    }

    public void setQuantityBoss(int quantityBoss) {
        this.quantityBoss = quantityBoss;
    }

    public int getShellCredits() {
        return shellCredits;
    }

    public void setShellCredits(int shellCredits) {
        this.shellCredits = shellCredits;
    }
}
