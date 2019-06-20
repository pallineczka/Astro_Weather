package com.example.astroweather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "Equation";

    Double dl1;
    Double sz1;
    Fragment fragment = null;

    private int COUNT = 2;

    ViewPagerAdapter(FragmentManager fm, Double dl1, Double sz1) {
        super(fm);
        this.dl1 = dl1;
        this.sz1 = sz1;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "\n\ndlugosc: " + dl1 + "\n\n");
        switch (position) {
            case 0:
                fragment = new FirstFragmentSun();
                break;
            case 1:
                fragment = new SecondFragmentMoon();
                break;
        }
        Bundle data = new Bundle();
        data.putDouble("dl1", dl1);
        data.putDouble("sz1", sz1);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public int getCount() {
        return COUNT;
    }
}
