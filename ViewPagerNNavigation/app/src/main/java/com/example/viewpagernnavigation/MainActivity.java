package com.example.viewpagernnavigation;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ViewPager mViewPager; // [cite: 202]
    BottomNavigationView mBottomNavigationView; // [cite: 203]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Ánh xạ View
        mViewPager = findViewById(R.id.view_pager); // [cite: 204]
        mBottomNavigationView = findViewById(R.id.bottom_navigation); // [cite: 205]

        // 2. Khởi tạo Adapter và gán vào ViewPager
        ViewpagerAdater viewpagerAdater = new ViewpagerAdater(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT); // [cite: 206]
        mViewPager.setAdapter(viewpagerAdater); // [cite: 207]
        mViewPager.setCurrentItem(0); // [cite: 208]

        // 3. Sự kiện khi vuốt ViewPager -> Đổi màu icon dưới BottomNavigation
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // [cite: 210]
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { // [cite: 212]
            }

            @Override
            public void onPageSelected(int position) { // [cite: 214]
                switch (position){ // [cite: 215]
                    case 0:
                        mBottomNavigationView.getMenu().findItem(R.id.person).setChecked(true); // [cite: 217]
                        break; // [cite: 219]
                    case 1:
                        mBottomNavigationView.getMenu().findItem(R.id.home).setChecked(true); // [cite: 221]
                        break; // [cite: 222]
                    case 2:
                        mBottomNavigationView.getMenu().findItem(R.id.settings).setChecked(true); // [cite: 224]
                        break; // [cite: 226]
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        // 4. Sự kiện khi bấm vào BottomNavigation -> Chuyển trang ViewPager tương ứng
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() { // [cite: 230, 231]
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.person) {
                    mViewPager.setCurrentItem(0);
                } else if (itemId == R.id.home) {
                    mViewPager.setCurrentItem(1);
                } else if (itemId == R.id.settings) {
                    mViewPager.setCurrentItem(2);
                }
                return true;
            }
        });
    }
}