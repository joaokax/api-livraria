package com.project.javaapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
//import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(unique = true)
    public String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String password;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "persons_roles",
            joinColumns = {@JoinColumn(name = "person_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    public List<RoleModel> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    public List<RoleModel> getRoles() {
        return roles;
    }
}
