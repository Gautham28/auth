package com.gautham.auth.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Enumerated(EnumType.STRING) // Tells JPA to store the Enum as text (e.g., "ROLE_ADMIN") instead of a number
    private AppRole roleName;
}