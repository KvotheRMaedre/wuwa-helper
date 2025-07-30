package com.wuwa.helper.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "ability_ascension_material")
public class AbilityAscensionMaterial {

    @Id
    @Column(name = "ability_ascension_material_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "ability_level")
    private int abilityLevel;

    @Column(name = "level_forgery")
    private int levelForgery;

    @Column(name = "quantity_forgery")
    private int quantityForgery;

    @Column(name = "level_drop")
    private int levelDrop;

    @Column(name = "quantity_drop")
    private int quantityDrop;

    @Column(name = "quantity_weekly_boss")
    private int quantityWeeklyBoss;

    @Column(name = "shell_credits")
    private int shellCredits;
}
