package org.workshop.microphoneschedulerapi.domain.dto;

import lombok.Builder;

@Builder
public record CustomMicrophoneListDTO(Long scene_characterId, int sceneId, String sceneName, Integer personageId, String personageName,
                                      Long userId, String userName, Integer microphoneId, String microphoneName) {
}
