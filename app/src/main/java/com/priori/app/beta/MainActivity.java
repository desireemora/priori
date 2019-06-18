package com.priori.app.beta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.*;


import com.priori.app.beta.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run(){
                // do something
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                // If you just use this that is not a valid context. Use ActivityName.this
                startActivity(intent);
            }
        }, 1000);
    }


}
