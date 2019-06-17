package desireemora.mail.usf.jarvis_beta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        //weather API setup
//        string url = "http://api.openweathermap.org/data/2.5/weather?q=tampa&APPID=001ade5089a978cd383942ac275ac67c";
//        URL obj = new URL(url);
//        httpURLConnection con = (httpURLConnection) obj.openConnection();
//        //The next section should call on a JSON parser

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

    }
    void calendarView(View view){

    }

}
