package com.example.n22dccn018_nguyen_khac_tung_duong.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.n22dccn018_nguyen_khac_tung_duong.fragments.BookGridFragment;
import com.example.n22dccn018_nguyen_khac_tung_duong.fragments.CartFragment;
import com.example.n22dccn018_nguyen_khac_tung_duong.fragments.ProfileFragment;

public class ViewPaperAdapter extends FragmentPagerAdapter {

    public ViewPaperAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new BookGridFragment();
            case 1: return new CartFragment();
            case 2: return new ProfileFragment();
            default: return new BookGridFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
