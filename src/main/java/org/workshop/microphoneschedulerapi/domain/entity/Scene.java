package org.workshop.microphoneschedulerapi.domain.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
public class Scene {
    @NonNull
    @Id
    @Column(nullable = false, unique = true)
    private int sceneId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "play_id")
    private Play play;
    private int actNumber;
    private int sceneNumber;
    private String sceneName;
    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE}) //fetch = FetchType.LAZY,
    @JoinTable(
            name = "scene_character",
            joinColumns = @JoinColumn(name = "scene_id", referencedColumnName = "sceneId"),
            inverseJoinColumns = @JoinColumn(name = "character_id", referencedColumnName = "characterId")
    )
    private List<Characterr> characters = new ArrayList<>();
}
