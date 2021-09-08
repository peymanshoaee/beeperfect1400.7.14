package com.project.springdemo.response;

public class LoginResponse {
    private String token;
    private long expireTime;
    private String result;
    private String description;
    private String doTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoTime() {
        return doTime;
    }

    public void setDoTime(String doTime) {
        this.doTime = doTime;
    }
}
