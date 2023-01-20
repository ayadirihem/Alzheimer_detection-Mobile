package com.example.alzheimer_detection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InfoActivity extends AppCompatActivity {

    ImageButton Police, Samu, Protection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        VideoView videoView =(VideoView)findViewById(R.id.video);

        //Creating MediaController
        String videoPath = "android.resource://" + this.getPackageName() + "/" + R.raw.video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        MediaController mediaController;
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.requestFocus();
        videoView.start();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.Info);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),Dashbord.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Test:
                        startActivity(new Intent(getApplicationContext(), QuizHome.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Info:
                        return true;
                    case R.id.Predict:
                        startActivity(new Intent(getApplicationContext(),PredictList.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        Police = findViewById(R.id.Police);
        Samu = findViewById(R.id.SAMU);
        Protection = findViewById(R.id.Protection);
        Police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String POLICENUM = "tel:197";
                Intent myActivity2 = new Intent(Intent.ACTION_DIAL, Uri.parse(POLICENUM));
                startActivity(myActivity2);
            }
        });
        Samu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SAMUNUM = "tel:190";
                Intent myActivity2 = new Intent(Intent.ACTION_DIAL, Uri.parse(SAMUNUM));
                startActivity(myActivity2);
            }
        });
        Protection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String PROTECTIONNUM = "tel:190";
                Intent myActivity2 = new Intent(Intent.ACTION_DIAL, Uri.parse(PROTECTIONNUM));
                startActivity(myActivity2);
            }
        });
    }
}