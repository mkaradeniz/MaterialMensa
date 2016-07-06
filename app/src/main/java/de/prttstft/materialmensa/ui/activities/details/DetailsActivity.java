package de.prttstft.materialmensa.ui.activities.details;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.extras.Analytics;
import de.prttstft.materialmensa.extras.DateTimeUtilities;
import de.prttstft.materialmensa.extras.Utilities;
import de.prttstft.materialmensa.model.Meal;
import timber.log.Timber;

import static de.prttstft.materialmensa.constants.GeneralConstants.MEAL;

public class DetailsActivity extends AppCompatActivity {
    private static final String LOCALE_DE = "Deutsch";
    private static final String MIME_TYPE_TEXT = "text/plain";
    private static final String PLAY_STORE_URL = "https://goo.gl/HD2ed2";

    @Bind(R.id.activity_details_additives_allergens) TextView additivesAllergens;
    @Bind(R.id.activity_details_image) ImageView mealImage;
    @Bind(R.id.activity_details_collapsing_toolbar_layout) CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.activity_details_toolbar) Toolbar toolbar;
    @Bind(R.id.activity_details_floating_action_button) FloatingActionButton floatingActionButton;

    private Meal meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        getExtras();
        setUpToolbar();
        setUpView();

        Analytics.activityDetailsViewed();
    }


    private void getExtras() {
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getString(MEAL) != null) {
                meal = new Gson().fromJson(getIntent().getStringExtra(MEAL), Meal.class);
            }
        } else {
            finish();
        }
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setUpView() {
        if (Utilities.getSystemLanguage().equals(LOCALE_DE)) {
            collapsingToolbarLayout.setTitle(meal.getNameDe());
        } else {
            collapsingToolbarLayout.setTitle(meal.getNameEn());
        }

        if (meal.getImage().isEmpty()) {
            mealImage.setImageResource(R.drawable.placeholder_meal_blur);
        } else {
            Glide.with(this)
                    .load(meal.getThumbnail())
                    .fitCenter()
                    .into(mealImage);
        }

        additivesAllergens.setText(buildAdditiveAllergenString(meal.getAllergens()));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            default:
                onBackPressed();
                return true;
        }
    }


    @OnClick(R.id.activity_details_floating_action_button)
    public void onFloatingActionButtonClicked() {
        shareMeal();
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

        Analytics.mealShared();
    }
}