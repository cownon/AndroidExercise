package com.example.nguyen_khac_tung_duong_n22dccn018.adapters;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.nguyen_khac_tung_duong_n22dccn018.fragments.BookGridFragment;
import com.example.nguyen_khac_tung_duong_n22dccn018.fragments.CartFragment;
import com.example.nguyen_khac_tung_duong_n22dccn018.fragments.ProfileFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
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
