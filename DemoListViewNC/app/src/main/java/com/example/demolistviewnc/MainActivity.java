package com.example.demolistviewnc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvCity;
    ArrayList<City> cityArrayList;
    CityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCity = findViewById(R.id.lvCity);

        cityArrayList = new ArrayList<>();

        cityArrayList.add(new City("New York", R.drawable.newyork, "http://1.com"));
        cityArrayList.add(new City("Paris", R.drawable.paris, "http://2.com"));
        cityArrayList.add(new City("Rome", R.drawable.rome, "http://3.com"));

        adapter = new CityAdapter(this, R.layout.dong_thanh_pho, cityArrayList);

        lvCity.setAdapter(adapter);

        lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                City city = cityArrayList.get(position);

                Toast.makeText(MainActivity.this,
                        city.getNameCity(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}