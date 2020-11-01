package com.geek.gutenberg.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Book {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("authors")
    @Expose
    private List<Author> authors = null;
    @SerializedName("bookshelves")
    @Expose
    private List<String> bookshelves = null;
    @SerializedName("download_count")
    @Expose
    private Integer downloadCount;
    @SerializedName("formats")
    @Expose
    private Formats formats;
    @SerializedName("languages")
    @Expose
    private List<String> languages = null;
    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("subjects")
    @Expose
    private List<String> subjects = null;
    @SerializedName("title")
    @Expose
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<String> getBookshelves() {
        return bookshelves;
    }

    public void setBookshelves(List<String> bookshelves) {
        this.bookshelves = bookshelves;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Formats getFormats() {
        return formats;
    }

    public void setFormats(Formats formats) {
        this.formats = formats;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
