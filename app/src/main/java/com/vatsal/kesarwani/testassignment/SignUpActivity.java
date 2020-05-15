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

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ImageButton google01,facebook01;
    private TextInputEditText email, password;
    private RadioButton agree;
    private Button signup01;
    private TextView login01;
    private String semail,spassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initaizeView();

        google01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        facebook01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        agree.setActivated(false);
        agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                signup01.setClickable(true);
                semail=email.getText().toString();
                spassword=password.getText().toString();

            }
        });

        signup01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //auth();
            }
        });

        login01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

    }

    private void auth() {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),Welcome.class));
    }

    private void initaizeView() {
        mAuth=FirebaseAuth.getInstance();
        google01=findViewById(R.id.google01);
        facebook01=findViewById(R.id.facebook01);
        email=findViewById(R.id.email01);
        password=findViewById(R.id.password01);
        agree=findViewById(R.id.agreeRadio);
        signup01=findViewById(R.id.signUp01);
        signup01.setClickable(false);
        login01=findViewById(R.id.login01);
    }
}
