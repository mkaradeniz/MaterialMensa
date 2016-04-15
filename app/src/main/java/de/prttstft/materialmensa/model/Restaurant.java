
package de.prttstft.materialmensa.model;

import com.google.gson.annotations.SerializedName;

import de.prttstft.materialmensa.model.restaurants.Cafete;
import de.prttstft.materialmensa.model.restaurants.GrillCafe;
import de.prttstft.materialmensa.model.restaurants.MensaAcademicaPaderborn;
import de.prttstft.materialmensa.model.restaurants.MensaForumPaderborn;
import de.prttstft.materialmensa.model.restaurants.Mensula;
import de.prttstft.materialmensa.model.restaurants.OneWaySnack;

public class Restaurant {
    @SerializedName("mensa-academica-paderborn")
    private MensaAcademicaPaderborn mensaAcademicaPaderborn;
    @SerializedName("mensa-forum-paderborn")
    private MensaForumPaderborn mensaForumPaderborn;
    @SerializedName("cafete")
    private Cafete cafete;
    @SerializedName("mensula")
    private Mensula mensula;
    @SerializedName("one-way-snack")
    private OneWaySnack oneWaySnack;
    @SerializedName("grill-cafe")
    private GrillCafe grillCafe;

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