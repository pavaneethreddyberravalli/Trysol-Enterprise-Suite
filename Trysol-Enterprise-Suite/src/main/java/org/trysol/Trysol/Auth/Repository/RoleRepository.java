package org.trysol.Trysol.Auth.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.trysol.Trysol.Auth.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);
}
