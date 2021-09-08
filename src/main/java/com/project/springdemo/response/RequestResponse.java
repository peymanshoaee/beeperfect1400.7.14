package com.project.springdemo.response;

import java.io.Serializable;

public class RequestResponse implements Serializable {
    private RequestConsoleReport requestConsoleReport;
    private String result;
    private String description;
    private String doTime;

    public RequestConsoleReport getRequestConsoleReport() {
        return requestConsoleReport;
    }

    public void setRequestConsoleReport(RequestConsoleReport requestConsoleReport) {
        this.requestConsoleReport = requestConsoleReport;
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
