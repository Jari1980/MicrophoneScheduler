package org.workshop.microphoneschedulerapi.domain.dto;

import lombok.Builder;

@Builder
public record CreateSceneDTOForm(String playName, int actNumber, int sceneNumber, String sceneName) {
}
