package com.priori.app.beta;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddTask extends AppCompatActivity {

    static SharedPreferences mPreferences;
    static SharedPreferences.Editor mEditor;

    public static PrioriDB prioriDB;

    Button task_btn_click;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        //preferences sheet variables
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        task_btn_click = findViewById(R.id.add_task_btn);

        prioriDB = Room.databaseBuilder(getApplicationContext(),PrioriDB.class,"taskdb").allowMainThreadQueries().build();

        final ImageButton backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTask.this, Welcome.class);
                startActivity(intent);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priorities_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        checkSharedPreferences();

        firebaseAuth = FirebaseAuth.getInstance();


        task_btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TaskDB task= new TaskDB();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                TextView task_name  = findViewById(R.id.et_taskName);
                TextView due_date  = findViewById(R.id.due_date_txtBox);
                TextView due_time  = findViewById(R.id.due_time_txtBox);

                task.setTaskName(task_name.getText().toString());
                task.setDueDate(due_date.getText().toString());
                task.setDueTime(due_time.getText().toString());
                task.setUserID(user.getUid());

                AddTask.prioriDB.myTaskDai().addTask(task);

                task_name.setText("");
                due_date.setText("");
                due_time.setText("");


                Toast.makeText(AddTask.this,"Task added",
                        Toast.LENGTH_LONG).show();
















            }
        });









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
