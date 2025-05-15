package org.workshop.microphoneschedulerapi.domain.dto;

import lombok.Builder;

@Builder
public record ManageActorToPersonage(int actorId, int personageId) {
}
