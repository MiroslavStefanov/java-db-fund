package com.softuni.gamestore.model.dto.view;

import java.math.BigDecimal;

public class DetailGameViewModel {

    private String title;
    private String price;
    private String description;
    private String releaseDate;

    public DetailGameViewModel() {
    }

    public DetailGameViewModel(String title, String price, String description, String releaseDate) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return String.format("Title: %s\r\nPrice: %s\r\nDescription: %s\r\nRelease date: %s",
                this.title, this.price, this.description, this.releaseDate);
    }
}
