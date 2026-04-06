package com.example.quanlysinhvien.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quanlysinhvien.R;
import com.example.quanlysinhvien.helper.DateTimeHelper;
import com.example.quanlysinhvien.model.SinhVien;

import java.util.List;

public class SinhVienAdapter extends BaseAdapter {
    private Context context;
    private List<SinhVien> sinhVienList;

    public SinhVienAdapter(Context context, List<SinhVien> sinhVienList) {
        this.context = context;
        this.sinhVienList = sinhVienList;
    }

    @Override
    public int getCount() {
        return sinhVienList.size();
    }

    @Override
    public Object getItem(int i) {
        return sinhVienList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_sinh_vien_items, null);
        }
        TextView tvID = view.findViewById(R.id.tvID);
        TextView tvHoten = view.findViewById(R.id.tvHoten);
        TextView tvNgaysinh = view.findViewById(R.id.tvNgaysinh);

        SinhVien sinhVien = sinhVienList.get(i);
        tvID.setText(sinhVien.getId());
        tvHoten.setText(sinhVien.getHoten());
        tvNgaysinh.setText(DateTimeHelper.toString(sinhVien.getNgaysinh()));

        return view;
    }
}