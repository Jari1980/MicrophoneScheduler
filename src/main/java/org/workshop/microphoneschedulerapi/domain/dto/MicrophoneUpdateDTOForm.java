package org.workshop.microphoneschedulerapi.domain.dto;

import lombok.Builder;

@Builder
public record MicrophoneUpdateDTOForm(int microphoneId, String microphoneName) {
}
