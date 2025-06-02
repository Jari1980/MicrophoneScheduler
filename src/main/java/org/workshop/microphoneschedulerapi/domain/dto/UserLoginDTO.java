package org.workshop.microphoneschedulerapi.domain.dto;

import lombok.Builder;

@Builder
public record UserLoginDTO(String jwtToken, String userName, String userRole) {
}
