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

    private int id;
    private int microphoneId;
    private int sceneId;
    private int actNumber;
    private String sceneName;
    private String personageName;
    private Long userId;
    private String userName;

}
