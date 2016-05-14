package de.prttstft.materialmensa.ui.activities.main;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
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
import de.prttstft.materialmensa.extras.DateTimeUtilities;
import de.prttstft.materialmensa.extras.UserSettings;
import de.prttstft.materialmensa.ui.activities.about.AboutActivity;
import de.prttstft.materialmensa.ui.activities.main.presenter.MainPresenter;
import de.prttstft.materialmensa.ui.activities.main.presenter.MainPresenterImplementation;
import de.prttstft.materialmensa.ui.activities.main.view.MainView;
import de.prttstft.materialmensa.ui.activities.settings.SettingsActivity;
import de.prttstft.materialmensa.ui.fragments.main.MainFragment;
import de.prttstft.materialmensa.ui.fragments.main.MainFragmentPagerAdapter;

import static de.prttstft.materialmensa.extras.RestaurantUtilites.getRestaurantIcon;
import static de.prttstft.materialmensa.extras.RestaurantUtilites.getRestaurantName;
import static de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractorImplementation.LIFESTYLE_LEVEL_FIVE_VEGAN;
import static de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractorImplementation.LIFESTYLE_NOT_SET;
import static de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractorImplementation.LIFESTYLE_VEGAN;
import static de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractorImplementation.LIFESTYLE_VEGETARIAN;
import static de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractorImplementation.ROLE_GUEST;
import static de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractorImplementation.ROLE_STAFF;
import static de.prttstft.materialmensa.ui.fragments.main.interactor.MainFragmentInteractorImplementation.ROLE_STUDENT;

public class MainActivity extends AppCompatActivity implements MainView {
    private static final Drawable DRAWER_HEADER_AVATAR_GUEST = ContextCompat.getDrawable(MyApplication.getAppContext(), R.drawable.ic_guest_black);
    private static final Drawable DRAWER_HEADER_AVATAR_STAFF = ContextCompat.getDrawable(MyApplication.getAppContext(), R.drawable.ic_staff_black);
    private static final Drawable DRAWER_HEADER_AVATAR_STUDENT = ContextCompat.getDrawable(MyApplication.getAppContext(), R.drawable.ic_school_black);
    @SuppressWarnings("WeakerAccess") @Bind(R.id.activity_main_collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.activity_main_drawer_layout) DrawerLayout drawerLayout;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.activity_main_navigation_view) NavigationView navigationView;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.activity_main_restaurant_status_text) TextView statusText;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.activity_main_tab_layout) TabLayout tabLayout;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.activity_main_toolbar) Toolbar toolbar;
    @SuppressWarnings("WeakerAccess") @Bind(R.id.activity_main_view_pager) ViewPager viewPager;
    private MainPresenter presenter;
    private int currentRestaurant = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenterImplementation(this);

        setUpToolbar();
        setDay(0);
        setUpTabs();
        setUpDrawerIcons();
        setUpDrawerLayout();
        setUpDrawerHeader();
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.getRestaurantStatus();
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setUpTabs() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (MainFragment.actionMode != null) {
                    MainFragment.actionMode.finish();
                }
                statusText.setVisibility(View.GONE);
                setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            Drawable tabIcon = ResourcesCompat.getDrawable(getResources(), getRestaurantIcon(i), null);
            assert tabIcon != null;
            Drawable wrappedTabIcon = DrawableCompat.wrap(tabIcon);
            DrawableCompat.setTintList(wrappedTabIcon, ColorStateList.valueOf(Color.BLACK));

            //noinspection ConstantConditions
            tabLayout.getTabAt(i).setIcon(wrappedTabIcon);
        }

        setCurrentTab(UserSettings.getDefaultRestaurant());
    }

    private void setUpDrawerIcons() {
        for (int i = 0; i < navigationView.getMenu().size() - 2; i++) {
            navigationView.getMenu().getItem(i).setIcon(DateTimeUtilities.getDateIcon(i));
            navigationView.getMenu().getItem(i).setTitle(DateTimeUtilities.getDayStringLong(i));
        }
    }

    private void setUpDrawerLayout() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_main_drawer_0:
                        setDay(0);
                        break;
                    case R.id.menu_main_drawer_1:
                        setDay(1);
                        break;
                    case R.id.menu_main_drawer_2:
                        setDay(2);
                        break;
                    case R.id.menu_main_drawer_3:
                        setDay(3);
                        break;
                    case R.id.menu_main_drawer_4:
                        setDay(4);
                        break;
                    case R.id.menu_main_drawer_5:
                        setDay(5);
                        break;
                    case R.id.menu_main_drawer_6:
                        setDay(6);
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
        String role = UserSettings.getRole();
        String lifestyle = UserSettings.getLifestyle();

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
                lifestyleTextView.setText(getString(R.string.activity_settings_preferences_lifestyle_meat));
                break;
            case LIFESTYLE_LEVEL_FIVE_VEGAN:
                lifestyleTextView.setText(R.string.activity_settings_preferences_lifestyle_level_five_vegan_drawer);
                break;
        }
    }

    private void getRestaurantStatus(int restaurant) {
        currentRestaurant = restaurant;
        statusText.setVisibility(View.GONE);
        presenter.getRestaurantStatus();
    }

    private void setCurrentTab(int position) {
        collapsingToolbarLayout.setTitle(getRestaurantName(position));
        getRestaurantStatus(position);
    }

    private void setDay(int day) {
        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), day));
        viewPager.getAdapter().notifyDataSetChanged();
        viewPager.setCurrentItem(UserSettings.getDefaultRestaurant());
        navigationView.getMenu().getItem(day).setChecked(true);

        setCurrentTab(UserSettings.getDefaultRestaurant());
    }

    private void setRestaurantClosed(int position) {
        Drawable tabIcon = ResourcesCompat.getDrawable(getResources(), getRestaurantIcon(position), null);
        assert tabIcon != null;
        Drawable wrappedTabIcon = DrawableCompat.wrap(tabIcon);
        DrawableCompat.setTintList(wrappedTabIcon, ContextCompat.getColorStateList(this, R.color.restaurant_status_closed));

        //noinspection ConstantConditions
        tabLayout.getTabAt(position).setIcon(wrappedTabIcon);
    }

    private void setRestaurantOpen(int position) {
        Drawable tabIcon = ResourcesCompat.getDrawable(getResources(), getRestaurantIcon(position), null);
        assert tabIcon != null;
        Drawable wrappedTabIcon = DrawableCompat.wrap(tabIcon);
        DrawableCompat.setTintList(wrappedTabIcon, ContextCompat.getColorStateList(this, R.color.restaurant_status_open));

        //noinspection ConstantConditions
        tabLayout.getTabAt(position).setIcon(wrappedTabIcon);
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
        setRestaurantClosed(restaurant);

        if (restaurant == currentRestaurant) {
            if (openingTime != null) {
                statusText.setText(openingTime);
                statusText.setVisibility(View.VISIBLE);
            } else {
                statusText.setText(getResources().getString(R.string.drawer_main_restaurant_status_closed));
                statusText.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void restaurantOpen(int restaurant, String closingTime) {
        setRestaurantOpen(restaurant);

        if (restaurant == currentRestaurant) {
            if (closingTime != null) {
                statusText.setText(closingTime);
                statusText.setVisibility(View.VISIBLE);
            } else {
                statusText.setVisibility(View.GONE);
            }
        }
    }
}