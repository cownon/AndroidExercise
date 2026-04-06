package com.example.demoimageview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageViewActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    ImageView imageView2;

    private int[] listButtonID = {
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        init();
    }

    public void init() {

        textView = findViewById(R.id.textView);
        imageView2 = findViewById(R.id.imageView2);

        imageView2.setImageResource(R.drawable.vitdonnald);

        for (int id : listButtonID) {
            Button btnTemp = findViewById(id);
            btnTemp.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.button3) {
            imageView2.setScaleType(ImageView.ScaleType.CENTER);
            textView.setText("Center Style");

        } else if (id == R.id.button4) {
            imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
            textView.setText("Center Crop Style");

        } else if (id == R.id.button5) {
            imageView2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            textView.setText("Center Inside Style");

        } else if (id == R.id.button6) {
            imageView2.setScaleType(ImageView.ScaleType.FIT_CENTER);
            textView.setText("Fit Center Style");

        } else if (id == R.id.button7) {
            imageView2.setScaleType(ImageView.ScaleType.FIT_END);
            textView.setText("Fit End Style");

        } else if (id == R.id.button8) {
            imageView2.setScaleType(ImageView.ScaleType.FIT_START);
            textView.setText("Fit Start Style");

        } else if (id == R.id.button9) {
            imageView2.setScaleType(ImageView.ScaleType.FIT_XY);
            textView.setText("Fit XY Style");
        }
    }
}