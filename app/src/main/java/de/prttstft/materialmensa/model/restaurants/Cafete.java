package de.prttstft.materialmensa.model.restaurants;

import com.google.gson.annotations.SerializedName;

public class Cafete {
    @SuppressWarnings("unused") @SerializedName("status") private String status;

    public String getStatus() {
        return status;
    }
}