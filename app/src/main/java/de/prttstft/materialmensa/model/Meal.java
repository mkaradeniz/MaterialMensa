package de.prttstft.materialmensa.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static de.prttstft.materialmensa.extras.Constants.USER_TYPE_GUEST;
import static de.prttstft.materialmensa.extras.Constants.USER_TYPE_STAFF;
import static de.prttstft.materialmensa.extras.Constants.USER_TYPE_STUDENT;

public class Meal {
    @SerializedName("date")
    private String date;
    @SerializedName("name")
    private String name;
    @SerializedName("name_en")
    private String nameEn;
    @SerializedName("description")
    private String description;
    @SerializedName("description_en")
    private String descriptionEn;
    @SerializedName("category")
    private String category;
    @SerializedName("category_en")
    private String categoryEn;
    @SerializedName("subcategory")
    private String subcategory;
    @SerializedName("subcategory_en")
    private String subcategoryEn;
    @SerializedName("priceStudents")
    private float priceStudents;
    @SerializedName("priceWorkers")
    private float priceWorkers;
    @SerializedName("priceGuests")
    private float priceGuests;
    @SerializedName("allergens")
    private List<String> allergens = new ArrayList<String>();
    @SerializedName("order_info")
    private int orderInfo;
    @SerializedName("badges")
    private List<String> badges = new ArrayList<String>();
    @SerializedName("restaurant")
    private String restaurant;
    @SerializedName("pricetype")
    private String pricetype;
    @SerializedName("image")
    private String image;
    @SerializedName("thumbnail")
    private String thumbnail;


    // Getters

    private static float round(float d, int decimalPlace) {
        return BigDecimal.valueOf(d).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public String getCategory() {
        return category;
    }

    public String getCategoryEn() {
        return categoryEn;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public String getSubcategoryEn() {
        return subcategoryEn;
    }

    public float getPriceStudents() {
        return priceStudents;
    }

    public float getPriceWorkers() {
        return priceWorkers;
    }

    public float getPriceGuests() {
        return priceGuests;
    }

    public List<String> getAllergens() {
        return allergens;
    }

    public int getOrderInfo() {
        return orderInfo;
    }

    public List<String> getBadges() {
        return badges;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getPricetype() {
        return pricetype;
    }

    public String getImage() {
        return image;
    }


    // Public Methods

    public String getThumbnail() {
        return thumbnail;
    }


    // Private Methods

    public String getPriceString(String user_type) {
        switch (user_type) {
            case USER_TYPE_STUDENT:
                return round(getPriceStudents(), 2) + "€";
            case USER_TYPE_STAFF:
                return getPriceWorkers() + "€";
            case USER_TYPE_GUEST:
                return round(getPriceGuests(), 2) + "€";
            default:
                return round(getPriceStudents(), 2) + "€" + " / " + getPriceWorkers() + "€" + " / " + getPriceGuests() + "€";
        }
    }

}