package org.workshop.microphoneschedulerapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.workshop.microphoneschedulerapi.domain.model.ScenePersonageMicrophoneCustom;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class AddSuggestedMicrophonesDTOForm {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ScenePersonageMicrophoneCustom> microphoneList;

}
