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
    private String actorId;
    @OneToOne
    @JoinColumn
    private User user;
}
