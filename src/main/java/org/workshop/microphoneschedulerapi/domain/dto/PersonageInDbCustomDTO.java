package org.workshop.microphoneschedulerapi.domain.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.workshop.microphoneschedulerapi.domain.entity.Personage;
import org.workshop.microphoneschedulerapi.domain.model.PersonageCustom;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class PersonageInDbCustomDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<PersonageCustom> personages;

}
