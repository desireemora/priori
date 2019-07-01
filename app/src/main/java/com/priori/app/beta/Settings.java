package com.priori.app.beta;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Set;

public class Settings extends AppCompatActivity {

    private Button signout;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    private Switch swdarkMode;
    private Switch swWeather;
    private Switch swBackup;
    private Switch swFingerLock;
    private Switch swTracker;
    private Switch swMantra;
    private Button btnLocation;

    private FingerprintManager fingerprintmanager;
    private KeyguardManager keyguardManager;

    static SharedPreferences mPreferences;
    static SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Defining the variables we will use
        signout = findViewById(R.id.signout_button);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        btnLocation = findViewById(R.id.btn_location);
        swdarkMode = findViewById(R.id.sw_DarkMode);
        swMantra = findViewById(R.id.sw_mantraSwitch);
        swWeather = findViewById(R.id.sw_weather);
        swBackup = findViewById(R.id.sw_backupswitch);
        swTracker = findViewById(R.id.sw_Prodtracker);
        swFingerLock = findViewById(R.id.sw_fingerprintLock);

        //preferences sheet variables
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        //checking the saved state of toggles
        checkSharedPreferences();

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
        swTracker.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mEditor.putBoolean("trackerState", swTracker.isChecked()); // value to store
                mEditor.apply();
            }
        });
        swBackup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mEditor.putBoolean("backupState", swBackup.isChecked()); // value to store
                mEditor.apply();
            }
        });
        swFingerLock.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (swFingerLock.isChecked()){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
                        fingerprintmanager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
                        if (!fingerprintmanager.isHardwareDetected()) {
                            mEditor.putBoolean("fingerLockState", false); // value to store
                            mEditor.apply();
                            swFingerLock.setChecked(false);
                            Toast.makeText(Settings.this, "Fingerprint Scanner Hardware Missing", Toast.LENGTH_LONG).show();
                        } else if (ContextCompat.checkSelfPermission(Settings.this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                            mEditor.putBoolean("fingerLockState", false); // value to store
                            mEditor.apply();
                            swFingerLock.setChecked(false);
                            Toast.makeText(Settings.this, "Permission not granted", Toast.LENGTH_LONG).show();
                        } else if (!keyguardManager.isKeyguardSecure()){
                            mEditor.putBoolean("fingerLockState", false); // value to store
                            mEditor.apply();
                            swFingerLock.setChecked(false);
                            Toast.makeText(Settings.this, "You need to add fingerprint Lock to your phone in Settings", Toast.LENGTH_LONG).show();
                        } else if (!fingerprintmanager.hasEnrolledFingerprints()){
                            mEditor.putBoolean("fingerLockState", false); // value to store
                            mEditor.apply();
                            swFingerLock.setChecked(false);
                            Toast.makeText(Settings.this, "You should add at least one fingerprint to use this feature", Toast.LENGTH_LONG).show();
                        } else {
                            mEditor.putBoolean("fingerLockState", swFingerLock.isChecked()); // value to store
                            mEditor.apply();
                            Toast.makeText(Settings.this, "You need have successfully enabled Fingerprint Lock!", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    mEditor.putBoolean("fingerLockState", swFingerLock.isChecked()); // value to store
                    mEditor.apply();
                    Toast.makeText(Settings.this, "You need have successfully disabled Fingerprint Lock!", Toast.LENGTH_LONG).show();

                }
            }
        });

        swWeather.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mEditor.putBoolean("weatherWidget", swWeather.isChecked()); // value to store
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

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Settings.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void checkSharedPreferences(){

        Boolean mantraSet = mPreferences.getBoolean("mantraState", true);
        Boolean weatherSet = mPreferences.getBoolean("weatherWidget", true);
        Boolean backupSet = mPreferences.getBoolean("backupState", true);
        Boolean fingerLockSet = mPreferences.getBoolean("fingerLockState", true);
        Boolean trackerSet = mPreferences.getBoolean("trackerState", true);
        Boolean themeSet = mPreferences.getBoolean("darkState", true);
        String citySet = Settings.mPreferences.getString("citySetting", "Tampa, US");

        if (themeSet) {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //Settings.this.recreate();
        } else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            // Settings.this.recreate();
        }
        swdarkMode.setChecked(themeSet);
        swMantra.setChecked(mantraSet);
        swWeather.setChecked(weatherSet);
        swBackup.setChecked(backupSet);
        swFingerLock.setChecked(fingerLockSet);
        swTracker.setChecked(trackerSet);
        btnLocation.setText(citySet);

    }
}
