package de.prttstft.materialmensa.model;

import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unused")
public class FirebaseMeal {
    private Float priceGuests;
    private Float priceStudents;
    private Float priceWorkers;
    private HashMap<String, Object> downvotes;
    private HashMap<String, Object> upvotes;
    private List<String> allergens;
    private List<String> badges;
    private String category;
    private String categoryDe;
    private String categoryEn;
    private String date;
    private String descriptionDe;
    private String descriptionEn;
    private String image;
    private String nameDe;
    private String nameEn;
    private String pricetype;
    private String restaurant;
    private String subcategory;
    private String subcategoryEn;
    private String thumbnail;
    private int orderInfo;


    public FirebaseMeal() {

    }

    public FirebaseMeal(Meal meal) {
        this.priceGuests = meal.getPriceGuests();
        this.priceStudents = meal.getPriceStudents();
        this.priceWorkers = meal.getPriceWorkers();
        this.allergens = meal.getAllergens();
        this.badges = meal.getBadges();
        this.category = meal.getCategory();
        this.categoryDe = meal.getCategoryDe();
        this.categoryEn = meal.getCategoryEn();
        this.date = meal.getDate();
        this.descriptionDe = meal.getDescriptionDe();
        this.descriptionEn = meal.getDescriptionEn();
        this.image = meal.getImage();
        this.nameDe = meal.getNameDe();
        this.nameEn = meal.getNameEn();
        this.pricetype = meal.getPricetype();
        this.restaurant = meal.getRestaurant();
        this.subcategory = meal.getSubcategory();
        this.subcategoryEn = meal.getSubcategoryEn();
        this.thumbnail = meal.getThumbnail();
        this.orderInfo = meal.getOrderInfo();
    }


    public Float getPriceGuests() {
        return priceGuests;
    }

    public Float getPriceStudents() {
        return priceStudents;
    }

    public Float getPriceWorkers() {
        return priceWorkers;
    }

    public HashMap<String, Object> getDownvotes() {
        return downvotes;
    }

    public HashMap<String, Object> getUpvotes() {
        return upvotes;
    }

    public List<String> getAllergens() {
        return allergens;
    }

    public List<String> getBadges() {
        return badges;
    }

    public String getCategory() {
        return category;
    }

    public String getCategoryDe() {
        return categoryDe;
    }

    public String getCategoryEn() {
        return categoryEn;
    }

    public String getDate() {
        return date;
    }

    public String getDescriptionDe() {
        return descriptionDe;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public String getImage() {
        return image;
    }

    public String getNameDe() {
        return nameDe;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getPricetype() {
        return pricetype;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public String getSubcategoryEn() {
        return subcategoryEn;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public int getOrderInfo() {
        return orderInfo;
    }
}