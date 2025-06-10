package org.workshop.microphoneschedulerapi.domain.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
//@EqualsAndHashCode
@Builder
@Entity
public class Personage {
    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(nullable = false, unique = true)
    private int personageId;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "actor_id")
    private Actor actor;
    /*
    @ManyToOne()
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "microphone_id")
    private Microphone microphoneId;
     */
    /*
    @ManyToMany(mappedBy = "characters", fetch = FetchType.EAGER) //, cascade = CascadeType.ALL)
    private List<Scene> scenes;// = new ArrayList<>();
     */
    //@JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "personage", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private List<Scene_character> scene_characters;
    @Column(unique = true)
    private String personageName;
}
