package de.prttstft.materialmensa.model;

import java.util.HashMap;

public class FirebaseMeal {
    private String name;
    private HashMap<String, Object> downvotes;
    private HashMap<String, Object> upvotes;

    public FirebaseMeal() {

    }

    public FirebaseMeal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Object> getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(HashMap<String, Object> downvotes) {
        this.downvotes = downvotes;
    }

    public HashMap<String, Object> getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(HashMap<String, Object> upvotes) {
        this.upvotes = upvotes;
    }
}