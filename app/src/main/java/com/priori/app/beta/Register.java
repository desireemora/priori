package com.priori.app.beta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private static final String KEY_NAME = "name";
    private static final String KEY_BDAY = "birthday";

    EditText email;
    EditText name;
    EditText password;
    EditText confirm_password;
    EditText bday;
    Button register;

    FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.register_email);
        name = findViewById(R.id.register_name);
        password = findViewById(R.id.register_password);
        confirm_password = findViewById(R.id.register_pass_confirm);
        bday = findViewById(R.id.register_birthday);
        register = findViewById(R.id.register_button);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void registerUser(View v){

        String db_name = name.getText().toString();
        String db_bday = bday.getText().toString();
        String db_email = email.getText().toString();

        Map<String, Object> user = new HashMap<>();

        user.put(KEY_NAME, db_name);
        user.put(KEY_BDAY, db_bday);

        db.collection("users").document(db_email).set(user);

        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),
                password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Register.this, "Registration successful",
                                        Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(Register.this, Login.class);
                                startActivity(intent);
                            }

                            else{
                                Toast.makeText(Register.this, task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });


    }

}
