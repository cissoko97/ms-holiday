package org.ck.holiday.securityservice.service.impl;

import jakarta.annotation.PostConstruct;
import org.ck.holiday.securityservice.models.ERole;
import org.ck.holiday.securityservice.models.Role;
import org.ck.holiday.securityservice.repository.RoleRepository;
import org.ck.holiday.securityservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;


    @PostConstruct
    void insertBasicRoles() {
        Role role;
        if (roleRepository.findByName(ERole.ADMIN).isEmpty()) {
            role = new Role();
            role.setName(ERole.ADMIN);
            roleRepository.save(role);
        }

        if (roleRepository.findByName(ERole.USER).isEmpty()) {
            role = new Role();
            role.setName(ERole.USER);
            roleRepository.save(role);
        }

        if (roleRepository.findByName(ERole.MODERATOR).isEmpty()) {
            role = new Role();
            role.setName(ERole.MODERATOR);
            roleRepository.save(role);
        }
    }
}
