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
public class Personage {
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(nullable = false, unique = true)
    private int characterId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "actor_id")
    private Actor actor;
    @OneToOne
    @JoinColumn(name = "microphone_id")
    private Microphone microphoneId;
    @ManyToMany(mappedBy = "characters", fetch = FetchType.EAGER)
    private List<Scene> scenes;// = new ArrayList<>();
    private String name;
}
