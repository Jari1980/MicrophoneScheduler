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
public class Character {
    @NonNull
    @Id
    @Column(nullable = false, unique = true)
    private int characterId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "actor_id")
    private Actor actor;
    @OneToOne
    @JoinColumn
    private Microphone microphoneId;
    @ManyToMany(mappedBy = "scenes", fetch = FetchType.EAGER)
    private List<Scene> scenes = new ArrayList<>();
}
