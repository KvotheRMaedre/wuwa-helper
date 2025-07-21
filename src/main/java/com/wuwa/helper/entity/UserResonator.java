package com.wuwa.helper.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class UserResonator {

    @EmbeddedId
    private UserResonatorId id;

    @Column(name = "acquisition_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate acquisitionDate;

    @Column(name = "current_level")
    private int currentLevel;

    @Column(name = "rank_ascension")
    private String rankAscension;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("resonatorId")
    @JoinColumn(name = "resonator_id")
    private Resonator resonator;

    public UserResonator() {
    }

    public UserResonator(UserResonatorId id, LocalDate acquisitionDate, int currentLevel, String rankAscension, User user, Resonator resonator) {
        this.id = id;
        this.acquisitionDate = acquisitionDate;
        this.currentLevel = currentLevel;
        this.rankAscension = rankAscension;
        this.user = user;
        this.resonator = resonator;
    }

    public UserResonatorId getId() {
        return id;
    }

    public void setId(UserResonatorId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Resonator getResonator() {
        return resonator;
    }

    public void setResonator(Resonator resonator) {
        this.resonator = resonator;
    }

    public LocalDate getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(LocalDate acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getRankAscension() {
        return rankAscension;
    }

    public void setRankAscension(String rankAscension) {
        this.rankAscension = rankAscension;
    }
}
