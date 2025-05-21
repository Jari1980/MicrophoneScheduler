package org.workshop.microphoneschedulerapi.domain.model;


import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class PersonageCustom {

    private int personageId;
    private String playName;
    private String personageName;
    private String actorName;

}
