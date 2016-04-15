package de.prttstft.materialmensa.ui.fragments.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import de.prttstft.materialmensa.extras.Utilities;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    // SharedPref
    final int PAGE_COUNT = 8;

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return MainFragment.newInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Utilities.getDateTabTitle(position);
    }
}