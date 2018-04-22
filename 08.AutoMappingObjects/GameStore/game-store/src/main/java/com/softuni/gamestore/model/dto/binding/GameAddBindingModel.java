package com.softuni.gamestore.model.dto.binding;

import org.hibernate.validator.constraints.URL;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public class GameAddBindingModel {

    private String title;
    private BigDecimal price;
    private Float size;
    private String trailer;
    private String thumbnail;
    private String description;
    private Date releaseDate;

    public GameAddBindingModel() {
    }

    public GameAddBindingModel(String title, BigDecimal price, Float size, String trailer, String thumbnail, String description, Date releaseDate) {
        this.title = title;
        this.price = price;
        this.size = size;
        this.trailer = trailer;
        this.thumbnail = thumbnail;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    @Pattern(regexp = "^[A-Z].{2,99}$")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @DecimalMin("0.0")
    @Digits(integer = 19, fraction = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    @Pattern(regexp = "[A-Za-z0-9]{11}")
    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @URL
    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Size(min = 20)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean validate() {

        Set<ConstraintViolation<GameAddBindingModel>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(this);
        if(violations.size()>0) {
            for(ConstraintViolation<GameAddBindingModel> v : violations)
                System.out.println(v.getMessage());

            return false;
        }

        if(this.size < 0.001) {
            System.out.println("size must be a positive number");
            return false;
        }

        return true;
    }
}
