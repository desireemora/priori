package com.priori.app.beta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

public class AddTask extends AppCompatActivity {

    static SharedPreferences mPreferences;
    static SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        //preferences sheet variables
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        final ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTask.this, Welcome.class);
                startActivity(intent);
            }
        });
        final Spinner spPriorities = findViewById(R.id.sp_priorities);
        final Spinner spList = findViewById(R.id.sp_list);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priorities_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriorities.setAdapter(adapter);
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
