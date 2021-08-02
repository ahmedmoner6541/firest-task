package com.ahmedmoner.firesttask.Model;

import java.io.Serializable;

public class Products implements Serializable {
   String id ;
    String name;
    String description;
    int price;
    int oldPrice;
    int rating;
    String type;
    String percentDiscount;

    String image;

    boolean isRecommended ;
    boolean isFavourite;
    boolean isPpopular ;
    boolean isOffers ;

    public Products() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(int oldPrice) {
        this.oldPrice = oldPrice;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(String percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isRecommended() {
        return isRecommended;
    }

    public void setRecommended(boolean recommended) {
        isRecommended = recommended;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public boolean isPpopular() {
        return isPpopular;
    }

    public void setPpopular(boolean ppopular) {
        isPpopular = ppopular;
    }

    public boolean isOffers() {
        return isOffers;
    }

    public void setOffers(boolean offers) {
        isOffers = offers;
    }

    public Products(String id, String name, String description, int price, int oldPrice, int rating, String type, String percentDiscount, String image, boolean isRecommended, boolean isFavourite, boolean isPpopular, boolean isOffers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.oldPrice = oldPrice;
        this.rating = rating;
        this.type = type;
        this.percentDiscount = percentDiscount;
        this.image = image;
        this.isRecommended = isRecommended;
        this.isFavourite = isFavourite;
        this.isPpopular = isPpopular;
        this.isOffers = isOffers;
    }
}
