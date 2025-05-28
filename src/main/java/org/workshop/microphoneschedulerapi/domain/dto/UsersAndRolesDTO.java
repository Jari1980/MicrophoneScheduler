package org.workshop.microphoneschedulerapi.domain.dto;

import lombok.Builder;
import org.workshop.microphoneschedulerapi.domain.model.UserRole;

@Builder
public record UsersAndRolesDTO(Long userId, String userName, UserRole role) {
}
