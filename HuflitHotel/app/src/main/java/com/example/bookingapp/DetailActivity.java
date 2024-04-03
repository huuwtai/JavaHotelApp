package com.example.bookingapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    private ImageView imbBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imbackclick();
    }
    void imbackclick(){
        imbBack = findViewById(R.id.Detail_button_back);
        imbBack.setOnClickListener(v->finish());
    }
}