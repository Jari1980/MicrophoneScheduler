package org.workshop.microphoneschedulerapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Builder
public record ActorsConnectedToUsersDTO(int actorId, Long userId, String userName) {

}
