package de.prttstft.materialmensa.ui.activities.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.prttstft.materialmensa.MyApplication;
import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.ui.activities.about.AboutActivity;
import de.prttstft.materialmensa.ui.activities.main.presenter.MainPresenter;
import de.prttstft.materialmensa.ui.activities.main.presenter.MainPresenterImplementation;
import de.prttstft.materialmensa.ui.activities.main.view.MainView;
import de.prttstft.materialmensa.ui.activities.settings.SettingsActivity;
import de.prttstft.materialmensa.ui.fragments.main.MainFragmentPagerAdapter;

import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_ACADEMICA;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_CAFETE;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_FORUM;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_GRILL_CAFE;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_MENSULA;
import static de.prttstft.materialmensa.extras.Constants.RestaurantIdConstants.RESTAURANT_ID_ONE_WAY_SNACK;
import static de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractorImplementation.LIFESTYLE_LEVEL_FIVE_VEGAN;
import static de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractorImplementation.LIFESTYLE_NOT_SET;
import static de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractorImplementation.LIFESTYLE_PREF;
import static de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractorImplementation.LIFESTYLE_VEGAN;
import static de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractorImplementation.LIFESTYLE_VEGETARIAN;
import static de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractorImplementation.ROLE_GUEST;
import static de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractorImplementation.ROLE_PREF;
import static de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractorImplementation.ROLE_STAFF;
import static de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractorImplementation.ROLE_STUDENT;


