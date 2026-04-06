package com.example.viewpagernnavigation;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewpagerAdater extends FragmentStatePagerAdapter { // 

    public ViewpagerAdater(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    @NonNull
    public Fragment getItem(int position) { // [cite: 188]
        switch (position) { // [cite: 189]
            case 0:
                return new FirstFragment(); // [cite: 191, 192]
            case 1:
                return new SecondFragment(); // [cite: 193, 194]
            case 2:
                return new ThirdFragment(); // [cite: 195, 196]
            default:
                return null; // [cite: 198]
        }
    }

    @Override
    public int getCount() {
        return 3; // [cite: 201]
    }
}