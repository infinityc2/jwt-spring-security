package com.security.secure.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "MEMBER")
@Entity
@Data
@NoArgsConstructor
@SequenceGenerator(name = "member_seq")
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    private Long id;

    @NotNull
    @Column(unique = true, name = "username")
    private String username;

    @NotNull
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "MEMBER_ROLES", 
        joinColumns = { @JoinColumn(name = "MEMBER_ID") },
        inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") }
    )
    private Set<Role> roles;

}