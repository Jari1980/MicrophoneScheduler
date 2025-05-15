package org.workshop.microphoneschedulerapi.domain.dto;

import lombok.Builder;

@Builder
public record UpdateSceneDTOForm(int sceneId, int actNumber, int sceneNumber, String sceneName) {
}
