package com.priori.app.beta;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;

import org.json.JSONException;
import org.json.JSONObject;
import android.support.v7.app.AlertDialog;

import java.util.List;
import java.util.Locale;


public class WeatherPage extends AppCompatActivity {

    TextView selectCity, cityField, detailsField, currentTemperatureField, weatherIcon;
    ProgressBar loader;
    Typeface weatherFont;
    String city = "Tampa";
    /* Please Put your API KEY here */
    String OPEN_WEATHER_MAP_API = "001ade5089a978cd383942ac275ac67c";
    /* Please Put your API KEY here */
    private LocationManager locationManager;
    private static final int MY_PERMISSION_REQUEST_LOCATION = 1;


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

        checkSharedPreferences();
        taskLoadUp(city);

        selectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(WeatherPage.this);
                alertDialog.setTitle("Change City");
                final EditText input = new EditText(WeatherPage.this);
                input.setHint(city);
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
        final ImageButton getLocation = findViewById(R.id.btn_getLocation);
        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(WeatherPage.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    if(ActivityCompat.shouldShowRequestPermissionRationale(WeatherPage.this, Manifest.permission.ACCESS_COARSE_LOCATION)){
                        ActivityCompat.requestPermissions(WeatherPage.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);
                    } else {
                        ActivityCompat.requestPermissions(WeatherPage.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);
                    }

                } else {
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                    try{
                        cityField.setText(hereLocation(location.getLatitude(), location.getLongitude()));
                        city = (String) cityField.getText();
                        taskLoadUp(city);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(WeatherPage.this, "Not Found!", Toast.LENGTH_SHORT).show();
                    }
                }




            }
        });
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults){
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSION_REQUEST_LOCATION:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(WeatherPage.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                        try{
                            cityField.setText(hereLocation(location.getLatitude(), location.getLongitude()));
                            city = (String) cityField.getText();
                            taskLoadUp(city);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(WeatherPage.this, "Not Found!", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(this, "No permission Granted!", Toast.LENGTH_SHORT).show();
                }
            }
        }
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
                loader.setVisibility(View.GONE);
                city = (String) cityField.getText();
            }


        }


    }
    public String hereLocation(double lat, double lon){
        String curCity= "";

        Geocoder geocoder = new Geocoder(WeatherPage.this, Locale.getDefault());
        List<Address> addressList;
        try{
            addressList = geocoder.getFromLocation(lat,lon,1);
            if (addressList.size() > 0){
                curCity = addressList.get(0).getLocality();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return curCity;
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




