package com.priori.app.beta;

import android.app.ActionBar;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


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

        String test;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy ");
        String strDate = "Current Date : " + mdformat.format(cal.getTime());

        SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.US);

        //getting vals from prefs
        int mon_count = mPreferences.getInt("Mon",0);
        int tue_count = mPreferences.getInt("Tue",0);
        int wed_count = mPreferences.getInt("Wed",0);
        int thu_count = mPreferences.getInt("Thu",0);
        int fri_count = mPreferences.getInt("Fri",0);
        int sat_count = mPreferences.getInt("Sat",0);
        int sun_count = mPreferences.getInt("Sun",0);


        //dot views
        ImageView mon_dot = findViewById(R.id.mon_dot);
        ImageView tue_dot = findViewById(R.id.tue_dot);
        ImageView wed_dot = findViewById(R.id.wed_dot);
        ImageView thu_dot = findViewById(R.id.thu_dot);
        ImageView fri_dot = findViewById(R.id.fri_dot);
        ImageView sat_dot = findViewById(R.id.sat_dot);
        ImageView sun_dot = findViewById(R.id.sun_dot);

        //Toast.makeText(Productivity.this,String.valueOf(day_count), Toast.LENGTH_SHORT).show();

        //params.width = 200; params.leftMargin = 100; params.topMargin = 200;
        //114 only moved up to one
        //214 only moved up to two
        //300 for 3
        //378 for 4
        //460 for 5
        //540 for 6
        //624 for 7
        //700 for 8
        //790 for 9
        //870 for 10+

        //all parameter functions for dots mon-sun
        ViewGroup.MarginLayoutParams mon_params = (ViewGroup.MarginLayoutParams) mon_dot.getLayoutParams();
        ViewGroup.MarginLayoutParams tue_params = (ViewGroup.MarginLayoutParams) tue_dot.getLayoutParams();
        ViewGroup.MarginLayoutParams wed_params = (ViewGroup.MarginLayoutParams) wed_dot.getLayoutParams();
        ViewGroup.MarginLayoutParams thu_params = (ViewGroup.MarginLayoutParams) thu_dot.getLayoutParams();
        ViewGroup.MarginLayoutParams fri_params = (ViewGroup.MarginLayoutParams) fri_dot.getLayoutParams();
        ViewGroup.MarginLayoutParams sat_params = (ViewGroup.MarginLayoutParams) sat_dot.getLayoutParams();
        ViewGroup.MarginLayoutParams sun_params = (ViewGroup.MarginLayoutParams) sun_dot.getLayoutParams();

        //Monday
        if(mon_count == 1){
            mon_params.bottomMargin = (114);
        }
        if(mon_count == 2){
            mon_params.bottomMargin = (214);
        }
        if(mon_count == 3){
            mon_params.bottomMargin = (300);
        }
        if(mon_count == 4){
            mon_params.bottomMargin = (378);
        }
        if(mon_count == 5){
            mon_params.bottomMargin = (460);
        }
        if(mon_count == 6){
            mon_params.bottomMargin = (540);
        }
        if(mon_count == 7){
            mon_params.bottomMargin = (624);
        }
        if(mon_count == 8){
            mon_params.bottomMargin = (700);
        }
        if(mon_count == 9){
            mon_params.bottomMargin = (790);
        }
        if(mon_count >= 10){
            mon_params.bottomMargin = (870);
        }

        //Tuesday
        if(tue_count == 1){
            tue_params.bottomMargin = (114);
        }
        if(tue_count == 2){
            tue_params.bottomMargin = (214);
        }
        if(tue_count == 3){
            tue_params.bottomMargin = (300);
        }
        if(tue_count == 4){
            tue_params.bottomMargin = (378);
        }
        if(tue_count == 5){
            tue_params.bottomMargin = (460);
        }
        if(tue_count == 6){
            tue_params.bottomMargin = (540);
        }
        if(tue_count == 7){
            tue_params.bottomMargin = (624);
        }
        if(tue_count == 8){
            tue_params.bottomMargin = (700);
        }
        if(tue_count == 9){
            tue_params.bottomMargin = (790);
        }
        if(tue_count >= 10){
            tue_params.bottomMargin = (870);
        }

        //Wednesday
        if(wed_count == 1){
            wed_params.bottomMargin = (114);
        }
        if(wed_count == 2){
            wed_params.bottomMargin = (214);
        }
        if(wed_count == 3){
            wed_params.bottomMargin = (300);
        }
        if(wed_count == 4){
            wed_params.bottomMargin = (378);
        }
        if(wed_count == 5){
            wed_params.bottomMargin = (460);
        }
        if(wed_count == 6){
            wed_params.bottomMargin = (540);
        }
        if(wed_count == 7){
            wed_params.bottomMargin = (624);
        }
        if(wed_count == 8){
            wed_params.bottomMargin = (700);
        }
        if(wed_count == 9){
            wed_params.bottomMargin = (790);
        }
        if(wed_count >= 10){
            wed_params.bottomMargin = (870);
        }

        //Thursday
        if(thu_count == 1){
            thu_params.bottomMargin = (114);
        }
        if(thu_count == 2){
            thu_params.bottomMargin = (214);
        }
        if(thu_count == 3){
            thu_params.bottomMargin = (300);
        }
        if(thu_count == 4){
            thu_params.bottomMargin = (378);
        }
        if(thu_count == 5){
            thu_params.bottomMargin = (460);
        }
        if(thu_count == 6){
            thu_params.bottomMargin = (540);
        }
        if(thu_count == 7){
            thu_params.bottomMargin = (624);
        }
        if(thu_count == 8){
            thu_params.bottomMargin = (700);
        }
        if(thu_count == 9){
            thu_params.bottomMargin = (790);
        }
        if(thu_count >= 10){
            thu_params.bottomMargin = (870);
        }

        //Friday
        if(fri_count == 1){
            fri_params.bottomMargin = (114);
        }
        if(fri_count == 2){
            fri_params.bottomMargin = (214);
        }
        if(fri_count == 3){
            fri_params.bottomMargin = (300);
        }
        if(fri_count == 4){
            fri_params.bottomMargin = (378);
        }
        if(fri_count == 5){
            fri_params.bottomMargin = (460);
        }
        if(fri_count == 6){
            fri_params.bottomMargin = (540);
        }
        if(fri_count == 7){
            fri_params.bottomMargin = (624);
        }
        if(fri_count == 8){
            fri_params.bottomMargin = (700);
        }
        if(fri_count == 9){
            fri_params.bottomMargin = (790);
        }
        if(fri_count >= 10){
            fri_params.bottomMargin = (870);
        }

        //Saturday
        if(sat_count == 1){
            sat_params.bottomMargin = (114);
        }
        if(sat_count == 2){
            sat_params.bottomMargin = (214);
        }
        if(sat_count == 3){
            sat_params.bottomMargin = (300);
        }
        if(sat_count == 4){
            sat_params.bottomMargin = (378);
        }
        if(sat_count == 5){
            sat_params.bottomMargin = (460);
        }
        if(sat_count == 6){
            sat_params.bottomMargin = (540);
        }
        if(sat_count == 7){
            sat_params.bottomMargin = (624);
        }
        if(sat_count == 8){
            sat_params.bottomMargin = (700);
        }
        if(sat_count == 9){
            sat_params.bottomMargin = (790);
        }
        if(sat_count >= 10){
            sat_params.bottomMargin = (870);
        }

        //Sunday
        if(sun_count == 1){
            sun_params.bottomMargin = (114);
        }
        if(sun_count == 2){
            sun_params.bottomMargin = (214);
        }
        if(sun_count == 3){
            sun_params.bottomMargin = (300);
        }
        if(sun_count == 4){
            sun_params.bottomMargin = (378);
        }
        if(sun_count == 5){
            sun_params.bottomMargin = (460);
        }
        if(sun_count == 6){
            sun_params.bottomMargin = (540);
        }
        if(sun_count == 7){
            sun_params.bottomMargin = (624);
        }
        if(sun_count == 8){
            sun_params.bottomMargin = (700);
        }
        if(sun_count == 9){
            sun_params.bottomMargin = (790);
        }
        if(sun_count >= 10){
            sun_params.bottomMargin = (870);
        }


    }

    private void checkSharedPreferences(){

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
