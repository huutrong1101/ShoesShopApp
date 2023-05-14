package com.example.shoesshop.Model;

public class Category {
    private String id;
    private String name;
    private String title;
    private String image;

    public Category(String id, String name, String title, String image) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
