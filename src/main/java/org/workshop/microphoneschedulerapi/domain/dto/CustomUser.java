package org.workshop.microphoneschedulerapi.domain.dto;

import lombok.Builder;

@Builder
public record CustomUser(Long userId, String userName) {
}
