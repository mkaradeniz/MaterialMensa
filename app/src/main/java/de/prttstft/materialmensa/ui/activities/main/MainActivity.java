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
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.prttstft.materialmensa.R;
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
import static de.prttstft.materialmensa.extras.Utilities.L;


public class MainActivity extends AppCompatActivity implements MainView {
    @Bind(R.id.activity_main_drawer_layout) DrawerLayout drawerLayout;
    @Bind(R.id.activity_main_navigation_view) NavigationView navigationView;
    @Bind(R.id.activity_main_tab_layout) TabLayout tabLayout;
    @Bind(R.id.activity_main_toolbar) Toolbar toolbar;
    @Bind(R.id.activity_main_view_pager) ViewPager viewPager;
    private MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), RESTAURANT_ID_ACADEMICA);
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenterImplementation(this);

        setUpToolbar();

        setupDrawerLayout();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        int tabCount = Integer.valueOf(sharedPreferences.getString(getString(R.string.key_pref_tabs), "8"));

        if (tabCount > 4) {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.restaurant_mensa_academica_paderborn));
        }
    }

    private void setupDrawerLayout() {
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        presenter.getRestaurantStatus();

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
                        L("About.");
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
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
    public void restaurantClosed(int restaurant) {
        ImageView circle = (ImageView) ((LinearLayoutCompat) navigationView.getMenu().getItem(restaurant).getActionView()).getChildAt(0);
        Drawable circleColor = circle.getDrawable();

        if (circleColor instanceof ShapeDrawable) {
            ((ShapeDrawable) circleColor).getPaint().setColor(ContextCompat.getColor(this, R.color.color_negative));
        } else if (circleColor instanceof GradientDrawable) {
            ((GradientDrawable) circleColor).setColor(ContextCompat.getColor(this, R.color.color_negative));
        }
    }

    @Override
    public void restaurantOpen(int restaurant) {
        ImageView circle = (ImageView) ((LinearLayoutCompat) navigationView.getMenu().getItem(restaurant).getActionView()).getChildAt(0);
        Drawable circleColor = circle.getDrawable();

        if (circleColor instanceof ShapeDrawable) {
            ((ShapeDrawable) circleColor).getPaint().setColor(ContextCompat.getColor(this, R.color.color_positive));
        } else if (circleColor instanceof GradientDrawable) {
            ((GradientDrawable) circleColor).setColor(ContextCompat.getColor(this, R.color.color_positive));
        }
    }
}