package com.example.alzheimer_detection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class QuizHome extends AppCompatActivity {

    private String selectedTopicName = "";
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_home);
        final LinearLayout java = findViewById(R.id.layoutJ);
        final TextView startBtn = findViewById(R.id.startQuizBtn);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.Test);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),Dashbord.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Test:
                        return true;
                    case R.id.Info:
                        startActivity(new Intent(getApplicationContext(), InfoActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Predict:
                        startActivity(new Intent(getApplicationContext(),PredictList.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        java.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedTopicName = "java";

                java.setBackgroundResource(R.drawable.round_back_white_stroke10);


            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(selectedTopicName.isEmpty()){
                    Toast.makeText(MainActivity.this,"Please select the option", Toast.LENGTH_SHORT).show();
                } else {*/
                Intent intent = new Intent(QuizHome.this, quiz.class);
                intent.putExtra("selectedTopic",selectedTopicName);
                startActivity(intent);
                //}
            }
        });
    }

}