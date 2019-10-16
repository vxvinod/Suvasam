package com.example.suvasam.model;

public class Awareness {

    private String title;
    private String imageUrl;
    private int id;
    private String description;
    private String date;

    public Awareness() {

    }

    public Awareness(String title, String imageUrl, int id, String description, String date) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.id = id;
        this.description = description;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
