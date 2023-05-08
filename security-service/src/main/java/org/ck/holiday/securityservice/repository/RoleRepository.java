package org.ck.holiday.securityservice.repository;

import org.ck.holiday.securityservice.models.ERole;
import org.ck.holiday.securityservice.models.Role;
import org.ck.holiday.securityservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole role);
}
