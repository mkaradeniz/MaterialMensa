package de.prttstft.materialmensa.ui.fragments.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MainFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private int day;

    public MainFragmentPagerAdapter(FragmentManager fm, int day) {
        super(fm);
        this.day = day;
    }


    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public Fragment getItem(int position) {
        return MainFragment.newInstance(day, position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}