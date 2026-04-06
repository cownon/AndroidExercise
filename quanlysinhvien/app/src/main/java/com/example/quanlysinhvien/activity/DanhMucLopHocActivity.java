package com.example.quanlysinhvien.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien.R;
import com.example.quanlysinhvien.adapter.LopHocAdapter;
import com.example.quanlysinhvien.model.LopHoc;
import com.example.quanlysinhvien.sqlite.LopHocDAO;

import java.util.List;

public class DanhMucLopHocActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtTenLopHoc;
    private ListView lvDanhSachLopHoc;
    private List<LopHoc> lopHocList;
    private LopHocAdapter lopHocAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc_lop_hoc);

        findViewById(R.id.btnLuuLopHoc).setOnClickListener(this);
        findViewById(R.id.btnThoatLopHoc).setOnClickListener(this);

        edtTenLopHoc = findViewById(R.id.edtTenLopHoc);
        lvDanhSachLopHoc = findViewById(R.id.lvdanhsachlophoc);

        fillLopHocListView(); // Tải danh sách lớp học [cite: 785]

        // Sự kiện nhấn giữ để xóa lớp học [cite: 796-812]
        lvDanhSachLopHoc.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                LopHocDAO lopHocDAO = new LopHocDAO(DanhMucLopHocActivity.this);
                LopHoc lopHoc = lopHocList.get(i);
                lopHocDAO.delete(lopHoc.getId());
                fillLopHocListView(); // Làm mới danh sách
                Toast.makeText(DanhMucLopHocActivity.this, "Đã xóa lớp học", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void fillLopHocListView() {
        LopHocDAO lopHocDAO = new LopHocDAO(this);
        lopHocList = lopHocDAO.getAll(); // Lấy tất cả lớp học [cite: 790]
        lopHocAdapter = new LopHocAdapter(this, lopHocList);
        lvDanhSachLopHoc.setAdapter(lopHocAdapter); // Cập nhật adapter [cite: 795]
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnLuuLopHoc) {
            LopHoc lopHoc = new LopHoc();
            lopHoc.setTenlophoc(edtTenLopHoc.getText().toString()); // Lấy tên lớp [cite: 827]

            LopHocDAO lopHocDAO = new LopHocDAO(this);
            lopHocDAO.insert(lopHoc); // Lưu vào DB [cite: 829]

            fillLopHocListView();
            edtTenLopHoc.setText(""); // Xóa dữ liệu vừa nhập
            Toast.makeText(this, "Đã lưu lớp học", Toast.LENGTH_LONG).show();
        } else if (id == R.id.btnThoatLopHoc) {
            finish();
        }
    }
}