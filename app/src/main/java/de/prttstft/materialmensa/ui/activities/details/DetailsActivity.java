package de.prttstft.materialmensa.ui.activities.details;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.model.Meal;

public class DetailsActivity extends AppCompatActivity {
    @Bind(R.id.activity_details_additives) TextView additives;
    @Bind(R.id.activity_details_allergens) TextView allergens;
    @Bind(R.id.activity_details_description) TextView mealDescription;
    @Bind(R.id.activity_details_description_no_image) TextView mealDescriptionNoImage;
    @Bind(R.id.activity_details_image) ImageView mealImage;
    @Bind(R.id.activity_details_image_container) FrameLayout imageContainer;
    @Bind(R.id.activity_details_name) TextView mealName;
    @Bind(R.id.activity_details_name_no_image) TextView mealNameNoImage;
    @Bind(R.id.activity_details_no_image_container) RelativeLayout noImageContainer;
    @Bind(R.id.activity_details_toolbar) Toolbar toolbar;
    Meal meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            String test = getIntent().getStringExtra("test");
            meal = new Gson().fromJson(test, Meal.class);
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

            if (meal.getImage() == null) {
                getSupportActionBar().setTitle(getString(R.string.restaurant_mensa_academica_paderborn));
                getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.colorPrimary));
            } else {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.background_toolbar_transparent));
            }
        }
    }

    private void setUpView() {
        if (meal.getImage() == null) {
            noImageContainer.setVisibility(View.VISIBLE);
            mealDescriptionNoImage.setText(meal.getCustomDescription());
            mealNameNoImage.setText(meal.getNameEn());
        } else {
            imageContainer.setVisibility(View.VISIBLE);
            mealDescription.setText(meal.getCustomDescription());
            mealName.setText(meal.getNameEn());
            Glide.with(this).load(meal.getImage()).into(mealImage);
        }

        allergens.setText(meal.getAllergens().toString());
        additives.setText(meal.getAllergens().toString());
    }
}
