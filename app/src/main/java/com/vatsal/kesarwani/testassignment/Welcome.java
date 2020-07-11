package com.vatsal.kesarwani.testassignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.LogWriter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.auth.OAuthProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Welcome extends AppCompatActivity {

    private static final int RC_SIGN_IN = 10;
    private FirebaseAuth mAuth;
    //private ImageButton facebook;
    //private SignInButton google;
    private LoginButton facebook;
    private Button email, github,google,phone,twitter,yahoo,anonymous;
    //private TextView login;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager callbackManager;
    private String EMAIL = "email";
    private AccessTokenTracker accessTokenTracker;
    //private LoginManager.getInstance().logInWithReadPermissions(this,Arrays.asList("public_profile"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        initializeView();

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PhoneLogin.class));
            }
        });

        anonymous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anonSignin();
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twitterSignin();
            }
        });

        yahoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yahooSignin();
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googlesignIn();
            }
        });

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                githubSignIn();
            }
        });

        /**facebook*/
        facebook.setReadPermissions(Arrays.asList(EMAIL));

        facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.w("Facebook", "" + loginResult);
                handlerFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
                Log.w("Facebook", "cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.w("Facebook", "" + exception);
            }
        });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    mAuth.signOut();
                }
            }
        };
        /**facebook*/

    }

    private void githubSignIn() {

        OAuthProvider.Builder provider = OAuthProvider.newBuilder("github.com");
        // Request read access to a user's email addresses.
        // This must be preconfigured in the app's API permissions.
        List<String> scopes =
                new ArrayList<String>() {
                    {
                        add("user:email");
                    }
                };
        provider.setScopes(scopes);

        mAuth.startActivityForSignInWithProvider(/* activity= */ Welcome.this, provider.build())
                .addOnSuccessListener(
                        new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                // User is signed in.
                                // IdP data available in
                                // authResult.getAdditionalUserInfo().getProfile().
                                // The OAuth access token can also be retrieved:
                                //  authResult.getCredential().getAccessToken().
                                Log.d("git token",authResult.getUser().getDisplayName());
                                //Log.d("git cred",authResult.getCredential().toString());
                                //Log.d("access token",((OAuthCredential)authResult.getCredential()).getAccessToken());


                                Toast.makeText(Welcome.this, "Github Sign in successful", Toast.LENGTH_SHORT).show();
                                Log.d("git login","success");
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle failure.
                                Toast.makeText(Welcome.this, "Github Sign in failed", Toast.LENGTH_SHORT).show();
                                Log.d("git login","failed"+ e.getMessage());
                            }
                        });

    }


    private void yahooSignin() {

        OAuthProvider.Builder provider = OAuthProvider.newBuilder("yahoo.com");

        mAuth
                .startActivityForSignInWithProvider(/* activity= */ this, provider.build())
                .addOnSuccessListener(
                        new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                // User is signed in.
                                // IdP data available in
                                // authResult.getAdditionalUserInfo().getProfile().
                                // The OAuth access token can be retrieved:
                                // authResult.getCredential().getAccessToken().
                                // Yahoo OAuth ID token can also be retrieved:
                                // authResult.getCredential().getIdToken().
                                Toast.makeText(Welcome.this, ""+authResult.getUser().getDisplayName(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle failure.
                            }
                        });

    }

    private void twitterSignin() {
        OAuthProvider.Builder provider = OAuthProvider.newBuilder("twitter.com");

        mAuth
                .startActivityForSignInWithProvider(/* activity= */ this, provider.build())
                .addOnSuccessListener(
                        new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                // User is signed in.
                                // IdP data available in
                                // authResult.getAdditionalUserInfo().getProfile().
                                // The OAuth access token can also be retrieved:
                                // authResult.getCredential().getAccessToken().
                                // The OAuth secret can be retrieved by calling:
                                // authResult.getCredential().getSecret().
                                Toast.makeText(Welcome.this, ""+authResult.getUser().getDisplayName(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle failure.
                                Toast.makeText(Welcome.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
    }

    private void anonSignin() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("anonymous", "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Welcome.this, ""+user.getDisplayName(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("anonymous", "signInAnonymously:failure", task.getException());
                            Toast.makeText(Welcome.this, "Authentication failed.",Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }


//    AccessToken accessToken = AccessToken.getCurrentAccessToken();
//    boolean isLoggedIn = accessToken != null && !accessToken.isExpired();


    private void handlerFacebookToken(AccessToken loginResult) {

        Log.d("Facebok", "" + loginResult);

        AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Login Successful
                            Log.w("facebook", "Log in successful");
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        } else {
                            Log.w("Facebook", "login in failed " + task.getException());
                        }
                    }
                });

    }

    private void googlesignIn() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //Intent signInIntent =Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("Welcome01", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("Welcome01", "Google sign in failed", e);
                // ...
                Toast.makeText(this, "Google Sign In Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Welcome01", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Welcome01", "signInWithCredential:failure", task.getException());
                            //Snackbar.make(google, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            /*updateUI(null);*/
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }

    private void initializeView() {
        mAuth = FirebaseAuth.getInstance();
        google = findViewById(R.id.google);
        facebook = findViewById(R.id.facebook);
        facebook.setReadPermissions("email", "public_profile");
        email = findViewById(R.id.signUp);
        //login = findViewById(R.id.login);
        callbackManager = CallbackManager.Factory.create();
        github = findViewById(R.id.github);
        phone=findViewById(R.id.phone);
        twitter=findViewById(R.id.twitter);
        yahoo=findViewById(R.id.yahoo);
        anonymous=findViewById(R.id.anonymous);
    }
}
