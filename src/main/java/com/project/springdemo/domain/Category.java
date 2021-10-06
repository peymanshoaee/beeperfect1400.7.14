package com.project.springdemo.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bp_category")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bp_category_seq")
    @SequenceGenerator(name = "bp_category_seq", sequenceName = "bp_category_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "title")
    private String title;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
