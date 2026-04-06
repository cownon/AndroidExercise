package com.example.quanlysinhvien.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quanlysinhvien.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Đăng ký sự kiện click cho các nút [cite: 632]
        findViewById(R.id.btnDanhMucLopHoc).setOnClickListener(this);
        findViewById(R.id.btnQuanLySinhVien).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnDanhMucLopHoc) {
            Intent intent = new Intent(MainActivity.this, DanhMucLopHocActivity.class);
            startActivity(intent); // Mở màn hình Lớp học [cite: 638]
        } else if (id == R.id.btnQuanLySinhVien) {
            Intent intent1 = new Intent(MainActivity.this, QuanLySinhVienActivity.class);
            startActivity(intent1); // Mở màn hình Sinh viên [cite: 641]
        }
    }
}