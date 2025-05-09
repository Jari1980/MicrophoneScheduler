package org.workshop.microphoneschedulerapi.domain.dto;

import lombok.Builder;

@Builder
public record MicrophonesInSceneDTOForm(String playName, int actNumber, int sceneNumber) {
}
