package com.project.springdemo.domain;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "bp_status")
public class Status implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bp_status_seq")
    @SequenceGenerator(name = "bp_status_seq", sequenceName = "bp_status_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
