package dev.Zerphyis.microRabbitMq.Domain.model.users;

import dev.Zerphyis.microRabbitMq.Domain.model.users.typeRole.TypeRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
public class Users implements UserDetails {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotBlank
    @Column(name = "nome")
    private String name;

    @NotBlank
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank
    @Column(name = "senha")
    private String password;

    @Builder.Default
    private boolean active = true;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "role", nullable = false)
    private TypeRole role;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of((new SimpleGrantedAuthority("ROLE_" + role.name())));
    }

    @PrePersist
    public void onCreate(){
        this.createdAt=LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate(){
        this.updatedAt=LocalDateTime.now();
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
