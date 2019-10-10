package com.security.secure.repository;

import com.security.secure.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RoleRepository extends JpaRepository<Role, Long>{

    Role findByRole(String role);
}