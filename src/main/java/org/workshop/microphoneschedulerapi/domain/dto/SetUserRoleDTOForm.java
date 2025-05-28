package org.workshop.microphoneschedulerapi.domain.dto;

import org.workshop.microphoneschedulerapi.domain.model.UserRole;

public record SetUserRoleDTOForm(Long userId, UserRole userRole) {
}
