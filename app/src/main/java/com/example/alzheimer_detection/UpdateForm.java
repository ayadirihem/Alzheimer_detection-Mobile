package com.example.alzheimer_detection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateForm extends AppCompatActivity {

    String path = "https://alzheimerpredictionmodel.herokuapp.com/predict";
    EditText FullName, Age, EDUC, SES, MMSE, CDR, eTIV, nWBV, ASF;
    RadioButton Male,Female;
    Button Return;
    Button Predict;
    int gender;
    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;
    ProgressDialog loadingbar;
    String id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_form);
        FullName = findViewById(R.id.PatientNameUp);
        id = getIntent().getExtras().getString("id");
        Age = findViewById(R.id.AgePerson_Up);
        EDUC = findViewById(R.id.EDUCPatient_UP);
        SES = findViewById(R.id.SESPatient_UP);
        MMSE = findViewById(R.id.MMSEPatient_UP);
        CDR = findViewById(R.id.CDRPatient_UP);
        eTIV = findViewById(R.id.eTIVPatient_UP);
        nWBV = findViewById(R.id.nWBVPatient_UP);
        ASF = findViewById(R.id.ASFPatient_UP);
        Male = findViewById(R.id.radio_Male_Up);
        Female = findViewById(R.id.radio_Female_Up);
        Predict = findViewById(R.id.PredictBtnUpdate);
        Return = findViewById(R.id.ReturnHomeU);
        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();
        loadingbar = new ProgressDialog(this);
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("PatientSheet");

        FullName.setText(getIntent().getExtras().getString("FullName"));

        Age.setText(getIntent().getExtras().getString("Age"));
        EDUC.setText(String.valueOf(getIntent().getExtras().getFloat("EDUC")));
        SES.setText(String.valueOf(getIntent().getExtras().getFloat("SES")));
        MMSE.setText(String.valueOf(getIntent().getExtras().getFloat("MMSE")));
        CDR.setText(String.valueOf(getIntent().getExtras().getFloat("CDR")));
        eTIV.setText(String.valueOf(getIntent().getExtras().getFloat("eTIV")));
        nWBV.setText(String.valueOf(getIntent().getExtras().getFloat("nWBV")));
        ASF.setText(String.valueOf(getIntent().getExtras().getFloat("ASF")));
        if (getIntent().getExtras().getString("Gender").equals("F"))
            Female.setChecked(true);
        else
            Male.setChecked(true);
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateForm.this,PredictList.class);
                startActivity(intent);
            }
        });
        Predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingbar.setTitle("Prediction ...");
                loadingbar.setMessage("Please wait, while we are checking the credentials.");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();
                // hit the API -> Volley
                StringRequest stringRequest = new StringRequest(Request.Method.POST, path,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String data = jsonObject.getString("Result");
                                    //{"Demented": 1, "Nondemented": 0, "Converted": 2}
                                    String res = "", sex;
                                    System.out.println(data);
                                    if (data.trim().equals("0") == true)
                                        res = "Nondemented";
                                    else if (data.trim().equals("1") == true)
                                        res = "Demented";
                                    else
                                        res = "Converted";
                                    if (Female.isChecked())
                                        sex = "F";
                                    else
                                        sex = "M";

                                    UpdateDatatoFirebase(sex, res);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(UpdateForm.this, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map getParams(){
                        Map params = new HashMap();
                        if (Male.isChecked())
                            gender = 1;
                        else if (Female.isChecked())
                            gender = 0;
                        params.put("Gender", String.valueOf(gender));
                        params.put("Age", Age.getText().toString());
                        params.put("EDUC", EDUC.getText().toString());
                        params.put("SES", SES.getText().toString());
                        params.put("MMSE", MMSE.getText().toString());
                        params.put("CDR", CDR.getText().toString());
                        params.put("eTIV", eTIV.getText().toString());
                        params.put("nWBV", nWBV.getText().toString());
                        params.put("ASF", ASF.getText().toString());

                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(UpdateForm.this);
                queue.add(stringRequest);


            }
        });
    }


    private void UpdateDatatoFirebase(String sex, String res) {
        // below 3 lines of code is used to set
        // data in our object class.
        PatientSheet Patient = new PatientSheet(FullName.getText().toString(),
                Integer.parseInt(Age.getText().toString()), Float.parseFloat(EDUC.getText().toString()),
                Float.parseFloat(SES.getText().toString()), Float.parseFloat(MMSE.getText().toString()),
                Float.parseFloat(CDR.getText().toString()), Float.parseFloat(eTIV.getText().toString()),
                Float.parseFloat(nWBV.getText().toString()), Float.parseFloat(ASF.getText().toString()), sex, res);

        // we are use add value event listener method
        // which is called with database reference.
        // Create ID to each sheet
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.child(id).setValue(Patient).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateForm.this, "Fail to update the data.."+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                })
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        loadingbar.dismiss();
                        Toast.makeText(UpdateForm.this, "info has been updated..", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(UpdateForm.this, PredictList.class);
                        startActivity(i);
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(UpdateForm.this, "Fail to update the data..", Toast.LENGTH_SHORT).show();
            }
        });
    }

}