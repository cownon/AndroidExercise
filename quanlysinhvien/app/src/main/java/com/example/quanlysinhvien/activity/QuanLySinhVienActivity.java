package com.example.quanlysinhvien.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien.R;
import com.example.quanlysinhvien.adapter.LopHocAdapter;
import com.example.quanlysinhvien.adapter.SinhVienAdapter;
import com.example.quanlysinhvien.helper.DateTimeHelper;
import com.example.quanlysinhvien.model.LopHoc;
import com.example.quanlysinhvien.model.SinhVien;
import com.example.quanlysinhvien.sqlite.LopHocDAO;
import com.example.quanlysinhvien.sqlite.SinhVienDAO;

import java.util.List;

public class QuanLySinhVienActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtMaSV, edtHotenSV, edtNgaySinhSV;
    private ListView lvDanhsachSinhvien;
    private Spinner spLopHoc;

    private SinhVienAdapter sinhVienAdapter;
    private List<LopHoc> lopHocList;
    private List<SinhVien> sinhVienList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_sinhvien);

        findViewById(R.id.btnLuuSinhVien).setOnClickListener(this);
        findViewById(R.id.btnThoatSinhVien).setOnClickListener(this);

        edtMaSV = findViewById(R.id.edtMaSV);
        edtHotenSV = findViewById(R.id.edtHotenSV);
        edtNgaySinhSV = findViewById(R.id.edtNgaySinhSV);
        spLopHoc = findViewById(R.id.spLopHoc);
        lvDanhsachSinhvien = findViewById(R.id.lvDanhsachSinhvien);

        fillLopHocToSpinner(); // Tải lớp học vào Spinner [cite: 1029]

        // Cập nhật lại ListView sinh viên khi người dùng đổi lớp học trên Spinner [cite: 1048-1055]
        spLopHoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fillLopHocToListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void fillLopHocToSpinner() {
        LopHocDAO lopHocDAO = new LopHocDAO(this);
        lopHocList = lopHocDAO.getAll();
        LopHocAdapter lopHocAdapter = new LopHocAdapter(this, lopHocList);
        spLopHoc.setAdapter(lopHocAdapter);
    }

    private void fillLopHocToListView() {
        SinhVienDAO sinhVienDAO = new SinhVienDAO(this);
        try {
            // Lấy ID lớp học đang được chọn ở Spinner [cite: 1059]
            int lopHocid = lopHocList.get(spLopHoc.getSelectedItemPosition()).getId();
            sinhVienList = sinhVienDAO.getAllByLophoc(lopHocid); // Tìm Sinh viên theo lớp [cite: 1060]
            sinhVienAdapter = new SinhVienAdapter(this, sinhVienList);
            lvDanhsachSinhvien.setAdapter(sinhVienAdapter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        SinhVienDAO sinhVienDAO = new SinhVienDAO(this);
        int id = view.getId();

        if (id == R.id.btnLuuSinhVien) {
            try {
                SinhVien sinhVien = new SinhVien();
                sinhVien.setId(edtMaSV.getText().toString());
                sinhVien.setHoten(edtHotenSV.getText().toString());

                // Parse chuỗi ngày sinh thành Date [cite: 1068]
                sinhVien.setNgaysinh(DateTimeHelper.toDate(edtNgaySinhSV.getText().toString()));

                // Lấy ID lớp học để gán khóa ngoại [cite: 1068]
                int lopHoc = spLopHoc.getSelectedItemPosition();
                sinhVien.setLophocid(lopHocList.get(lopHoc).getId());

                sinhVienDAO.insert(sinhVien); // Thêm vào DB [cite: 1068]
                Toast.makeText(this, "Sinh viên đã được lưu", Toast.LENGTH_LONG).show();

                fillLopHocToListView(); // Cập nhật lại list [cite: 1068]

                // UX: Xóa trắng form sau khi lưu thành công
                edtMaSV.setText("");
                edtHotenSV.setText("");
                edtNgaySinhSV.setText("");

            } catch (Exception ex) {
                ex.printStackTrace();
                Toast.makeText(this, "Lỗi: Vui lòng nhập ngày theo định dạng dd/MM/yyyy", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.btnThoatSinhVien) {
            finish();
        }
    }
}