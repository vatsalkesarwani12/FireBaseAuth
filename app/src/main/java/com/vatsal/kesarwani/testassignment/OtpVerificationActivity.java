package com.vatsal.kesarwani.testassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class OtpVerificationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText otp;
    private TextView sendAgain;
    private Button verify;
    private String sotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        initializeViews();

        sotp=otp.getText().toString();
        sendAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO again ask for otp
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Authenticalte
                auth();
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });
    }

    private void auth() {
    }

    private void initializeViews() {
        mAuth=FirebaseAuth.getInstance();
        otp=findViewById(R.id.otp);
        sendAgain=findViewById(R.id.sendAgain);
        verify=findViewById(R.id.verify);
    }
}
