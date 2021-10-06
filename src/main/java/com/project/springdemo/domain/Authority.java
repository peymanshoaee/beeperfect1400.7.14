package com.project.springdemo.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bp_authority")
public class Authority implements GrantedAuthority,Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bp_authority_seq")
    @SequenceGenerator(name = "bp_authority_seq", sequenceName = "bp_authority_id_seq", allocationSize = 1)

    @Column(name = "id")
    private Long id;

    @Column(name= "name")
    private String name;

    @Column(name= "code")
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getAuthority() {
        return code;
    }
}
