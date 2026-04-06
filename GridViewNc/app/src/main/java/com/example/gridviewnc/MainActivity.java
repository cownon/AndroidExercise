package com.example.gridviewnc;

import android.os.Bundle;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gridviewnc.model.HinhAnh;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gvTen;
    HinhAnhAdapter adapter;
    ArrayList<HinhAnh> arrayHinhAnh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gvTen = findViewById(R.id.gvTen);

        arrayHinhAnh = new ArrayList<>();
        arrayHinhAnh.add(new HinhAnh(R.drawable.img, "Hinh 1"));
        arrayHinhAnh.add(new HinhAnh(R.drawable.img_1, "Hinh 2"));
        arrayHinhAnh.add(new HinhAnh(R.drawable.img_2, "Hinh 3"));
        arrayHinhAnh.add(new HinhAnh(R.drawable.img_3, "Hinh 4"));
        arrayHinhAnh.add(new HinhAnh(R.drawable.img_4, "Hinh 5"));
        arrayHinhAnh.add(new HinhAnh(R.drawable.img_5, "Hinh 6"));
        arrayHinhAnh.add(new HinhAnh(R.drawable.img_6, "Hinh 7"));
        arrayHinhAnh.add(new HinhAnh(R.drawable.img_7, "Hinh 8"));
        arrayHinhAnh.add(new HinhAnh(R.drawable.img_8, "Hinh 9"));

        adapter = new HinhAnhAdapter(R.layout.dong_hinh_anh, this, arrayHinhAnh);
        gvTen.setAdapter(adapter);
    }
}