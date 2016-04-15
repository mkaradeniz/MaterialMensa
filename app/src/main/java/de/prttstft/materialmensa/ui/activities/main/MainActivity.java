package de.prttstft.materialmensa.ui.activities.main;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.ui.activities.main.view.MainView;
import de.prttstft.materialmensa.ui.fragments.main.MainFragmentPagerAdapter;

import static de.prttstft.materialmensa.extras.Constants.NavigationDrawerConstants.NAVIGATION_DRAWER_RESTAURANT_ACADEMICA;
import static de.prttstft.materialmensa.extras.Constants.NavigationDrawerConstants.NAVIGATION_DRAWER_RESTAURANT_CAFETE;
import static de.prttstft.materialmensa.extras.Constants.NavigationDrawerConstants.NAVIGATION_DRAWER_RESTAURANT_FORUM;
import static de.prttstft.materialmensa.extras.Constants.NavigationDrawerConstants.NAVIGATION_DRAWER_RESTAURANT_GRILL_CAFE;
import static de.prttstft.materialmensa.extras.Constants.NavigationDrawerConstants.NAVIGATION_DRAWER_RESTAURANT_MENSULA;
import static de.prttstft.materialmensa.extras.Constants.NavigationDrawerConstants.NAVIGATION_DRAWER_RESTAURANT_ONE_WAY_SNACK;
import static de.prttstft.materialmensa.extras.Utilities.L;


public class MainActivity extends AppCompatActivity implements MainView {
    @Bind(R.id.activity_main_coordinator_layout) CoordinatorLayout coordinatorLayout;
    @Bind(R.id.activity_main_drawer_layout) DrawerLayout drawerLayout;
    @Bind(R.id.activity_main_navigation_view) NavigationView navigationView;
    @Bind(R.id.activity_main_tab_layout) TabLayout tabLayout;
    @Bind(R.id.activity_main_toolbar) Toolbar toolbar;
    @Bind(R.id.activity_main_view_pager) ViewPager viewPager;
    MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), NAVIGATION_DRAWER_RESTAURANT_ACADEMICA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setupDrawerLayout();

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        // SharedPref
        /*if (tabCount > 5) {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }*/
    }

    private void setupDrawerLayout() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.getGroupId() == R.id.drawer_main_group2) {
                    navigationView.getMenu().setGroupCheckable(R.id.drawer_main_group1, false, true);
                    navigationView.getMenu().setGroupCheckable(R.id.drawer_main_group2, true, true);
                } else {
                    navigationView.getMenu().setGroupCheckable(R.id.drawer_main_group1, true, true);
                    navigationView.getMenu().setGroupCheckable(R.id.drawer_main_group2, false, true);
                }

                menuItem.setChecked(true);

                switch (menuItem.getItemId()) {
                    case R.id.menu_main_drawer_restaurant_mensa_academica_paderborn:
                        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), NAVIGATION_DRAWER_RESTAURANT_ACADEMICA));
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.menu_main_drawer_restaurant_mensa_forum_paderborn:
                        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), NAVIGATION_DRAWER_RESTAURANT_FORUM));
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.menu_main_drawer_restaurant_cafete:
                        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), NAVIGATION_DRAWER_RESTAURANT_CAFETE));
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.menu_main_drawer_restaurant_mensula:
                        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), NAVIGATION_DRAWER_RESTAURANT_MENSULA));
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.menu_main_drawer_restaurant_one_way_snack:
                        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), NAVIGATION_DRAWER_RESTAURANT_ONE_WAY_SNACK));
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.menu_main_drawer_restaurant_grill_cafe:
                        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), NAVIGATION_DRAWER_RESTAURANT_GRILL_CAFE));
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.menu_main_drawer_settings:
                        L("Settings.");
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
}