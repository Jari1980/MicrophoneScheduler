package org.workshop.microphoneschedulerapi.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
public class Play {
    @NonNull
    @Id
    @Column(nullable = false, unique = true)
    private String playName;
    private LocalDate dateCreated;
    private String description;
}
