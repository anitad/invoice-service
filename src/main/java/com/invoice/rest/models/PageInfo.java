package com.invoice.rest.models;

public class PageInfo {
    public static final long DEFAULT_OFFSET = 0;
    public static final int DEFAULT_MAX_NO_OF_ROWS = 100;
    private int offset;
    private int limit;
    private int nextOffset;
    private long totalRecords;
    // private List<T> elements;


    public PageInfo(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public int getNextOffset() {
        return offset + limit;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }
}
