package com.project.springdemo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class RequestConsoleRequest implements Serializable {
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("page")
    private Integer page;
    @JsonProperty("size")
    private Integer size;
    @JsonProperty("categoryCode")
    private String categoryCode;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }
}
