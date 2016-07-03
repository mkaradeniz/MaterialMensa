package de.prttstft.materialmensa.model;

import java.util.HashMap;

public class FirebaseMeal {
    private String nameDe;
    private String nameEn;
    private HashMap<String, Object> downvotes;
    private HashMap<String, Object> upvotes;

    @SuppressWarnings("unused")
    public FirebaseMeal() {

    }

    public FirebaseMeal(String nameDe, String nameEn) {
        this.nameDe = nameDe;
        this.nameEn = nameEn;
    }


    public String getNameEn() {
        return nameEn;
    }

    public String getNameDe() {
        return nameDe;
    }

    public HashMap<String, Object> getDownvotes() {
        return downvotes;
    }

    public HashMap<String, Object> getUpvotes() {
        return upvotes;
    }
}