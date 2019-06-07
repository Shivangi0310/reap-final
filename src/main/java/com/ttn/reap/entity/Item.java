package com.ttn.reap.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private Integer quantity;

    @NotNull
    @NotBlank
    private Integer price;

    @NotNull
    @NotBlank
    private String imageURL;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Item(@NotNull @NotBlank String name, @NotNull @NotBlank Integer quantity, @NotNull @NotBlank Integer price, @NotNull @NotBlank String imageURL) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.imageURL = imageURL;
    }

    public Item() {
    }
}
