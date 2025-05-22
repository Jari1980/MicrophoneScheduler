package org.workshop.microphoneschedulerapi.domain.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class ActorOwnSceneCustom {

    private int sceneId;
    private int actNumber;
    private int sceneNumber;
    private String sceneName;
    private String personageName;
    private int microphoneId;
    private String microphoneName;

}
