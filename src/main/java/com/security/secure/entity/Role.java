package com.security.secure.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "ROLE")
@Entity
@SequenceGenerator(name = "role_seq")
@Data
@NoArgsConstructor
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    private Long id;

    @NotNull
    @Pattern(regexp = "^[A-Z]+$")
    @Column(unique = true, name = "role")
    private String role;
    
}