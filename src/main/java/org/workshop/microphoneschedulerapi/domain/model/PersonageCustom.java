package org.workshop.microphoneschedulerapi.domain.model;


import jakarta.persistence.Entity;
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
public class PersonageCustom {

    private int personageId;
    private String playName;
    private String personageName;
    private String actorName;
    private List<Scene> scenes;

}
