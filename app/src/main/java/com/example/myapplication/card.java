package com.example.myapplication;

public class card {
    private String id;
    private int image;
    private String name;

    public card() {
        super();
    }

    public card(String id, int image, String name) {
        super();
        this.id = id;
        this.image = image;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}