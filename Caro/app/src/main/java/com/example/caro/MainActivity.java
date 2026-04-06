package com.example.caro;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int Winer = -1;
    int startGame = 0;
    Button btPlayAgain, bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9;
    TextView txtShowresult;

    int ActivePlayer = 1; // 1: Player 1 (X), 2: Player 2 (O)
    ArrayList<Integer> Player1 = new ArrayList<>();
    ArrayList<Integer> Player2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa(); // Ánh xạ các thành phần giao diện

        btPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startGame == 1) {
                    PlayAgain();
                    startGame = 0;
                    btPlayAgain.setText("Bắt đầu");
                } else if (startGame == 0) {
                    btPlayAgain.setText("Chơi lại");
                    startGame = 1;
                }
            }
        });
    }

    void AnhXa() {
        btPlayAgain = findViewById(R.id.btPlayAgain);
        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        bt3 = findViewById(R.id.bt3);
        bt4 = findViewById(R.id.bt4);
        bt5 = findViewById(R.id.bt5);
        bt6 = findViewById(R.id.bt6);
        bt7 = findViewById(R.id.bt7);
        bt8 = findViewById(R.id.bt8);
        bt9 = findViewById(R.id.bt9);
        txtShowresult = findViewById(R.id.txtShowresult);
    }

    public void btClick(View view) {
        Button btSelected = (Button) view;
        int CellID = 0;

        // Xác định ID của ô được bấm
        if (btSelected.getId() == R.id.bt1) CellID = 1;
        else if (btSelected.getId() == R.id.bt2) CellID = 2;
        else if (btSelected.getId() == R.id.bt3) CellID = 3;
        else if (btSelected.getId() == R.id.bt4) CellID = 4;
        else if (btSelected.getId() == R.id.bt5) CellID = 5;
        else if (btSelected.getId() == R.id.bt6) CellID = 6;
        else if (btSelected.getId() == R.id.bt7) CellID = 7;
        else if (btSelected.getId() == R.id.bt8) CellID = 8;
        else if (btSelected.getId() == R.id.bt9) CellID = 9;

        // Chỉ cho phép đánh khi game đã bắt đầu, chưa có người thắng và ô đó chưa ai đánh
        if (Winer == -1 && startGame == 1 && !Player1.contains(CellID) && !Player2.contains(CellID)) {
            PlayGame(CellID, btSelected);
        }
    }

    void PlayGame(int CellID, Button btselected) {
        if (ActivePlayer == 1) {
            btselected.setText("X");
            btselected.setBackgroundColor(Color.GREEN);
            btselected.setTextColor(Color.RED);
            Player1.add(CellID);
            ActivePlayer = 2;
        } else if (ActivePlayer == 2) {
            btselected.setText("O"); // Dùng chữ O thay vì số 0
            btselected.setTextColor(Color.WHITE);
            btselected.setBackgroundColor(Color.BLUE);
            Player2.add(CellID);
            ActivePlayer = 1;
        }

        CheckWiner();

        if (Winer == 1) {
            txtShowresult.setVisibility(View.VISIBLE);
            txtShowresult.setText("Player 1 thắng !");
        } else if (Winer == 2) {
            txtShowresult.setVisibility(View.VISIBLE);
            txtShowresult.setText("Player 2 thắng !");
        } else if (Winer == 0) {
            txtShowresult.setVisibility(View.VISIBLE);
            txtShowresult.setText("Hòa!");
        }
    }

    void CheckWiner() {
        // --- Dòng ngang ---
        // Dòng 1
        if (Player1.contains(1) && Player1.contains(2) && Player1.contains(3)) Winer = 1;
        if (Player2.contains(1) && Player2.contains(2) && Player2.contains(3)) Winer = 2;
        // Dòng 2
        if (Player1.contains(4) && Player1.contains(5) && Player1.contains(6)) Winer = 1;
        if (Player2.contains(4) && Player2.contains(5) && Player2.contains(6)) Winer = 2;
        // Dòng 3
        if (Player1.contains(7) && Player1.contains(8) && Player1.contains(9)) Winer = 1;
        if (Player2.contains(7) && Player2.contains(8) && Player2.contains(9)) Winer = 2;

        // --- Cột dọc ---
        // Cột 1
        if (Player1.contains(1) && Player1.contains(4) && Player1.contains(7)) Winer = 1;
        if (Player2.contains(1) && Player2.contains(4) && Player2.contains(7)) Winer = 2;
        // Cột 2
        if (Player1.contains(2) && Player1.contains(5) && Player1.contains(8)) Winer = 1;
        if (Player2.contains(2) && Player2.contains(5) && Player2.contains(8)) Winer = 2;
        // Cột 3 (Đã sửa lỗi logic từ tài liệu gốc)
        if (Player1.contains(3) && Player1.contains(6) && Player1.contains(9)) Winer = 1;
        if (Player2.contains(3) && Player2.contains(6) && Player2.contains(9)) Winer = 2;

        // --- Đường chéo ---
        // Chéo 1
        if (Player1.contains(1) && Player1.contains(5) && Player1.contains(9)) Winer = 1;
        if (Player2.contains(1) && Player2.contains(5) && Player2.contains(9)) Winer = 2;
        // Chéo 2
        if (Player1.contains(3) && Player1.contains(5) && Player1.contains(7)) Winer = 1;
        if (Player2.contains(3) && Player2.contains(5) && Player2.contains(7)) Winer = 2;

        // Kiểm tra hòa (Tất cả 9 ô đều có người đánh nhưng chưa có Winer)
        int sum = Player1.size() + Player2.size();
        if (sum == 9 && Winer == -1) {
            Winer = 0;
        }
    }

    void PlayAgain() {
        Player1.clear();
        Player2.clear();
        Winer = -1;
        ActivePlayer = 1; // Reset lại người chơi 1 đi trước

        // Reset lại giao diện các ô cờ
        Button[] buttons = {bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8, bt9};
        for (Button btn : buttons) {
            btn.setText("");
            btn.setBackgroundColor(Color.rgb(188, 185, 185)); // Màu nền xám ban đầu
        }

        txtShowresult.setVisibility(View.INVISIBLE);
    }
}