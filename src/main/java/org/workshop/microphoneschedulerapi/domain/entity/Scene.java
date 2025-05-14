package org.workshop.microphoneschedulerapi.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int sceneId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "play_id")
    private Play play;
    private int actNumber;
    private int sceneNumber;
    private String sceneName;
    @ManyToMany(fetch = FetchType.EAGER)//,cascade = {CascadeType.ALL}) //PERSIST, CascadeType.MERGE}) //fetch = FetchType.LAZY,
    @JoinTable(
            name = "scene_character",
            joinColumns = @JoinColumn(name = "scene_id", referencedColumnName = "sceneId"),
            inverseJoinColumns = @JoinColumn(name = "personage_id", referencedColumnName = "personageId")
    )
    private List<Personage> characters;// = new ArrayList<>();
}
