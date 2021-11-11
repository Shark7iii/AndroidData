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

    public void setTitle(String title) { Title=title; }

    public void setImageView(int imageView) { this.imageView=imageView; }
}
