package com.priori.app.beta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    private Switch swdarkMode;
    private TextView textdark;
    static SharedPreferences mPreferences;
    static SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        textdark = findViewById(R.id.textView5);
        final ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, Welcome.class);
                startActivity(intent);
            }
        });
        final Button locationButton = findViewById(R.id.btn_location);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, WeatherPage.class);
                startActivity(intent);
            }
        });

        swdarkMode = findViewById(R.id.sw_DarkMode);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        checkSharedPreferences();
//        Settings.this.recreate();

        swdarkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swdarkMode.isChecked()){
                    mEditor.putString(getString(R.string.darkMode), "True");
                    mEditor.apply();
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                } else {
                    mEditor.putString(getString(R.string.darkMode), "False");
                    mEditor.apply();
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
//                checkSharedPreferences();
//                Settings.this.recreate();
            }
        });


    }
    private void checkSharedPreferences(){
        String themeSet = mPreferences.getString(getString(R.string.darkMode), "True");
        if (themeSet == "True") {
            swdarkMode.setChecked(true);
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            textdark.setText("DARK MODE ON");
            //Settings.this.recreate();
        } else {
            swdarkMode.setChecked(false);
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            textdark.setText("DARK MODE OFF");
           // Settings.this.recreate();
        }
    }
}
