package org.workshop.microphoneschedulerapi.domain.dto;

import lombok.Builder;

@Builder
public record CustomMicrophoneListDTO(int sceneId, String sceneName, Integer personageId, String personageName,
                                      int userId, String userName, Integer microphoneId, String microphoneName) {
}
