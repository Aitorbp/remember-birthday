package com.example.rememberdate;

public class Birthday {
    String name;
    String image;
    Long date;

    public Birthday(String name, String image, Long date) {
        this.name = name;
        this.image = image;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Long getDate() {
        return date;
    }



}
