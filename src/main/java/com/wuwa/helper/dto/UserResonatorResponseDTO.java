package com.wuwa.helper.dto;

import java.time.LocalDate;
import java.util.UUID;

public record UserResonatorResponseDTO(UUID resonatorId, String resonatorName, LocalDate acquisitionDate, int currentLevel, String rankAscension) {
}
