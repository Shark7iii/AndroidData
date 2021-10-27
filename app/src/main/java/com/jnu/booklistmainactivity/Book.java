package com.jnu.booklistmainactivity;

public class Book {
    private String Title;
    private int imageView;
    Book() {

    }

    public Book(String title, int imageView) {
        this.Title = title;
        this.imageView = imageView;
    }

    public String getTitle() {
        return Title;
    }

    public int getimageView() {
        return imageView;
    }

}
