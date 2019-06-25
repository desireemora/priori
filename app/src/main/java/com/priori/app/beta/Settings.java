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
    private Switch swMantra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Defining the variables we will use
        textdark = findViewById(R.id.textView5);
        swdarkMode = findViewById(R.id.sw_DarkMode);
        swMantra = findViewById(R.id.mantraSwitch);

        //preferences sheet variables
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        //Sending intents when the back button is clicked
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


        //checking the saved state of toggles
        checkSharedPreferences();
//        Settings.this.recreate();

        //Switch mantra toggle onclick listener
        swMantra.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mEditor.putBoolean("mantraState", swMantra.isChecked()); // value to store
                mEditor.apply();
            }
        });

        //Switch dark mode toggle onclick listener
        swdarkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.putBoolean("darkState", swdarkMode.isChecked()); // value to store
                mEditor.apply();
                checkSharedPreferences();

            }
        });


    }

    private void checkSharedPreferences(){
        Boolean themeSet = mPreferences.getBoolean("darkState", true);
        Boolean mantraSet = mPreferences.getBoolean("mantraState", true);

        if (themeSet) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            textdark.setText("DARK MODE ON");
            //Settings.this.recreate();
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            textdark.setText("DARK MODE OFF");
            // Settings.this.recreate();
        }
        swdarkMode.setChecked(themeSet);
        swMantra.setChecked(mantraSet);

    }
}
