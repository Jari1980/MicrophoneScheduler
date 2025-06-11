package org.workshop.microphoneschedulerapi.domain.dto;

import lombok.Builder;
import org.workshop.microphoneschedulerapi.domain.entity.Personage;

import java.util.List;

@Builder
public record CustomScenePersonageDTO(int sceneId, int sceneNumber, int actNumber, String sceneName, List<Personage> personages) {
}
