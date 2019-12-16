package com.security.secure.dataloader;

import java.util.stream.Stream;

import com.security.secure.entity.Role;
import com.security.secure.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleDataloader implements ApplicationRunner {

    @Autowired private RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Stream.of("USER", "ADMIN").forEach(r -> {
            Role role = new Role();
            role.setRole(r);
            roleRepository.save(role);
        });
        roleRepository.findAll().forEach(System.out::println);
    }

    
}