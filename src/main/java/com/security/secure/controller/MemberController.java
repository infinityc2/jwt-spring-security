package com.security.secure.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.security.secure.config.JwtUtil;
import com.security.secure.config.MemberDetailsService;
import com.security.secure.entity.Member;
import com.security.secure.entity.Role;
import com.security.secure.repository.MemberRepository;
import com.security.secure.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    @Autowired private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/members")
    @PreAuthorize("hasRole('ADMIN')")
    public Collection<Member> getAllMember() {
        return memberRepository.findAll();
    }

    @GetMapping("/members/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<Member> getMember(@PathVariable Long id) {
        return memberRepository.findById(id);
    }

    @PostMapping("/members/{roleList}")
    public Member createMember(@PathVariable List<Long> roleList, @RequestBody Map<String, Object> body) {
        Member newMember = new Member();
        Set<Role> roles = new HashSet<>();
        newMember.setUsername(body.get("username").toString());
        newMember.setPassword(passwordEncoder.encode(body.get("password").toString()));
        roleList.forEach(role -> {
            Optional<Role> r = roleRepository.findById(role);
            roles.add(r.get());
        });
        newMember.setRoles(roles);
        return memberRepository.save(newMember);
    }

    @PatchMapping("/members")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Member changePassword(@RequestBody Map<String, Object> body) throws Exception {
        Member member = memberRepository.findByUsername(body.get("username").toString());
        if (passwordEncoder.matches(body.get("prevPassword").toString(), member.getPassword())) {
            member.setPassword(passwordEncoder.encode(body.get("newPassword").toString()));
        } else {
            throw new Exception("Previous password not match");
        }
        
        return memberRepository.save(member);
    }

    @DeleteMapping("/members/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteMember(@PathVariable Long id) {
        memberRepository.deleteById(id);
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

}