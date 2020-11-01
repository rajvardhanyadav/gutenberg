package com.geek.gutenberg.model;

public class Genre {
    private String tag;

    public Genre(String tag, int image) {
        this.tag = tag;
        this.image = image;
    }

    public String getTag() {
        return tag;
    }

    public int getImage() {
        return image;
    }

    private int image;
}
