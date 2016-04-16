package de.prttstft.materialmensa.ui.fragments.main;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.joda.time.DateTime;

import de.prttstft.materialmensa.MyApplication;
import de.prttstft.materialmensa.R;
import de.prttstft.materialmensa.extras.Utilities;

public class MainFragmentPagerAdapter extends FragmentStatePagerAdapter {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());
    final int PAGE_COUNT = Integer.valueOf(sharedPreferences.getString(MyApplication.getAppContext().getString(R.string.key_pref_tabs), "8"));

    private int restaurant;

    public MainFragmentPagerAdapter(FragmentManager fm, int restaurant) {
        super(fm);
        this.restaurant = restaurant;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return MainFragment.newInstance(position, restaurant);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getDateTabTitle(position);
    }

    private String getDateTabTitle(int page) {
        DateTime date = new DateTime().plusDays(page);
        return Utilities.getDayString(date);
    }
}