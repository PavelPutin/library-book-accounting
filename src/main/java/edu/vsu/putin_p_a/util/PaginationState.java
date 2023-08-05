package edu.vsu.putin_p_a.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

public class PaginationState {
    private long current;
    private int pageSize;
    private long total;

    public static PaginationState init(long total) {
        PaginationState p = new PaginationState();
        p.update(total, 0, 5);
        return p;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        if (current < 0) { current = 0; }
        if (current >= getPagesAmount()) { current = getPagesAmount() - 1L; }
        this.current = current;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize < 0) { pageSize = 5; }
        this.pageSize = pageSize;
    }

    public long getPagesAmount() {
        return (long) Math.ceil((double) total / pageSize);
    }

    public void update(long total, long current, int booksPerPage) {
        setTotal(total);
        setCurrent(current);
        setPageSize(booksPerPage);
    }
}
