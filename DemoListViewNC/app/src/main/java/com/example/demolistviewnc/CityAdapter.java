package com.example.demolistviewnc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CityAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<City> cityList;

    public CityAdapter(Context context, int layout, List<City> cityList) {
        this.context = context;
        this.layout = layout;
        this.cityList = cityList;
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(layout, null);

        TextView txtTen = convertView.findViewById(R.id.txtTen);
        TextView txtLink = convertView.findViewById(R.id.txtlink);
        ImageView imgHinh = convertView.findViewById(R.id.imgHinh);

        City city = cityList.get(position);

        txtTen.setText(city.getNameCity());
        txtLink.setText(city.getLinkWiki());
        imgHinh.setImageResource(city.getHinh());

        return convertView;
    }
}