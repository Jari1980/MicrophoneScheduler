package org.workshop.microphoneschedulerapi.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.workshop.microphoneschedulerapi.domain.model.UserRole;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NonNull
    @Column(unique = true)
    private String userName;
    private UserRole userRole;
    private String password; //This will be hashed to start with, if theres time trying JWT token


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRole.getUserAuthorities();
    }

    @Override
    public String getUsername() {
        return userName;
    }
}
