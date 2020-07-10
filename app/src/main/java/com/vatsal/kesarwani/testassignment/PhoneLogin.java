package com.vatsal.kesarwani.testassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneLogin extends AppCompatActivity {

    Button sendotp, verifyotp;
    EditText phoneNo, otp;
    String phn = "null", ot = "null", codesent;
    private static final String TAG = "PhoneLogin";
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        intit();
        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check()) {
                    Toast.makeText(PhoneLogin.this, "Enter 10 digits phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                verifyotp.setVisibility(View.VISIBLE);
                sendotp.setVisibility(View.GONE);
                otp.setVisibility(View.VISIBLE);
                mobileSignIn();
            }
        });

        verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!check2()){
                    Toast.makeText(PhoneLogin.this, "Enter the valid otp", Toast.LENGTH_SHORT).show();
                    return;
                }
                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(codesent, ot);
                signInWithPhoneAuthCredential(credential);
            }
        });

    }

    private void mobileSignIn() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phn,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        Toast.makeText(PhoneLogin.this, "Enter OTP", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(PhoneLogin.this, "Cannot create Account " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Error", e.getMessage());
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        Log.v("Success1", s);
                        codesent = s;
                    }
                });

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(PhoneLogin.this, "Success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(PhoneLogin.this, ""+user.getPhoneNumber(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(PhoneLogin.this, "Failed " + task.getException(), Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    private boolean check() {
        phn = phoneNo.getText().toString();
        return phn.length() == 10;
    }

    private boolean check2() {
        ot = otp.getText().toString();
        return ot.length() > 3;
    }

    private void intit() {
        sendotp = findViewById(R.id.sendotp);
        phoneNo = findViewById(R.id.phoneNo);
        otp = findViewById(R.id.otp);
        mAuth = FirebaseAuth.getInstance();
        verifyotp = findViewById(R.id.verifyotp);
    }
}