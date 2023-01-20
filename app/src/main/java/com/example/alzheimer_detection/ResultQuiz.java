package com.example.alzheimer_detection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ResultQuiz extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_quiz);
        final AppCompatButton startNewBtn = findViewById(R.id.startNewQuiz);
        final TextView correctAnswer = findViewById(R.id.correctAnswers);
        final TextView incorrectAnswer = findViewById(R.id.incorrectAnswers);
        final TextView score = findViewById(R.id.score);
        final TextView res = findViewById(R.id.res);

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
                        startActivity(new Intent(getApplicationContext(),quiz.class));
                        overridePendingTransition(0,0);
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


        final int getCorrectAnswer = getIntent().getIntExtra("correct",0);
        final int getIncorrectAnswer = getIntent().getIntExtra("incorrect",0);

        correctAnswer.setText(String.valueOf(getCorrectAnswer));
        incorrectAnswer.setText(String.valueOf(getIncorrectAnswer));

        if(getCorrectAnswer!= 0){
            final int calculateScore = getCorrectAnswer/getCorrectAnswer+getIncorrectAnswer;
            score.setText(String.valueOf("Score : "+ calculateScore));

        }
        if(getCorrectAnswer < 5){
            res.setText("You have alzheimer");
        }else {
            res.setText("Good mental health");
        }

        startNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResultQuiz.this, Dashbord.class));
                finish();
            }
        });

    }
}