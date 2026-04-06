package com.example.quanlysinhvien.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quanlysinhvien.R;
import com.example.quanlysinhvien.model.LopHoc;

import java.util.List;

public class LopHocAdapter extends BaseAdapter {
    private Context context;
    private List<LopHoc> lopHocList;

    public LopHocAdapter(Context context, List<LopHoc> lopHocList) {
        this.context = context;
        this.lopHocList = lopHocList;
    }

    @Override
    public int getCount() {
        return lopHocList.size();
    }

    @Override
    public Object getItem(int i) {
        return lopHocList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_danh_muc_lop_hoc_items, null);
        }
        TextView txtIdLopHoc = view.findViewById(R.id.txtIdLopHoc);
        TextView txtTenLopHoc = view.findViewById(R.id.txtTenLopHoc);

        LopHoc lopHoc = lopHocList.get(i);
        txtIdLopHoc.setText(String.valueOf(lopHoc.getId()));
        txtTenLopHoc.setText(lopHoc.getTenlophoc());

        return view;
    }
}