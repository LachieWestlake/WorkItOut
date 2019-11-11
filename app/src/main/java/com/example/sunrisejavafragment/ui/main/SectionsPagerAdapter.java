package com.example.sunrisejavafragment.ui.main;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sunrisejavafragment.FragmentViewModel;
import com.example.sunrisejavafragment.R;
import com.example.sunrisejavafragment.calc.GeoLocation;

import java.util.TimeZone;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter{

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.homeTabText, R.string.rangeTabText};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        TimeZone tz = TimeZone.getDefault();

        switch (position)
        {
            case 0:
                return HomeFragment.newInstance(new GeoLocation("Melbourne", -37.50, 145.01, tz));

            case 1:
                return SelectRange.newInstance(new GeoLocation("Melbourne", -37.50, 145.01, tz));

            default:
                return null;
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return TAB_TITLES.length;
    }
}