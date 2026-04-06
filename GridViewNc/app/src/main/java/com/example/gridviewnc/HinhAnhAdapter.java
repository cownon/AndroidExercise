package com.example.gridviewnc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.gridviewnc.model.HinhAnh;

import java.util.List;

public class HinhAnhAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<HinhAnh> hinhAnhList;

    public HinhAnhAdapter(int layout, Context context, List<HinhAnh> hinhAnhList) {
        this.layout = layout;
        this.context = context;
        this.hinhAnhList = hinhAnhList;
    }


    @Override
    public int getCount() {
        return hinhAnhList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
          Context.LAYOUT_INFLATER_SERVICE
        );
        convertView = inflater.inflate(layout, parent, false);
        ImageView imageView = convertView.findViewById(R.id.imageView2);
        HinhAnh hinhAnh = hinhAnhList.get(position);
        imageView.setImageResource(hinhAnh.getHinh());
        return convertView;
    }
}
