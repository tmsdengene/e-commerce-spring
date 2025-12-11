package com.gibesystems.ecommerce.shared;

import java.util.List;

/**
 * Generic pagination response wrapper that can contain any type of data.
 * This class encapsulates paginated data along with pagination metadata.
 *
 * @param <T> The type of data contained in the response
 */
public class PageResponse<T> {
    private List<T> data;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;

    /**
     * Default constructor
     */
    public PageResponse() {
    }

    /**
     * Constructor with all parameters
     *
     * @param data The list of data items
     * @param pageNumber The current page number (0-indexed)
     * @param pageSize The size of the page
     * @param totalElements The total number of elements
     * @param totalPages The total number of pages
     * @param hasNext Whether there is a next page
     * @param hasPrevious Whether there is a previous page
     */
    public PageResponse(List<T> data, int pageNumber, int pageSize, long totalElements, int totalPages, boolean hasNext, boolean hasPrevious) {
        this.data = data;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }

    /**
     * Constructor from Spring's Page object
     *
     * @param page The Spring Page object
     */
    public PageResponse(org.springframework.data.domain.Page<T> page) {
        this.data = page.getContent();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.hasNext = page.hasNext();
        this.hasPrevious = page.hasPrevious();
    }

    // Getters and Setters
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    @Override
    public String toString() {
        return "PageResponse{" +
                "data=" + data +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                ", hasNext=" + hasNext +
                ", hasPrevious=" + hasPrevious +
                '}';
    }
}
