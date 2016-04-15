package de.prttstft.materialmensa.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static de.prttstft.materialmensa.extras.Constants.PRICE_TYPE_WEIGHTED;
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

    public String getThumbnail() {
        return thumbnail;
    }


    // Public Methods

    public String getPriceString(String user_type) {
        String priceGuest = round(getPriceGuests(), 2);
        String priceStaff = round(getPriceWorkers(), 2);
        String priceStudents = round(getPriceStudents(), 2);

        switch (user_type) {
            case USER_TYPE_STUDENT:
                if (pricetype.equals(PRICE_TYPE_WEIGHTED)) {
                    return "Price per 100gr: " + priceStudents + "€";
                } else {
                    return priceStudents + "€";
                }
            case USER_TYPE_STAFF:
                if (pricetype.equals(PRICE_TYPE_WEIGHTED)) {
                    return "Price per 100gr: " + priceStaff + "€";
                } else {
                    return priceStaff + "€";
                }
            case USER_TYPE_GUEST:
                if (pricetype.equals(PRICE_TYPE_WEIGHTED)) {
                    return "Price per 100gr: " + priceGuest + "€";
                } else {
                    return priceGuest + "€";
                }
            default:
                if (pricetype.equals(PRICE_TYPE_WEIGHTED)) {
                    return "Price per 100gr: " + priceStudents + "€" + " / " + priceStaff + "€" + " / " + priceGuest + "€";
                } else {
                    return priceStudents + "€" + " / " + priceStaff + "€" + " / " + priceGuest + "€";
                }
        }
    }

    public int getOrderNumber() {
        switch (getRestaurant()) {
            case "mensa-academica-paderborn":
                switch (getSubcategoryEn()) {
                    case "Default Menu":
                        return 0;
                    case "Dish":
                        return 1;
                    case "Pasta":
                        return 2;
                    case "Wok":
                        return 3;
                    case "Grill":
                        return 4;
                    case "Default Dessert":
                        return 7;
                    case "Counter Dessert":
                        return 8;
                    default:
                        switch (getCategoryEn()) {
                            case "Side Dish":
                                return 6;
                            case "Soups":
                                return 5;
                        }
                }
                break;
            case "mensa-forum-paderborn":
                switch (getCategory()) {
                    case "dish-default":
                        return 0;
                    case "dish":
                        return 1;
                    case "dish-grill":
                        return 2;
                    case "sidedish":
                        return 3;
                    case "dessert-counter":
                        return 4;
                }
                break;
            default:
                switch (getCategory()) {
                    case "classic":
                        return 0;
                    case "dish":
                        return 0;
                    case "wrap":
                        return 0;
                    case "offer":
                        return 0;
                    case "dessert-counter":
                        return 4;
                    case "sandwich":
                        return 1;
                    case "appetizer":
                        return 1;
                    case "lahmacun":
                        return 2;
                    case "maincourses":
                        return 2;
                    case "dessert":
                        return 3;
                }
                break;
        }
        return 99;
    }


    // Private Methods

    private String round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.toString();
    }
}