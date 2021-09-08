package com.project.springdemo.domain;

import com.project.springdemo.enums.Authority;

import javax.persistence.*;
import java.util.List;

@Entity
public class Roles {
    @Id
    @GeneratedValue
    private Long Id;

    private String name;

    @ElementCollection(targetClass = Authority.class,fetch = FetchType.EAGER)
    private List<Authority> authorities;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
}
