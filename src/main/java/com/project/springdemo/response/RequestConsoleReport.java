package com.project.springdemo.response;

import com.project.springdemo.domain.User;

import java.io.Serializable;

public class RequestConsoleReport implements Serializable {
    private Long id;
    private String title;
    private String description;
    private Long minAmount;
    private Long maxAmount;
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Long minAmount) {
        this.minAmount = minAmount;
    }

    public Long getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Long maxAmount) {
        this.maxAmount = maxAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
