package com.wuwa.helper.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public record UserResonatorDTO(UUID resonatorId, LocalDate acquisitionDate, int currentLevel, String rankAscension) {
}
