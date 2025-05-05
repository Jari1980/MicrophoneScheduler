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
public class User {
    @NonNull
    @Id
    @Column(nullable = false, unique = true)
    private int userId;
    private String userName;
    private String role; //Will mostlikely be enum for differebt roles
    private String password; //This will be hashed to start with, if theres time trying JWT token
}
