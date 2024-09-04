package com.WheelHub.WheelHub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 255)
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @Column(name = "username", length = 255, unique = true, nullable = false)
    @NotBlank(message = "Username is mandatory")
    @Size(max = 255, message = "Username cannot exceed 255 characters")
    private String username;

    @Column(name = "password", length = 255, nullable = false)
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    private String password;

    @Column(name = "email", length = 255, unique = true, nullable = false)
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(max = 255, message = "Email cannot exceed 255 characters")
    private String email;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @Column(name = "created_at")
    @PastOrPresent(message = "Created at must be in the past or present")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @PastOrPresent(message = "Updated at must be in the past or present")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "seller")
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "user")
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    @OneToMany(mappedBy = "user")
    private List<SavedSearch> savedSearches;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
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