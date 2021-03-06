package com.security.secure.entity;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
// import javax.validation.Validation;
// import javax.validation.Validator;
// import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleJunitTest {

    @Autowired private TestEntityManager entityManager;
    // private Validator validator;

    @Before
    public void setup() {
        // ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        // validator = factory.getValidator();
    }
    
    @Test
    public void roleShouldBeNotNull() {
        try {
            Role role = new Role();
            role.setRole("ADMIN");
            entityManager.persist(role);
            entityManager.flush();
        } catch (ConstraintViolationException e) {
            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            assertEquals(violations, false);
            assertEquals(violations.size(), 1);
        } 
    }

    @Test(expected = ConstraintViolationException.class)
    public void shouldBeConstraintViolationExceptionWhenRoleIsNull() {
        Role role = new Role();
        entityManager.persistAndFlush(role);
    }
}