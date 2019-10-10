package com.security.secure.controller;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import com.security.secure.entity.Role;
import com.security.secure.entity.Member;
import com.security.secure.repository.RoleRepository;
import com.security.secure.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired private MemberRepository memberRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/member")
    public Collection<Member> getAllMember() {
        return memberRepository.findAll();
    }

    @GetMapping("/member/{id}")
    public Optional<Member> getMember(@PathVariable Long id) {
        return memberRepository.findById(id);
    }

    @PostMapping("/sign-up")
    public Member signUp(@RequestBody Map<String, Object> body) {
        Member newMember = new Member();
        Role role = roleRepository.findByRole("USER");
        newMember.setUsername(body.get("username").toString());
        newMember.setPassword(bCryptPasswordEncoder.encode(body.get("password").toString()));
        newMember.setRole(role);
        return memberRepository.save(newMember);
    }

}