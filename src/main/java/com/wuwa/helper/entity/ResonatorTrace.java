package com.wuwa.helper.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "resonator_trace")
public class ResonatorTrace {

    @Id
    @Column(name = "resonator_trace_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "description")
    private String description;
}
