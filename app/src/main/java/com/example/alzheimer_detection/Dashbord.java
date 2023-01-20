package com.example.alzheimer_detection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.checkerframework.checker.nullness.compatqual.NonNullType;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;

public class Dashbord extends AppCompatActivity {

    ImageView stat1;
    BottomNavigationView bottomNavigationView;
    ProgressBar progressBar;
    TextView time;
    TextView date;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String currentdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);
        stat1 = findViewById(R.id.StatImage1);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        return true;
                    case R.id.Test:
                        startActivity(new Intent(getApplicationContext(),QuizHome.class));
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
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        calendar = Calendar.getInstance();

        dateFormat = new SimpleDateFormat("EEE, MMM d,yy");
        currentdate = dateFormat.format(calendar.getTime());
        date.setText(currentdate);

        dateFormat = new SimpleDateFormat("h:mm a");
        currentdate = dateFormat.format(calendar.getTime());
        time.setText(currentdate);
        /*
        if (!Python.isStarted())
            Python.start(new AndroidPlatform(this));
        final Python py = Python.getInstance();

        PyObject pyo = py.getModule("main");
        PyObject obj = pyo.callAttr("main");

        try
        {
            Bitmap bmp = BitmapFactory.decodeByteArray(java.util.Base64.getDecoder().decode(obj.toString()), 0, java.util.Base64.getDecoder().decode(obj.toString()).length);
            stat1.setImageBitmap(bmp);

        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        // lets create script first..
        /*String str = obj.toString();
        Log.d("Activity", str);
        byte data[] = java.util.Base64.getDecoder().decode(str);

                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length); // convert to bitmap

                //now set it to image view
                stat1.setImageBitmap(bmp);
                stat1.setCropToPadding(false);
                stat1.setAdjustViewBounds(true);*/
        OkHttpClient okhttpclient = new OkHttpClient();
        Request request = new Request.Builder().url("http://192.168.1.12:5000/graph").build();
        okhttpclient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(Dashbord.this,"Ooops! check connection please!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        stat1.setImageBitmap(bitmap);
                    }
                });
            }
        });
            }



    }
