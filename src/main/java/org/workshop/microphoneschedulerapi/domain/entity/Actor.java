package org.workshop.microphoneschedulerapi.domain.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
public class Actor {
    @NonNull
    @Id
    @Column(nullable = false, unique = true)
    private int actorId;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
