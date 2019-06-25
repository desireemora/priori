package com.priori.app.beta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity{


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        mAuth = FirebaseAuth.getInstance();
    }



    public void registerUser(View view){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void loginButtonActivity(View view){
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
    }
}
