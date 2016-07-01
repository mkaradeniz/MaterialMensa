package de.prttstft.materialmensa.ui.activities.details;

import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.extras.DateTimeUtilities;
import de.prttstft.materialmensa.extras.UserSettings;
import de.prttstft.materialmensa.extras.Utilities;
import de.prttstft.materialmensa.model.Meal;
import timber.log.Timber;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static de.prttstft.materialmensa.constants.GeneralConstants.MEAL;

public class DetailsActivity extends AppCompatActivity {
    private static final String LOCALE_DE = "Deutsch";
    private static final String MIME_TYPE_TEXT = "text/plain";
    private static final String PLAY_STORE_URL = "https://goo.gl/HD2ed2";
    @SuppressWarnings("WeakerAccess") @Bind(R.id.activity_details_additives_allergens) TextView additivesAllergens;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.item_meal_description) TextView mealDescription;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.activity_details_description_no_image) TextView mealDescriptionNoImage;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.activity_details_image) ImageView mealImage;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.activity_details_image_container) FrameLayout imageContainer;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.activity_details_image_progress_bar) ProgressBar progressBar;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.activity_details_name) TextView mealName;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.activity_details_name_no_image) TextView mealNameNoImage;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.activity_details_no_image_container) RelativeLayout noImageContainer;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.activity_details_toolbar) Toolbar toolbar;
    private Meal meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getString(MEAL) != null) {
                meal = new Gson().fromJson(getIntent().getStringExtra(MEAL), Meal.class);
            }
        } else {
            finish();
        }

        setUpToolbar();
        setUpView();
    }


    private void setUpToolbar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if (meal.getImage().isEmpty()) {
                getSupportActionBar().setTitle(getString(R.string.activity_details_title));
                getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.colorPrimary));
            } else {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.background_toolbar_transparent));
            }
        }
    }

    private void setUpView() {
        if (meal.getImage().isEmpty()) {
            noImageContainer.setVisibility(VISIBLE);
            mealDescriptionNoImage.setText(meal.getCustomDescription());

            if (!UserSettings.getHideFiltered() && meal.isFiltered()) {
                mealNameNoImage.setTextColor(ContextCompat.getColor(this, R.color.materialRed900));
            }

            if (Utilities.getSystemLanguage().equals(LOCALE_DE)) {
                mealNameNoImage.setText(meal.getNameDe());

            } else {
                mealNameNoImage.setText(meal.getNameEn());
            }
        } else {
            imageContainer.setVisibility(VISIBLE);
            progressBar.setVisibility(VISIBLE);
            mealDescription.setText(meal.getCustomDescription());

            if (!UserSettings.getHideFiltered() && meal.isFiltered()) {
                mealName.setTextColor(ContextCompat.getColor(this, R.color.materialRed900));
            }

            if (Utilities.onWifi()) {
                Glide.with(this).
                        load(meal.getImage())
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                progressBar.setVisibility(GONE);
                                return false;
                            }
                        })
                        .into(mealImage);
            } else {
                Glide.with(this).load(meal.getThumbnail())
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                progressBar.setVisibility(GONE);
                                return false;
                            }
                        })
                        .into(mealImage);
            }

            if (Utilities.getSystemLanguage().equals(LOCALE_DE)) {
                mealName.setText(meal.getNameDe());
            } else {
                mealName.setText(meal.getNameEn());
            }
        }

        additivesAllergens.setText(buildAdditiveAllergenString(meal.getAllergens()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_details_share:
                shareMeal();
                return true;
            default:
                onBackPressed();
                return true;
        }
    }


    private String buildAdditiveAllergenString(List<String> additivesAllergens) {
        String builder = "";

        for (int i = 0; i < additivesAllergens.size(); i++) {
            String additiveAllergenString = getAdditiveAllergenString(additivesAllergens.get(i));
            if (!additiveAllergenString.isEmpty()) {
                builder = builder + additiveAllergenString + "\n";
            }
        }

        return builder;
    }

    private String buildShareString() {
        String shareString = getString(R.string.share_string_prefix, DateTimeUtilities.getShareDayString(meal.getDate()));

        if (Utilities.getSystemLanguage().equals(LOCALE_DE)) {
            shareString = shareString + meal.getNameDe();
        } else {
            shareString = shareString + meal.getNameEn();
        }

        shareString = shareString + getString(R.string.share_string_suffix, PLAY_STORE_URL);

        return shareString;
    }

    private String getAdditiveAllergenString(String additiveAllergen) {
        String[] additives = getResources().getStringArray(R.array.activity_settings_preferences_additives_array);
        String[] allergens = getResources().getStringArray(R.array.activity_settings_preferences_allergens_array);

        switch (additiveAllergen) {
            case "1":
                return additives[0];
            case "2":
                return additives[1];
            case "3":
                return additives[2];
            case "4":
                return additives[3];
            case "5":
                return additives[4];
            case "6":
                return additives[5];
            case "7":
                return additives[6];
            case "8":
                return additives[7];
            case "9":
                return additives[8];
            case "10":
                return additives[9];
            case "11":
                return additives[10];
            case "12":
                return additives[11];
            case "13":
                return additives[12];
            case "14":
                return additives[13];
            case "15":
                return additives[14];
            case "A1":
                return allergens[0];
            case "A2":
                return allergens[1];
            case "A3":
                return allergens[2];
            case "A4":
                return allergens[3];
            case "A5":
                return allergens[4];
            case "A6":
                return allergens[5];
            case "A7":
                return allergens[6];
            case "A8":
                return allergens[7];
            case "A9":
                return allergens[8];
            case "A10":
                return allergens[9];
            case "A11":
                return allergens[10];
            case "A12":
                return allergens[11];
            case "A13":
                return allergens[12];
            case "A14":
                return allergens[13];
            default:
                Timber.d("Default AdditiveAllergene called: $additiveAllergen");
                return "";
        }
    }

    private void shareMeal() {
        ShareCompat.IntentBuilder
                .from(this)
                .setType(MIME_TYPE_TEXT)
                .setText(buildShareString())
                .setChooserTitle(R.string.share_chooser_title)
                .startChooser();
    }
}