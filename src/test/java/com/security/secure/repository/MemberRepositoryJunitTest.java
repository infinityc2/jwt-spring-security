package com.security.secure.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.HashSet;
import java.util.Set;

import com.security.secure.entity.Member;
import com.security.secure.entity.Role;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MemberRepositoryJunitTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private MemberRepository memberRepository;

    @After
    public void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    public void shouldBeFoundWhenExistMember() {
        Role role = new Role();
        role.setRole("MASTER");
        entityManager.persist(role);

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        Member member = new Member();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setUsername("masterAdmin");
        member.setPassword(passwordEncoder.encode("SecurePassworD"));
        member.setRoles(roles);
        entityManager.persist(member);

        Member master = memberRepository.findByUsername("masterAdmin");
        assertNotEquals("Master shouldn't be null", master, null);
        assertEquals("Should be match", passwordEncoder.matches("SecurePassworD", master.getPassword()), true);
        assertEquals("Should be masterAdmin", master.getUsername(), "masterAdmin");
    }
}