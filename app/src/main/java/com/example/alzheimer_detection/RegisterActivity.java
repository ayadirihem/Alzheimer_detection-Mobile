package com.example.alzheimer_detection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText repPass, email, password;
    Button conx, Login;
    ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.email = findViewById(R.id.EmailR);
        this.password = (EditText) this.findViewById(R.id.PasswordR);
        loadingbar = new ProgressDialog(this);
        this.conx = (Button) this.findViewById(R.id.SignUp);
        Login = findViewById(R.id.BackLogin);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        conx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString();
                String pwd = password.getText().toString();
                if (email.getText().toString().isEmpty()) {
                    email.setError("Please write your email ...");
                }
                else if (password.getText().toString().isEmpty()) {
                    password.setError("Please write your password ...");
                }
                else {
                    loadingbar.setTitle("Create Account");
                    loadingbar.setMessage("Please wait, while we are checking the credentials.");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();
                    mAuth.createUserWithEmailAndPassword(mail,pwd)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"Congratulation , your account has been created",Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();
                                        Intent i3 = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(i3);
                                    }
                                    else{

                                        Toast.makeText(getApplicationContext(),"Network error: Please Try again",Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();
                                    }
                                }
                            });
                }

            }
        });
    }
}