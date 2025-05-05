package org.workshop.microphoneschedulerapi.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
public class Microphone {
    @NonNull
    @Id
    @Column(nullable = false, unique = true)
    private int microphoneId;
}
