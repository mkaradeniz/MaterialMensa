package de.prttstft.materialmensa.model;

import com.google.gson.annotations.SerializedName;

import de.prttstft.materialmensa.model.restaurants.Cafete;
import de.prttstft.materialmensa.model.restaurants.GrillCafe;
import de.prttstft.materialmensa.model.restaurants.MensaAcademicaPaderborn;
import de.prttstft.materialmensa.model.restaurants.MensaForumPaderborn;
import de.prttstft.materialmensa.model.restaurants.Mensula;
import de.prttstft.materialmensa.model.restaurants.OneWaySnack;

public class Restaurant {
    @SuppressWarnings("unused") @SerializedName("mensa-academica-paderborn") private MensaAcademicaPaderborn mensaAcademicaPaderborn;
    @SuppressWarnings("unused") @SerializedName("mensa-forum-paderborn") private MensaForumPaderborn mensaForumPaderborn;
    @SuppressWarnings("unused") @SerializedName("cafete") private Cafete cafete;
    @SuppressWarnings("unused") @SerializedName("mensula") private Mensula mensula;
    @SuppressWarnings("unused") @SerializedName("one-way-snack") private OneWaySnack oneWaySnack;
    @SuppressWarnings("unused") @SerializedName("grill-cafe") private GrillCafe grillCafe;

    public MensaAcademicaPaderborn getMensaAcademicaPaderborn() {
        return mensaAcademicaPaderborn;
    }

    public MensaForumPaderborn getMensaForumPaderborn() {
        return mensaForumPaderborn;
    }

    public Cafete getCafete() {
        return cafete;
    }

    public Mensula getMensula() {
        return mensula;
    }

    public OneWaySnack getOneWaySnack() {
        return oneWaySnack;
    }

    public GrillCafe getGrillCafe() {
        return grillCafe;
    }
}