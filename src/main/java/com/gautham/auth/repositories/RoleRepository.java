package com.gautham.auth.repositories;

import com.gautham.auth.models.AppRole;
import com.gautham.auth.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole roleName);
}