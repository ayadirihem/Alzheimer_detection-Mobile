package com.example.alzheimer_detection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;


public class AddPredict extends AppCompatActivity {
    String url = "https://alzheimerpredictionmodel.herokuapp.com/predict";
    EditText FullName, Age, EDUC, SES, MMSE, CDR, eTIV, nWBV, ASF;
    RadioButton Male,Female;
    RadioGroup genderBtns;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.add_predictionform);

        FullName = findViewById(R.id.PatientName);
        Age = findViewById(R.id.AgePerson);
        EDUC = findViewById(R.id.EDUCPatient);
        SES = findViewById(R.id.SESPatient);
        MMSE = findViewById(R.id.MMSEPatient);
        CDR = findViewById(R.id.CDRPatient);
        eTIV = findViewById(R.id.eTIVPatient);
        nWBV = findViewById(R.id.nWBVPatient);
        ASF = findViewById(R.id.ASFPatient);
        Male = findViewById(R.id.radio_Male);
        Female = findViewById(R.id.radio_Female);
        Predict = findViewById(R.id.PredictBtn);
        Return = findViewById(R.id.Return);
        genderBtns = findViewById(R.id.GenderGroup);
        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("PatientSheet");
        loadingbar = new ProgressDialog(this);
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPredict.this,PredictList.class);
                startActivity(intent);
            }
        });


        Predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(FullName.getText().toString()))
                    FullName.setError("FullName field can't be empty");
                else if (TextUtils.isEmpty(Age.getText().toString()))
                    Age.setError("Age field can't be empty");
                else if(TextUtils.isEmpty(EDUC.getText().toString()))
                    EDUC.setError("EDUC field can't be empty");
                else if (TextUtils.isEmpty(SES.getText().toString()))
                    SES.setError(("SES field can't be empty"));
                else if (TextUtils.isEmpty(MMSE.getText().toString()))
                    MMSE.setError("MMSE field can't be empty");
                else if(TextUtils.isEmpty(CDR.getText().toString()))
                    CDR.setError("CDR field can't be empty");
                else if (TextUtils.isEmpty(eTIV.getText().toString()))
                    eTIV.setError("eTIV field can't be empty");
                else if(TextUtils.isEmpty(nWBV.getText().toString()))
                    nWBV.setError("nWBV field can't be empty");
                else if(TextUtils.isEmpty(ASF.getText().toString()))
                    ASF.setError("ASF field can't be empty");
                else if (genderBtns.getCheckedRadioButtonId() == -1)
                    Toast.makeText(AddPredict.this, "You need to choose a gender",Toast.LENGTH_SHORT).show();
                else {
                    loadingbar.setTitle("Prediction ...");
                    loadingbar.setMessage("Please wait, while we are checking the credentials.");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();
                    // hit the API -> Volley
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
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

                                        addDatatoFirebase(sex, res);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    loadingbar.dismiss();
                                    Toast.makeText(AddPredict.this, "Oooops ! Network error: Please Try again", Toast.LENGTH_SHORT).show();
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
                    RequestQueue queue = Volley.newRequestQueue(AddPredict.this);
                    queue.add(stringRequest);


                }
            }
        });
    }


    private void addDatatoFirebase(String sex, String res) {
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
        String key = databaseReference.child("PatientSheet").push().getKey();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.child(key).setValue(Patient);
                loadingbar.dismiss();
                ResultDialog(res);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(AddPredict.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ResultDialog(String res){

        AlertDialog.Builder builder = new AlertDialog.Builder(AddPredict.this);
        final View layout = getLayoutInflater().inflate(R.layout.dialog_result, null);
        TextView DialogN = layout.findViewById(R.id.DialogPName);
        TextView predt = layout.findViewById(R.id.Prediction);
        DialogN.setText("Patient name: "+FullName.getText().toString());
        predt.setText("Prediction: "+res);
        builder .setView(layout)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(AddPredict.this, PredictList.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}

