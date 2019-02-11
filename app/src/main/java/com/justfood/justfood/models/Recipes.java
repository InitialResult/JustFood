package com.justfood.justfood.models;

public class Recipes {
    private String title;
    private String imageUrl;
    private String f2f_url;

    public Recipes(String title, String imageUrl, String f2f_url) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.f2f_url = f2f_url;
    }

    public String getTitle() {
        return this.title;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String getF2f_url() {
        return this.f2f_url;
    }
}
