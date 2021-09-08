package com.project.springdemo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class UserConsoleRequest implements Serializable {
    @JsonProperty("username")
    private String username;
    @JsonProperty("page")
    private Integer page;
    @JsonProperty("size")
    private Integer size;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "UserConsoleRequest{" +
                "username='" + username + '\'' +
                ", page=" + page +
                ", size=" + size +
                '}';
    }
}
