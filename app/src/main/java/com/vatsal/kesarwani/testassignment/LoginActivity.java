package com.vatsal.kesarwani.testassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ImageButton google2,facebook2;
    private TextInputEditText email,password;
    private String semail,spassword;
    private RadioButton remember;
    private TextView forgot;
    private Button loginButton;
    private TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeView();

        google2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        facebook2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                loginButton.setClickable(true);
                semail=email.getText().toString();
                spassword=password.getText().toString();

            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),OtpVerificationActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),Welcome.class));
    }

    private void initializeView() {
        mAuth=FirebaseAuth.getInstance();
        google2=findViewById(R.id.google02);
        facebook2=findViewById(R.id.facebook02);
        email=findViewById(R.id.email02);
        password=findViewById(R.id.password02);
        remember=findViewById(R.id.rememberRadio);
        forgot=findViewById(R.id.forgot);
        loginButton=findViewById(R.id.loginButton);
        loginButton.setClickable(false);
        signUp=findViewById(R.id.signUp02);
    }
}
