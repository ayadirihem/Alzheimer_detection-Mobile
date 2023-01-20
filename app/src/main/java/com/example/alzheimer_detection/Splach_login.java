package com.example.alzheimer_detection;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class Splach_login extends AppCompatActivity {

    Handler handler;
    Runnable runnable;
    ImageView img;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach_login);
        img = findViewById(R.id.img);
        img.animate().alpha(1000).setDuration(0);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent dap = new Intent(Splach_login.this, LoginActivity.class);
                startActivity(dap);
                finish();
            }
        },1000);
    }
}