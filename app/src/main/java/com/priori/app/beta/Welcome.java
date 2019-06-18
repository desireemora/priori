package com.priori.app.beta;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // beginning of working code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


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
        final ImageButton btnTracker = findViewById(R.id.btn_productivityTracker);
        btnTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Productivity.class);
                startActivity(intent);
            }
        });

//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                //TODO your background code
//                //      The next section should call on a JSON parser
//                String sURL = "http://api.openweathermap.org/data/2.5/weather?q=tampa&APPID=001ade5089a978cd383942ac275ac67c"; //just a string
//
//                // Connect to the URL using java's native library
//                URL url = null;
//
//                try {
//                    url = new URL(sURL);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
//
//                URLConnection request = null;
//
//                try {
//                    request = url.openConnection();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    request.connect();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                // Convert to a JSON object to print data
//                JsonParser jp = new JsonParser(); //from gson
//                JsonElement root = null; //Convert the input stream to a json element
//                try {
//                    root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                JsonObject rootObj = root.getAsJsonObject(); //May be an array, may be an object.
//                String weather = rootObj.get("temp").getAsString(); //just grab the weather
//
//                TextView weather_view_text = (TextView)findViewById(R.id.weather_temp_text);
//                weather_view_text.setText(weather);
//            }
//        });




    }
    protected void onResume(){
        super.onResume();
//        checkTheme(mPreferences.getString(getString(R.string.darkMode), "False"));

    }

    void checkTheme(String dark){
        if (dark == "True") {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //Settings.this.recreate();
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            // Settings.this.recreate();
        }
    }

}
