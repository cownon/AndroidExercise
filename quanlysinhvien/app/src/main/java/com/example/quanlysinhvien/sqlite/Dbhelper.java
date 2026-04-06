package com.example.quanlysinhvien.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class Dbhelper extends SQLiteOpenHelper {
    private static final String DB_Name = "QLSinhvien";
    private static final int DB_Version = 2;

    public Dbhelper(@Nullable Context context) {
        super(context, DB_Name, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Bảng Lớp Học
        String lophocSQL = "CREATE TABLE lophocs (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenlop TEXT NOT NULL)";

        // Bảng Sinh Viên (có khóa ngoại lophocid tham chiếu tới id của lophocs)
        String sinhvienSQL = "CREATE TABLE sinhviens (id TEXT PRIMARY KEY, " +
                "hoten TEXT NOT NULL, ngaysinh TEXT, lophocid INTEGER, " +
                "FOREIGN KEY (lophocid) REFERENCES lophocs(id))";

        sqLiteDatabase.execSQL(lophocSQL);
        sqLiteDatabase.execSQL(sinhvienSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String dropSinhVien = "DROP TABLE IF EXISTS sinhviens";
        String dropLopHoc = "DROP TABLE IF EXISTS lophocs";

        sqLiteDatabase.execSQL(dropSinhVien);
        sqLiteDatabase.execSQL(dropLopHoc);
        onCreate(sqLiteDatabase);
    }
}