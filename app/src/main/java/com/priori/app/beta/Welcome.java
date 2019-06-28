package com.priori.app.beta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Welcome extends AppCompatActivity {

    static SharedPreferences mPreferences;
    static SharedPreferences.Editor mEditor;
    private TextView mantra_txt;
    private TextView weather_txt;
    private ImageButton btnTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // beginning of working code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Defining the variables we will use
        mantra_txt = findViewById(R.id.mantra_txt);
        weather_txt = findViewById(R.id.tv_weatherText);

        //preferences sheet variables
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        //checks for mantra visibility status




        final ImageButton settingsButton = findViewById(R.id.btn_settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Settings.class);
                startActivity(intent);
            }
        });

        final CalendarView cal = findViewById(R.id.cv_calendar);
        cal.setDate(System.currentTimeMillis(),true,true);
        final Button btnCalendar = findViewById(R.id.btnCalendar);
        final LinearLayout listView = findViewById(R.id.lv_ListView);
        cal.setVisibility(View.GONE);
        final TextView viewTitle = findViewById(R.id.tv_listTitle);
        viewTitle.setText("TOP 5");
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                viewTitle.setText("");

            }
        });
        final Button btnDaily = findViewById(R.id.btnDaily);
        btnDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                viewTitle.setText("Daily");
            }
        });
        final Button btnTop = findViewById(R.id.btn_top5);
        btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                viewTitle.setText("TOP 5");
            }
        });
        final ImageButton btnAddTask = findViewById(R.id.btn_addtask);
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, AddTask.class);
                startActivity(intent);
            }
        });
        btnTracker = findViewById(R.id.btn_productivityTracker);
        btnTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Productivity.class);
                startActivity(intent);
            }
        });
        checkSharedPreferences();

    }
    protected void onResume(){
        super.onResume();

    }


    private void checkSharedPreferences(){
        Boolean mantraSet = mPreferences.getBoolean("mantraState", true);
        Boolean themeSet = mPreferences.getBoolean("darkState", true);
        Boolean weatherWidget = mPreferences.getBoolean("weatherWidget", true);
        Boolean backupSet = mPreferences.getBoolean("backupState", true);
        Boolean trackerSet = mPreferences.getBoolean("trackerState", true);

        if(mantraSet == true){
            mantra_txt.setVisibility(View.VISIBLE);
        }else{
            mantra_txt.setVisibility(View.INVISIBLE);
        }

        if (themeSet){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        if (weatherWidget){
            weather_txt.setVisibility(View.VISIBLE);
        } else {
            weather_txt.setVisibility(View.INVISIBLE);
        }
        if (backupSet){
            //
        }
        if (trackerSet){
            btnTracker.setVisibility(View.VISIBLE);
        } else {
            btnTracker.setVisibility(View.INVISIBLE);
        }

    }





}
