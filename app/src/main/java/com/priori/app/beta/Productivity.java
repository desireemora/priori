package com.priori.app.beta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageButton;

public class Productivity extends AppCompatActivity {

    static SharedPreferences mPreferences;
    static SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productivity);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        final ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Productivity.this, Welcome.class);
                startActivity(intent);
            }
        });
        checkSharedPreferences();
    }
    private void checkSharedPreferences() {

        Boolean themeSet = mPreferences.getBoolean("darkState", true);

        if (themeSet) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //Settings.this.recreate();
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            // Settings.this.recreate();
        }
    }

}
