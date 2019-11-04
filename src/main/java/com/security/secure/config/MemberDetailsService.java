package com.security.secure.config;

import java.util.ArrayList;

import com.security.secure.entity.Member;
import com.security.secure.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberDetailsService implements UserDetailsService {

    @Autowired private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username);

        if (member == null) {
            throw new UsernameNotFoundException("Username '" + username + "' not found");
        }
        return new User(member.getUsername(), member.getPassword(), new ArrayList<>());
    }
    
}