package org.workshop.microphoneschedulerapi.domain.dto;

import lombok.Builder;

@Builder
public record ManagePersonageToScene(int sceneId, int personageId) {
}
