package org.workshop.microphoneschedulerapi.domain.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class ScenePersonageMicrophoneCustom {

    private int microphoneId;
    private int sceneId;
    private int actNumber;
    private int sceneNumber;
    private String sceneName;
    private String personageName;
    private Long userId;
    private String userName;

}
