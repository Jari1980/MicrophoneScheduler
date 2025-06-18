package org.workshop.microphoneschedulerapi.domain.dto;

import lombok.Builder;

@Builder
public record AddMicrophoneDTOForm(Long scene_characterId, int microphoneId) {
}
