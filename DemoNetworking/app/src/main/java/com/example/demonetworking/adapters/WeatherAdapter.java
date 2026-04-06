package com.example.demonetworking.adapters; // Thay bằng tên package của bạn

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demonetworking.R;
import com.example.demonetworking.models.Weather;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WeatherAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Weather> weatherList;

    public WeatherAdapter(Context context, ArrayList<Weather> weatherList) {
        this.context = context;
        this.weatherList = weatherList;
    }

    @Override
    public int getCount() {
        return weatherList.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_weather_items, null);
        }

        Weather weather = weatherList.get(position);

        TextView txtTime = convertView.findViewById(R.id.txtTimeForecast);
        TextView txtTemp = convertView.findViewById(R.id.txtTemperatureForecast);
        TextView txtWind = convertView.findViewById(R.id.txtWindForecast);
        ImageView imgIcon = convertView.findViewById(R.id.imgForecast);

        // API trả về định dạng "2025-04-08 00:00". Ta thay dấu cách bằng xuống dòng để giống y hệt thiết kế
        txtTime.setText(weather.getTime().replace(" ", "\n"));
        txtTemp.setText(weather.getTemp() + "°C");
        txtWind.setText(weather.getWind() + " km/h"); // Hoặc "km" theo thiết kế của bạn

        // URL ảnh của WeatherAPI thường bắt đầu bằng "//cdn.weatherapi...". Cần thêm "https:" để Picasso đọc được
        String fullIconUrl = "https:" + weather.getIconUrl();
        Picasso.get().load(fullIconUrl).into(imgIcon);

        return convertView;
    }
}