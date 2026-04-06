package com.example.nguyen_khac_tung_duong_n22dccn018.models;

public class Book {
    private String title;

    private String author;
    private double price;
    private int imageResource;

    public Book(String title,String author, double price, int imageResource) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.imageResource = imageResource;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public double getPrice() { return price; }
    public int getImageResource() { return imageResource; }
}
