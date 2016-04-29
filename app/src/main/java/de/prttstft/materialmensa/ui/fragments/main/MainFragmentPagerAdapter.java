package de.prttstft.materialmensa.ui.fragments.main;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.joda.time.DateTime;

import de.prttstft.materialmensa.extras.DateTimeUtilities;
import de.prttstft.materialmensa.extras.UserSettings;

public class MainFragmentPagerAdapter extends FragmentStatePagerAdapter {
    Drawable myDrawable;
    private int restaurant;

    public MainFragmentPagerAdapter(FragmentManager fm, int restaurant) {
        super(fm);
        this.restaurant = restaurant;
    }

    @Override
    public int getCount() {
        return UserSettings.getTabCount();
    }

    @Override
    public Fragment getItem(int position) {
        return MainFragment.newInstance(restaurant, position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    private String getDateTabTitle(int page) {
        DateTime date = new DateTime().plusDays(page);
        return DateTimeUtilities.getDayString(date);
    }
}