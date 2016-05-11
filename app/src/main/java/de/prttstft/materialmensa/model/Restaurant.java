package de.prttstft.materialmensa.model;

import com.google.gson.annotations.SerializedName;

import de.prttstft.materialmensa.model.restaurants.BistroHotspot;
import de.prttstft.materialmensa.model.restaurants.Cafete;
import de.prttstft.materialmensa.model.restaurants.GrillCafe;
import de.prttstft.materialmensa.model.restaurants.MensaAcademicaPaderborn;
import de.prttstft.materialmensa.model.restaurants.MensaAtrium;
import de.prttstft.materialmensa.model.restaurants.MensaBasilica;
import de.prttstft.materialmensa.model.restaurants.MensaForumPaderborn;
import de.prttstft.materialmensa.model.restaurants.Mensula;
import de.prttstft.materialmensa.model.restaurants.OneWaySnack;

public class Restaurant {
    @SuppressWarnings("unused") @SerializedName("bistro-hotspot") private BistroHotspot bistroHotspot;
    @SuppressWarnings("unused") @SerializedName("cafete") private Cafete cafete;
    @SuppressWarnings("unused") @SerializedName("grill-cafe") private GrillCafe grillCafe;
    @SuppressWarnings("unused") @SerializedName("mensa-academica-paderborn") private MensaAcademicaPaderborn mensaAcademicaPaderborn;
    @SuppressWarnings("unused") @SerializedName("mensa-forum-paderborn") private MensaForumPaderborn mensaForumPaderborn;
    @SuppressWarnings("unused") @SerializedName("mensa-hamm") private MensaBasilica mensaBasilica;
    @SuppressWarnings("unused") @SerializedName("mensa-lippstadt") private MensaAtrium mensaAtrium;
    @SuppressWarnings("unused") @SerializedName("mensula") private Mensula mensula;
    @SuppressWarnings("unused") @SerializedName("one-way-snack") private OneWaySnack oneWaySnack;

    public BistroHotspot getBistroHotspot() {
        return bistroHotspot;
    }

    public Cafete getCafete() {
        return cafete;
    }

    public GrillCafe getGrillCafe() {
        return grillCafe;
    }

    public MensaAcademicaPaderborn getMensaAcademicaPaderborn() {
        return mensaAcademicaPaderborn;
    }

    public MensaAtrium getMensaAtrium() {
        return mensaAtrium;
    }

    public MensaBasilica getMensaBasilica() {
        return mensaBasilica;
    }

    public MensaForumPaderborn getMensaForumPaderborn() {
        return mensaForumPaderborn;
    }

    public Mensula getMensula() {
        return mensula;
    }

    public OneWaySnack getOneWaySnack() {
        return oneWaySnack;
    }
}