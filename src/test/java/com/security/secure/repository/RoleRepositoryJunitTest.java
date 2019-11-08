package com.security.secure.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.security.secure.entity.Role;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleRepositoryJunitTest {

    @Autowired private TestEntityManager entityManager;
    @Autowired private RoleRepository roleRepository;

    @After
    public void tearDown() throws Exception {
        roleRepository.deleteAll();
    }

    @Test
    public void shouldBeFoundWhenExistRole() {
        Role role = new Role();
        role.setRole("EMPLOYEE");
        entityManager.persist(role);

        Role employeeRole = roleRepository.findByRole("EMPLOYEE");
        assertNotEquals("Employee shouldn't be null", employeeRole, null);
        assertEquals("Should be EMPLOYEE", employeeRole.getRole(), "EMPLOYEE");
    }
}