package com.example.demonetworking;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.demonetworking.adapters.WeatherAdapter;
import com.example.demonetworking.models.Weather;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editSearch;
    ImageView imgSearch, imgSeasonForecast;
    TextView txtCityName, txtTemperature, txtCityTemperature;
    ListView lvWeather;

    ArrayList<Weather> weatherArrayList;
    WeatherAdapter weatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ View
        editSearch = findViewById(R.id.editTextTextPersonName);
        imgSearch = findViewById(R.id.imgSearch);
        imgSeasonForecast = findViewById(R.id.imgSeasonForecast);
        txtCityName = findViewById(R.id.txtCityName);
        txtTemperature = findViewById(R.id.txtTemperature);
        txtCityTemperature = findViewById(R.id.txtCityTemperature);
        lvWeather = findViewById(R.id.lvWeather);

        // Khởi tạo List và Adapter
        weatherArrayList = new ArrayList<>();
        weatherAdapter = new WeatherAdapter(this, weatherArrayList);
        lvWeather.setAdapter(weatherAdapter);

        // Lấy thời tiết mặc định khi vừa mở app
        getWeatherData("Ho Chi Minh");

        // Bắt sự kiện khi click vào nút tìm kiếm
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = editSearch.getText().toString().trim();
                if (city.isEmpty()) {
                    // Lệnh Toast PHẢI nằm ở đây
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên thành phố", Toast.LENGTH_SHORT).show();
                } else {
                    getWeatherData(city);
                }
            }
        });
    }

    private void getWeatherData(String cityName) {
        // Chuỗi API Key của bạn
        String url = "https://api.weatherapi.com/v1/forecast.json?key=fc8f4188ad3f48d8a10132707221212&q=" + cityName + "&days=1&aqi=no&alerts=no";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            weatherArrayList.clear(); // Xóa dữ liệu cũ khi tìm thành phố mới

                            // 1. Lấy thông tin chung (Tên thành phố)
                            JSONObject locationObj = response.getJSONObject("location");
                            String name = locationObj.getString("name");
                            txtCityName.setText(name);

                            // 2. Lấy thông tin thời tiết hiện tại (Current)
                            JSONObject currentObj = response.getJSONObject("current");
                            String temp = currentObj.getString("temp_c");
                            txtTemperature.setText(temp + "°C");

                            JSONObject conditionObj = currentObj.getJSONObject("condition");
                            String conditionText = conditionObj.getString("text");
                            String conditionIcon = conditionObj.getString("icon");
                            txtCityTemperature.setText(conditionText);

                            // Load ảnh hiện tại
                            Picasso.get().load("https:" + conditionIcon).into(imgSeasonForecast);

                            // 3. Lấy dự báo theo giờ (Forecast)
                            JSONObject forecastObj = response.getJSONObject("forecast");
                            JSONArray forecastDayArray = forecastObj.getJSONArray("forecastday");
                            JSONObject firstDayObj = forecastDayArray.getJSONObject(0);
                            JSONArray hourArray = firstDayObj.getJSONArray("hour");

                            for (int i = 0; i < hourArray.length(); i++) {
                                JSONObject hourObj = hourArray.getJSONObject(i);
                                String time = hourObj.getString("time");
                                String hourTemp = hourObj.getString("temp_c");
                                String wind = hourObj.getString("wind_kph");

                                JSONObject hourCondition = hourObj.getJSONObject("condition");
                                String hourIcon = hourCondition.getString("icon");

                                // Thêm vào danh sách
                                weatherArrayList.add(new Weather(time, hourTemp, wind, hourIcon));
                            }

                            // Cập nhật lại ListView
                            weatherAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Lỗi đọc dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Không tìm thấy thành phố hoặc lỗi mạng!", Toast.LENGTH_SHORT).show();
                        Log.e("Volley Error", error.toString());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }
}