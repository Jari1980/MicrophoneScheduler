package org.workshop.microphoneschedulerapi.domain.dto;

import lombok.Builder;

@Builder
public record addActDTOForm(String playName, int act, int scenes) {
}
