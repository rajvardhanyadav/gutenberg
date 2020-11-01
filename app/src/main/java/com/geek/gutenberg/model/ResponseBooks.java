package com.geek.gutenberg.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBooks {
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("previous")
    @Expose
    private Object previous;
    @SerializedName("results")
    @Expose
    private List<Book> books = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public List<Book> getResults() {
        return books;
    }

    public void setResults(List<Book> books) {
        this.books = books;
    }
}
