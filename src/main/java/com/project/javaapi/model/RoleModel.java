package com.project.javaapi.model;

import com.project.javaapi.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class RoleModel implements GrantedAuthority, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleName role_name;

    @Override
    public String getAuthority() {
        return role_name.toString();
    }

    public String getName() {
        return role_name.getName();
    }

}
