package org.workshop.microphoneschedulerapi.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
public class Scene_character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long scene_character_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "scene_id")
    private Scene scene;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "personage_id")
    private Personage personage;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "microphone_id")
    private Microphone microphone;
}
