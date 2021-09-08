package com.project.springdemo.response;

import java.io.Serializable;
import java.util.List;

public class RequestConsoleResponse implements Serializable {
    private List<RequestConsoleReport> requestConsoleReportList;
    private long totalElements;
    private int totalPages;
    private int numberOfElements;

    public List<RequestConsoleReport> getRequestConsoleReportList() {
        return requestConsoleReportList;
    }

    public void setRequestConsoleReportList(List<RequestConsoleReport> requestConsoleReportList) {
        this.requestConsoleReportList = requestConsoleReportList;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }
}
