package com.example.alzheimer_detection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import org.tensorflow.lite.DataType;
import org.tensorflow.lite.schema.Model;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class IRMPrediction extends AppCompatActivity {

    Button SubmitIRM;
    TextView IRMRESULT;
    ImageView IRMPicture;
    int imagesize = 224;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irmprediction);

        IRMRESULT = findViewById(R.id.IRMRESULT);
        IRMPicture = findViewById(R.id.IRMPicture);
        SubmitIRM = findViewById(R.id.SubmitIRM);

        SubmitIRM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery,1);
            }
        });
    }

    public void classifyImage(Bitmap image) {
        /*try {
            Model model = Model.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
            tensorImage.load(image);
            ByteBuffer byteBuffer = tensorImage.getBuffer();
            inputFeature0.loadBuffer(byteBuffer);


            // Runs model inference and gets result.
            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
            float[] confidences = outputFeature0.getFloatArray();

            int maxPos = 0;
            float maxC=0;
            System.out.println(outputFeature0.getFloatArray().length);
            for (int i=0; i<outputFeature0.getFloatArray().length; i++){
                System.out.println(outputFeature0.getFloatArray()[i]);
                if (maxC < outputFeature0.getFloatArray()[i]){
                    maxC = outputFeature0.getFloatArray()[i];
                    maxPos = i;
                }
            }
            String[] classes = {"Mild Demented", "Moderate Demented", "Non Demented", "Very Mild Demented"};
            IRMRESULT.setText("Result: "+classes[maxPos] );
            model.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Uri dat = data.getData();
            Bitmap image = null;
            try {
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
            } catch (IOException e) {
                e.printStackTrace();
            }
            IRMPicture.setImageBitmap(image);

            image = Bitmap.createScaledBitmap(image, imagesize, imagesize, false);
            classifyImage(image);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}