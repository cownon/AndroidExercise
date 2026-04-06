package com.example.demolistview;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Khai báo biến
    ArrayList<String> arrayMonhoc;
    ArrayAdapter<String> adapter; // Định nghĩa rõ kiểu <String> cho Adapter
    ListView lsView;
    Button btnThem, btnCapnhat, btnXoa;
    EditText editText;
    int pos = -1; // Đặt giá trị mặc định là -1 (chưa chọn gì cả)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Xử lý khoảng cách thanh trạng thái (Edge To Edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các thành phần giao diện
        lsView = findViewById(R.id.lsView);
        btnThem = findViewById(R.id.btnThem);
        btnCapnhat = findViewById(R.id.btnCapnhat);
        editText = findViewById(R.id.edText);

        // Khởi tạo và thêm dữ liệu vào mảng
        arrayMonhoc = new ArrayList<>();
        arrayMonhoc.add("Android");
        arrayMonhoc.add("Java");
        arrayMonhoc.add("PHP");
        arrayMonhoc.add("Hadoop");
        arrayMonhoc.add("Sap");
        arrayMonhoc.add("Python");
        arrayMonhoc.add("Ajax");
        arrayMonhoc.add("C++");
        arrayMonhoc.add("Ruby");
        arrayMonhoc.add("Rails");

        // Khởi tạo Adapter và gán cho ListView
        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arrayMonhoc);
        lsView.setAdapter(adapter);

        // 1. Nút Thêm
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String monHocMoi = editText.getText().toString().trim();
                // Kiểm tra xem ô nhập có trống không
                if (!monHocMoi.isEmpty()) {
                    arrayMonhoc.add(monHocMoi);
                    adapter.notifyDataSetChanged();
                    editText.setText(""); // Xóa rỗng ô nhập sau khi thêm
                } else {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên môn học!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 2. Nút Cập nhật
        btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String monHocSua = editText.getText().toString().trim();
                // Kiểm tra xem đã chọn phần tử chưa (pos != -1) và ô nhập không trống
                if (pos != -1 && !monHocSua.isEmpty()) {
                    arrayMonhoc.set(pos, monHocSua);
                    adapter.notifyDataSetChanged();
                    pos = -1; // Reset lại vị trí sau khi cập nhật xong
                    editText.setText("");
                    Toast.makeText(MainActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                } else if (pos == -1) {
                    Toast.makeText(MainActivity.this, "Hãy chọn một môn học dưới danh sách để cập nhật", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 3. Click ngắn vào phần tử trong ListView -> Đẩy lên EditText
        lsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editText.setText(arrayMonhoc.get(i));
                pos = i;
            }
        });

        // 4. Click giữ lâu vào phần tử -> Xóa khỏi danh sách
        lsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "Đã xóa: " + arrayMonhoc.get(i), Toast.LENGTH_LONG).show();
                arrayMonhoc.remove(i);
                adapter.notifyDataSetChanged();

                if (pos == i) {
                    editText.setText("");
                    pos = -1;
                }

                return true;
            }
        });
    }
}