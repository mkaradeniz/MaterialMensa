package de.prttstft.materialmensa.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Meal {
    @SerializedName("date") private String date;
    @SerializedName("name_de") private String nameDe;
    @SerializedName("name_en") private String nameEn;
    @SerializedName("description_de") private String descriptionDe;
    @SerializedName("description_en") private String descriptionEn;
    @SerializedName("category_de") private String categoryDe;
    @SerializedName("category_en") private String categoryEn;
    @SerializedName("subcategory") private String subcategory;
    @SerializedName("subcategory_en") private String subcategoryEn;
    @SerializedName("priceStudents") private float priceStudents;
    @SerializedName("priceWorkers") private float priceWorkers;
    @SerializedName("priceGuests") private float priceGuests;
    @SerializedName("allergens") private List<String> allergens = new ArrayList<>();
    @SerializedName("order_info") private int orderInfo;
    @SerializedName("badges") private List<String> badges = new ArrayList<>();
    @SerializedName("restaurant") private String restaurant;
    @SerializedName("pricetype") private String pricetype;
    @SerializedName("image") private String image;
    @SerializedName("thumbnail") private String thumbnail;
    private String priceString;
    private int orderNumber;
    private String customDescription;

    // Getters & Setters

    public String getDate() {
        return date;
    }

    public String getNameDe() {
        return nameDe;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getDescriptionDe() {
        return descriptionDe;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public String getCategoryDe() {
        return categoryDe;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public String getPriceString() {
        return this.priceString;
    }

    public void setPriceString(String priceString) {
        this.priceString = priceString;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomDescription() {
        return customDescription;
    }

    public void setCustomDescription(String customDescription) {
        this.customDescription = customDescription;
    }


    // Helpers

    public String getBadge() {
        if (badges != null && badges.size() > 0) {
            return badges.get(0);
        } else {
            return "";
        }
    }
}