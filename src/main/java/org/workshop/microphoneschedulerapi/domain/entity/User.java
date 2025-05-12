package org.workshop.microphoneschedulerapi.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.workshop.microphoneschedulerapi.domain.model.UserRole;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NonNull
    @Column(unique = true)
    private String userName;
    private UserRole userRole;
    private String password; //This will be hashed to start with, if theres time trying JWT token
}
