package com.project.springdemo.response;

import java.io.Serializable;
import java.util.List;

public class UserConsoleResponse implements Serializable {
    private List<UserConsoleReport> userConsoleReportList;
    private long totalElements;
    private int totalPages;
    private int numberOfElements;

    public List<UserConsoleReport> getUserConsoleReportList() {
        return userConsoleReportList;
    }

    public void setUserConsoleReportList(List<UserConsoleReport> userConsoleReportList) {
        this.userConsoleReportList = userConsoleReportList;
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
