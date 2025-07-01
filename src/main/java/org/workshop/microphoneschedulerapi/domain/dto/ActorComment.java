package org.workshop.microphoneschedulerapi.domain.dto;

import lombok.Builder;

@Builder
public record ActorComment(Long id, String comment) {
}
