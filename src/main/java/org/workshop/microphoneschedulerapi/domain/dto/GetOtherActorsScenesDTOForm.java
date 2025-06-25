package org.workshop.microphoneschedulerapi.domain.dto;

import lombok.Builder;

@Builder
public record GetOtherActorsScenesDTOForm(Long userId, String playName) {
}
