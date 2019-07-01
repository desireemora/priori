package com.priori.app.beta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity{

    EditText email;
    EditText password;
    Button login;
    private CheckBox rememberCred;
    static SharedPreferences mPreferences;
    static SharedPreferences.Editor mEditor;
    private ProgressBar loadingBar;
    private ImageButton btnFingerprint;
    private TextView txtFingerprint;

    private FirebaseAuth firebaseAuth;

    private FingerprintManager fingerprintmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        email = findViewById(R.id.prompt_email);
        password = findViewById(R.id.prompt_password);
        login = findViewById(R.id.login_button);
        rememberCred = findViewById(R.id.cb_remember);
        loadingBar = findViewById(R.id.progressBar);
        loadingBar.setVisibility(View.GONE);
        btnFingerprint = findViewById(R.id.ibtn_fingerprint);
        btnFingerprint.setVisibility(View.GONE);
        txtFingerprint = findViewById(R.id.tv_fingerprint);
        txtFingerprint.setVisibility(View.GONE);

        checkSharedPreferences();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),
                        password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if (rememberCred.isChecked()) {
                                mEditor.putString("usrEmail", email.getText().toString());
                                mEditor.putString("usrPass", password.getText().toString());
                                mEditor.putBoolean("rememberCred", true);
                                mEditor.apply();
                            } else {
                                mEditor.putString("usrEmail", "");
                                mEditor.putString("usrPass", "");
                                mEditor.putBoolean("rememberCred", false);
                                mEditor.apply();
                            }
                            loadingBar.setVisibility(View.GONE);
                            startActivity(new Intent(Login.this, Welcome.class) );
                        }

                        else{
                            loadingBar.setVisibility(View.GONE);
                            Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        rememberCred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rememberCred.isChecked()) {
                    mEditor.putBoolean("rememberCred", false);
                    mEditor.apply();
                }

            }
        });
        fingerprintmanager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
        FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
        fingerprintHandler.startAuth(fingerprintmanager, null);

    }

    public void registerUser(View view){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
    private void checkSharedPreferences(){
        String usrEmail = mPreferences.getString("usrEmail", "");
        String usrPass = mPreferences.getString("usrPass", "");
        Boolean usrRememberCred = mPreferences.getBoolean("rememberCred", false);
        Boolean fingerLockSet = mPreferences.getBoolean("fingerLockState", true);
        Boolean themeSet = mPreferences.getBoolean("darkState", true);

        if (themeSet) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //Settings.this.recreate();
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            // Settings.this.recreate();
        }
        if (fingerLockSet){
            txtFingerprint.setVisibility(View.VISIBLE);
            btnFingerprint.setVisibility(View.VISIBLE);
        }
        if (usrRememberCred) {
            email.setText(usrEmail);
            password.setText(usrPass);
            rememberCred.setChecked(true);
        }


    }
}
