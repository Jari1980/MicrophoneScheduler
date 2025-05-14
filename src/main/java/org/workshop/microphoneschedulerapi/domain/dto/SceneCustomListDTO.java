package org.workshop.microphoneschedulerapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.workshop.microphoneschedulerapi.domain.entity.Scene;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class SceneCustomListDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Scene> scenes;

}