public class MainActivity extends AppCompatActivity implements MainView {
    private static final Drawable DRAWER_HEADER_AVATAR_GUEST = ContextCompat.getDrawable(MyApplication.getAppContext(), R.drawable.ic_guest_black);
    private static final Drawable DRAWER_HEADER_AVATAR_STAFF = ContextCompat.getDrawable(MyApplication.getAppContext(), R.drawable.ic_staff_black);
    private static final Drawable DRAWER_HEADER_AVATAR_STUDENT = ContextCompat.getDrawable(MyApplication.getAppContext(), R.drawable.ic_school_black);
    private final MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), RESTAURANT_ID_ACADEMICA);
    @SuppressWarnings("WeakerAccess") @Bind(R.id.activity_main_drawer_layout) DrawerLayout drawerLayout;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.activity_main_navigation_view) NavigationView navigationView;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.activity_main_tab_layout) TabLayout tabLayout;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.activity_main_toolbar) Toolbar toolbar;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.activity_main_view_pager) ViewPager viewPager;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenterImplementation(this);

        setUpToolbar();
        setUpDrawerLayout();
        setUpDrawerHeader();
        setUpTabs();
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.getRestaurantStatus();
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.restaurant_mensa_academica_paderborn));
        }
    }

    private void setUpDrawerLayout() {
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_main_drawer_restaurant_mensa_academica_paderborn:
                        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), RESTAURANT_ID_ACADEMICA));
                        adapter.notifyDataSetChanged();
                        toolbar.setTitle(getString(R.string.restaurant_mensa_academica_paderborn));
                        menuItem.setChecked(true);
                        break;
                    case R.id.menu_main_drawer_restaurant_mensa_forum_paderborn:
                        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), RESTAURANT_ID_FORUM));
                        adapter.notifyDataSetChanged();
                        toolbar.setTitle(getString(R.string.restaurant_mensa_forum_paderborn));
                        menuItem.setChecked(true);
                        break;
                    case R.id.menu_main_drawer_restaurant_cafete:
                        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), RESTAURANT_ID_CAFETE));
                        adapter.notifyDataSetChanged();
                        toolbar.setTitle(getString(R.string.restaurant_cafete));
                        menuItem.setChecked(true);
                        break;
                    case R.id.menu_main_drawer_restaurant_mensula:
                        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), RESTAURANT_ID_MENSULA));
                        adapter.notifyDataSetChanged();
                        toolbar.setTitle(getString(R.string.restaurant_mensula));
                        menuItem.setChecked(true);
                        break;
                    case R.id.menu_main_drawer_restaurant_one_way_snack:
                        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), RESTAURANT_ID_ONE_WAY_SNACK));
                        adapter.notifyDataSetChanged();
                        toolbar.setTitle(getString(R.string.restaurant_one_way_snack));
                        menuItem.setChecked(true);
                        break;
                    case R.id.menu_main_drawer_restaurant_grill_cafe:
                        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), RESTAURANT_ID_GRILL_CAFE));
                        adapter.notifyDataSetChanged();
                        toolbar.setTitle(getString(R.string.restaurant_grill_cafe));
                        menuItem.setChecked(true);
                        break;
                    case R.id.menu_main_drawer_settings:
                        Intent startSettingsActivityIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                        startActivity(startSettingsActivityIntent);
                        break;
                    case R.id.menu_main_drawer_about:
                        Intent startAboutActivityIntent = new Intent(getApplicationContext(), AboutActivity.class);
                        startActivity(startAboutActivityIntent);
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void setUpDrawerHeader() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String role = sharedPreferences.getString(ROLE_PREF, ROLE_STUDENT);
        String lifestyle = sharedPreferences.getString(LIFESTYLE_PREF, LIFESTYLE_NOT_SET);

        RelativeLayout header = (RelativeLayout) navigationView.getHeaderView(0);
        FrameLayout frameLayout = (FrameLayout) header.getChildAt(0);
        ImageView avatar = (ImageView) frameLayout.getChildAt(1);
        TextView roleTextView = (TextView) header.getChildAt(1);
        TextView lifestyleTextView = (TextView) header.getChildAt(2);

        switch (role) {
            case ROLE_GUEST:
                roleTextView.setText(getString(R.string.activity_settings_preferences_role_guest));
                avatar.setImageDrawable(DRAWER_HEADER_AVATAR_GUEST);
                avatar.setScaleX(1.5f);
                avatar.setScaleY(1.5f);
                break;
            case ROLE_STAFF:
                roleTextView.setText(getString(R.string.activity_settings_preferences_role_staff));
                avatar.setImageDrawable(DRAWER_HEADER_AVATAR_STAFF);
                avatar.setScaleX(1.5f);
                avatar.setScaleY(1.5f);
                break;
            case ROLE_STUDENT:
                roleTextView.setText(getString(R.string.activity_settings_preferences_role_student));
                avatar.setImageDrawable(DRAWER_HEADER_AVATAR_STUDENT);
                avatar.setScaleX(1f);
                avatar.setScaleY(1f);
                break;
        }

        switch (lifestyle) {
            case LIFESTYLE_VEGAN:
                lifestyleTextView.setText(getString(R.string.activity_settings_preferences_lifestyle_vegan));
                break;
            case LIFESTYLE_VEGETARIAN:
                lifestyleTextView.setText(getString(R.string.activity_settings_preferences_lifestyle_vegetarian));
                break;
            case LIFESTYLE_NOT_SET:
                lifestyleTextView.setText(getString(R.string.activity_settings_preferences_lifestyle_default));
                break;
            case LIFESTYLE_LEVEL_FIVE_VEGAN:
                lifestyleTextView.setText(R.string.activity_settings_preferences_lifestyle_level_five_vegan_drawer);
                break;
        }
    }

    private void setUpTabs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int tabCount = Integer.valueOf(sharedPreferences.getString(getString(R.string.activity_settings_preferences_tabs_key), "8"));

        if (tabCount > 4) {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void restaurantClosed(int restaurant, String openingTime) {
        ImageView circle = (ImageView) ((LinearLayoutCompat) navigationView.getMenu().getItem(restaurant).getActionView()).getChildAt(0);
        TextView statusTextView = (TextView) ((LinearLayoutCompat) navigationView.getMenu().getItem(restaurant).getActionView()).getChildAt(1);

        if (openingTime != null) {
            statusTextView.setText(openingTime);
            statusTextView.setTextColor(ContextCompat.getColor(this, R.color.colorNegative));

            circle.setVisibility(View.GONE);
            statusTextView.setVisibility(View.VISIBLE);
        } else {
            Drawable circleColor = circle.getDrawable();

            if (circleColor instanceof ShapeDrawable) {
                ((ShapeDrawable) circleColor).getPaint().setColor(ContextCompat.getColor(this, R.color.colorNegative));

                circle.setVisibility(View.VISIBLE);
                statusTextView.setVisibility(View.GONE);
            } else if (circleColor instanceof GradientDrawable) {
                ((GradientDrawable) circleColor).setColor(ContextCompat.getColor(this, R.color.colorNegative));

                circle.setVisibility(View.VISIBLE);
                statusTextView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void restaurantOpen(int restaurant, String closingTime) {
        ImageView circle = (ImageView) ((LinearLayoutCompat) navigationView.getMenu().getItem(restaurant).getActionView()).getChildAt(0);
        TextView statusTextView = (TextView) ((LinearLayoutCompat) navigationView.getMenu().getItem(restaurant).getActionView()).getChildAt(1);

        if (closingTime != null) {
            statusTextView.setText(closingTime);
            statusTextView.setTextColor(ContextCompat.getColor(this, R.color.colorPositive));

            circle.setVisibility(View.GONE);
            statusTextView.setVisibility(View.VISIBLE);
        } else {
            Drawable circleColor = circle.getDrawable();

            if (circleColor instanceof ShapeDrawable) {
                ((ShapeDrawable) circleColor).getPaint().setColor(ContextCompat.getColor(this, R.color.colorPositive));

                circle.setVisibility(View.VISIBLE);
                statusTextView.setVisibility(View.GONE);
            } else if (circleColor instanceof GradientDrawable) {
                ((GradientDrawable) circleColor).setColor(ContextCompat.getColor(this, R.color.colorPositive));

                circle.setVisibility(View.VISIBLE);
                statusTextView.setVisibility(View.GONE);
            }
        }
    }
}