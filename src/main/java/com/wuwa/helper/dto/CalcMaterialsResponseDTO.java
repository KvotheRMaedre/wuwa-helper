package com.wuwa.helper.dto;

import java.util.List;

public record CalcMaterialsResponseDTO(List<Integer> totalForgeryMaterialsByLevel, int totalCollectable, int totalBossMaterial, int totalShellCredits) {
}
