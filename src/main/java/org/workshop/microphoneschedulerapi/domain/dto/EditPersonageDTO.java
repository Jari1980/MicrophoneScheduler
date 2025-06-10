package org.workshop.microphoneschedulerapi.domain.dto;

import lombok.Builder;
import org.workshop.microphoneschedulerapi.domain.entity.User;

@Builder
public record EditPersonageDTO(int personageId, String personageName, int actorId) {
}
