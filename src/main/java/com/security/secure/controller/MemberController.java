package com.security.secure.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.security.secure.config.JwtUtil;
import com.security.secure.config.MemberDetailsService;
import com.security.secure.entity.Member;
import com.security.secure.entity.Role;
import com.security.secure.repository.MemberRepository;
import com.security.secure.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MemberController {

    @Autowired private MemberRepository memberRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private MemberDetailsService memberDetailsService;
    @Autowired private JwtUtil jwtTokenUtil;

    @GetMapping("/member")
    public Collection<Member> getAllMember() {
        return memberRepository.findAll();
    }

    @GetMapping("/member/{id}")
    public Optional<Member> getMember(@PathVariable Long id) {
        return memberRepository.findById(id);
    }

    @PostMapping("/member")
    public Member createMember(@RequestBody Map<String, Object> body) {
        Member newMember = new Member();
        Optional<Role> role = roleRepository.findById(Long.valueOf(body.get("role").toString()));
        newMember.setUsername(body.get("username").toString());
        newMember.setPassword(body.get("password").toString());
        newMember.setRole(role.get());
        return memberRepository.save(newMember);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Map<String, Object> authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.get("username").toString(), authenticationRequest.get("password").toString()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password");
        }
        final UserDetails userDetails = memberDetailsService.loadUserByUsername(authenticationRequest.get("username").toString());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        Map<String, Object> authenticationResponse = new HashMap<>();
        authenticationResponse.put("jwt", jwt);
        return ResponseEntity.ok(authenticationResponse);
    }

    @PutMapping("/member/{id}")
    public Member changeMember(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Member member = memberRepository.findById(id).get();
        String password = member.getPassword();
        String prevPassword = body.get("password").toString();
        String newPassword = body.get("new-password").toString();

        if (!password.equals(prevPassword)) {
            return null;
        } else {
            member.setPassword(newPassword);
        }
        return memberRepository.save(member);
    }

}