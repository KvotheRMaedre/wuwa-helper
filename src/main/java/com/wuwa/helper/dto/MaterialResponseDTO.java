package com.wuwa.helper.dto;

import java.util.UUID;

public record MaterialResponseDTO (UUID id, String description, String setName, int level, String materialType, Boolean isCraftable, UUID sourceMaterialId, String sourceMaterialName){
}
