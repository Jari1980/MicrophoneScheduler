package org.workshop.microphoneschedulerapi.domain.dto;

import lombok.Builder;

@Builder
public record CreatePersonageDTOForm(String personageName) {
}
