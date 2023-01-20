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

public class LoginActivity extends AppCompatActivity {

    Button loginBtn, RegisterBtn;
    EditText email, password;
    private FirebaseAuth mAuth;
    ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.LOGIN);
        RegisterBtn = findViewById(R.id.Register);
        this.email = (EditText) this.findViewById(R.id.Email);
        this.password = (EditText) this.findViewById(R.id.Password);
        loadingbar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty()) {
                    email.setError("Please write your email ...");
                }
                else if (password.getText().toString().isEmpty()) {
                    password.setError("Please write your password ...");
                }
                else {
                    loadingbar.setTitle("Login Account");
                    loadingbar.setMessage("Please wait, while we are checking the credentials.");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();
                    String Mail = email.getText().toString();
                    String pwd = password.getText().toString();
                    mAuth.signInWithEmailAndPassword(Mail, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "logged in Successful", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                                Intent i = new Intent(LoginActivity.this, Dashbord.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Account do not exist: please check your information", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                            }
                        }
                    });

            }
        };
        });
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });


    }
}