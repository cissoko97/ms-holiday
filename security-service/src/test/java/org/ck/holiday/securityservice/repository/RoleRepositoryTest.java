package org.ck.holiday.securityservice.repository;

import org.assertj.core.api.Assertions;
import org.ck.holiday.securityservice.models.ERole;
import org.ck.holiday.securityservice.models.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void should_findRole_by_name_Ok() {
        Role role1 = new Role(ERole.PARENT);
        entityManager.persist(role1);

        Optional<Role> role = roleRepository.findByName(ERole.PARENT);

        assertNotNull(role);
        assertTrue(role.isPresent());
        assertEquals(ERole.PARENT, role.get().getName());
    }

    @Test
    void should_deleteRole_by_Ok() {
        Role role1 = new Role(ERole.PARENT);
        entityManager.persist(role1);

        Optional<Role> role = roleRepository.findByName(ERole.PARENT);

        roleRepository.delete(role.get());
        List<Role> all = roleRepository.findAll();
        Assertions.assertThat(all).hasSize(3).isNotEmpty();
    }

    @Test
    void should_emptyOk_by_Ok() {
        
        List<Role> all = roleRepository.findAll();
        Assertions.assertThat(all).hasSize(3).isNotEmpty();
    }
}