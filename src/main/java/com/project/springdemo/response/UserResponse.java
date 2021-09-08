package com.project.springdemo.response;

import java.io.Serializable;

public class UserResponse implements Serializable {

    private UserConsoleReport userConsoleReport;
    private String result;
    private String description;
    private String doTime;

    public UserConsoleReport getUserConsoleReport() {
        return userConsoleReport;
    }

    public void setUserConsoleReport(UserConsoleReport userConsoleReport) {
        this.userConsoleReport = userConsoleReport;
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
