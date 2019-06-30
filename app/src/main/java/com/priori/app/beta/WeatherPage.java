package com.priori.app.beta;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import android.support.v7.app.AlertDialog;

import java.util.Locale;
import java.util.Set;


public class WeatherPage extends AppCompatActivity {

    TextView selectCity, cityField, detailsField, currentTemperatureField, humidity_field, pressure_field, weatherIcon, updatedField;
    ProgressBar loader;
    Typeface weatherFont;
    String city = "Tampa";
    /* Please Put your API KEY here */
    String OPEN_WEATHER_MAP_API = "001ade5089a978cd383942ac275ac67c";
    /* Please Put your API KEY here */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_page);

        loader = (ProgressBar) findViewById(R.id.loader);
        selectCity = (TextView) findViewById(R.id.selectCity);
        cityField = (TextView) findViewById(R.id.city_field);
//            updatedField = (TextView) findViewById(R.id.updated_field);
        detailsField = (TextView) findViewById(R.id.details_field);
        currentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
//            humidity_field = (TextView) findViewById(R.id.humidity_field);
//            pressure_field = (TextView) findViewById(R.id.pressure_field);
//            weatherIcon = (TextView) findViewById(R.id.weather_icon);
//            weatherFont = Typeface.createFromAsset(getAssets(), "assets/fonts/weathericons-regular-webfont.ttf");
//            weatherIcon.setTypeface(weatherFont);
        checkSharedPreferences();
        taskLoadUp(city);

        selectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(WeatherPage.this);
                alertDialog.setTitle("Change City");
                final EditText input = new EditText(WeatherPage.this);
                input.setText(city);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("Change",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                city = input.getText().toString();
                                taskLoadUp(city);
                            }
                        });
                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog.show();
            }
        });
        final ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherPage.this, Settings.class);
                startActivity(intent);
            }
        });

    }


    public void taskLoadUp(String query) {
        if (Weather.isNetworkAvailable(getApplicationContext())) {
            DownloadWeather task = new DownloadWeather();
            task.execute(query);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }


    class DownloadWeather extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loader.setVisibility(View.VISIBLE);

        }

        protected String doInBackground(String... args) {
            String xml = Weather.excuteGet("http://api.openweathermap.org/data/2.5/weather?q=" + args[0] +
                    "&units=imperial&appid=" + OPEN_WEATHER_MAP_API);
            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {

            try {
                JSONObject json = new JSONObject(xml);
                if (json != null) {
                    JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                    JSONObject main = json.getJSONObject("main");
                    DateFormat df = DateFormat.getDateTimeInstance();

                    cityField.setText(json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country"));
                    detailsField.setText(details.getString("description").toUpperCase(Locale.US));
                    currentTemperatureField.setText(String.format("%.2f", main.getDouble("temp")) + "°");
//                        humidity_field.setText("Humidity: " + main.getString("humidity") + "%");
//                        pressure_field.setText("Pressure: " + main.getString("pressure") + " hPa");
//                        updatedField.setText(df.format(new Date(json.getLong("dt") * 1000)));
                    loader.setVisibility(View.GONE);
                    Settings.mEditor.putString("citySetting", json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country"));
                    Settings.mEditor.apply();
                    Settings.mEditor.commit();

                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Error, Check City", Toast.LENGTH_SHORT).show();
            }


        }


    }
    private void checkSharedPreferences(){

        String citySet = Settings.mPreferences.getString("citySetting", "Tampa, US");
        Boolean themeSet = Settings.mPreferences.getBoolean("darkState", true);

        if (themeSet) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //Settings.this.recreate();
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            // Settings.this.recreate();
        }
        city = citySet;
        cityField.setText(citySet);

    }
}




