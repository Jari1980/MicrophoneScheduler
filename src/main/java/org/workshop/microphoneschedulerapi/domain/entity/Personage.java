package org.workshop.microphoneschedulerapi.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    private int personageId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "actor_id")
    private Actor actor;
    @ManyToOne()
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "microphone_id")
    private Microphone microphoneId;
    @ManyToMany(mappedBy = "characters", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Scene> scenes;// = new ArrayList<>();
    private String personageName;
}
