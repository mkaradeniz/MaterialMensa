package de.prttstft.materialmensa.ui.activities.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.ui.activities.main.view.MainView;
import de.prttstft.materialmensa.ui.fragments.main.MainFragmentPagerAdapter;


public class MainActivity extends AppCompatActivity implements MainView {
    @Bind(R.id.activity_main_toolbar) Toolbar toolbar;
    @Bind(R.id.activity_main_view_pager) ViewPager viewPager;
    @Bind(R.id.activity_main_tab_layout) TabLayout tabLayout;
    //private MainAdapter adapter;
    //private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        viewPager.setAdapter(new MainFragmentPagerAdapter(getSupportFragmentManager(), this));
        tabLayout.setupWithViewPager(viewPager);
    }
}
