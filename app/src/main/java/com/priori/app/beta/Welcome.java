package com.priori.app.beta;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.SystemClock;
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
import android.widget.Toast;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Random;


public class Welcome extends AppCompatActivity {

    static SharedPreferences mPreferences;
    static SharedPreferences.Editor mEditor;
    private TextView mantra_txt;
    private TextView weather_txt;
    private ImageButton btnTracker;
    private Handler handler = new Handler();
    private CalendarView cal;
    private LinearLayout listView;
    private TextView viewTitle;
    public static PrioriDB prioriDB;
    FirebaseAuth firebaseAuth;

    //Mantra Array
    Random rand = new Random();
    int n = rand.nextInt(53); // Gives n such that 0 <= n < 53

    String[] mantraArray = {"“The Way Get Started Is To Quit Talking And Begin Doing.” – Walt Disney",
            "“The Pessimist Sees Difficulty In Every Opportunity. The Optimist Sees Opportunity In Every Difficulty.” – Winston Churchill",
            "“Don’t Let Yesterday Take Up Too Much Of Today.” – Will Rogers","“You Learn More From Failure Than From Success. Don’t Let It Stop You. Failure Builds Character.” – Unknown",
            "“It’s Not Whether You Get Knocked Down, It’s Whether You Get Up.” – Inspirational Quote By Vince Lombardi",
            "“If You Are Working On Something That You Really Care About, You Don’t Have To Be Pushed. The Vision Pulls You.” – Steve Jobs",
            "“People Who Are Crazy Enough To Think They Can Change The World, Are The Ones Who Do.” – Rob Siltanen",
            "“Failure Will Never Overtake Me If My Determination To Succeed Is Strong Enough.” – Og Mandino",
            "“Entrepreneurs Are Great At Dealing With Uncertainty And Also Very Good At Minimizing Risk. That’s The Classic Entrepreneur.” – Mohnish Pabrai",
            "“We May Encounter Many Defeats But We Must Not Be Defeated.” – Maya Angelou",
            "“Knowing Is Not Enough; We Must Apply. Wishing Is Not Enough; We Must Do.” – Johann Wolfgang Von Goethe",
            "“Imagine Your Life Is Perfect In Every Respect; What Would It Look Like?” – Brian Tracy",
            "“We Generate Fears While We Sit. We Overcome Them By Action.” – Dr. Henry Link",
            "“Whether You Think You Can Or Think You Can’t, You’re Right.” – Quote By Henry Ford",
            "“Security Is Mostly A Superstition. Life Is Either A Daring Adventure Or Nothing.” – Helen Keller",
            "“The Man Who Has Confidence In Himself Gains The Confidence Of Others.” – Hasidic Proverb",
            "“The Only Limit To Our Realization Of Tomorrow Will Be Our Doubts Of Today.” – Franklin D. Roosevelt",
            "“Creativity Is Intelligence Having Fun.” – Albert Einstein",
            "“What You Lack In Talent Can Be Made Up With Desire, Hustle And Giving 110% All The Time.” – Don Zimmer",
            "“Do What You Can With All You Have, Wherever You Are.” – Theodore Roosevelt",
            "“Develop An ‘Attitude Of Gratitude’. Say Thank You To Everyone You Meet For Everything They Do For You.” – Brian Tracy",
            "“You Are Never Too Old To Set Another Goal Or To Dream A New Dream.” – C.S. Lewis",
            "“To See What Is Right And Not Do It Is A Lack Of Courage.” – Confucius",
            "“Reading Is To The Mind, As Exercise Is To The Body.” – Brian Tracy",
            "“Fake It Until You Make It! Act As If You Had All The Confidence You Require Until It Becomes Your Reality.” – Brian Tracy",
            "“The Future Belongs To The Competent. Get Good, Get Better, Be The Best!” – Brian Tracy",
            "“For Every Reason It’s Not Possible, There Are Hundreds Of People Who Have Faced The Same Circumstances And Succeeded.” – Jack Canfield",
            "“Things Work Out Best For Those Who Make The Best Of How Things Work Out.” – John Wooden",
            "“A Room Without Books Is Like A Body Without A Soul.” – Marcus Tullius Cicero","“I Think Goals Should Never Be Easy, They Should Force You To Work, Even If They Are Uncomfortable At The Time.” – Michael Phelps","“One Of The Lessons That I Grew Up With Was To Always Stay True To Yourself And Never Let What Somebody Else Says Distract You From Your Goals.” – Michelle Obama","“Today’s Accomplishments Were Yesterday’s Impossibilities.” – Robert H. Schuller","“The Only Way To Do Great Work Is To Love What You Do. If You Haven’t Found It Yet, Keep Looking. Don’t Settle.” – Steve Jobs","“You Don’t Have To Be Great To Start, But You Have To Start To Be Great.” – Zig Ziglar","“A Clear Vision, Backed By Definite Plans, Gives You A Tremendous Feeling Of Confidence And Personal Power.” – Brian Tracy","“There Are No Limits To What You Can Accomplish, Except The Limits You Place On Your Own Thinking.” – Brian Tracy","“Integrity Is The Most Valuable And Respected Quality Of Leadership. Always Keep Your Word.” - Brian Tracy","Your limitation—it’s only your imagination.","Push yourself, because no one else is going to do it for you.","Sometimes later becomes never. Do it now.","Great things never come from comfort zones.","Dream it. Wish it. Do it.","Success doesn’t just find you. You have to go out and get it.","The harder you work for something, the greater you’ll feel when you achieve it.","Dream bigger. Do bigger.","Don’t stop when you’re tired. Stop when you’re done.","Wake up with determination. Go to bed with satisfaction.","Do something today that your future self will thank you for.","Little things make big days.","It’s going to be hard, but hard does not mean impossible.","Don’t wait for opportunity. Create it.","Sometimes we’re tested not to show our weaknesses, but to discover our strengths.","The key to success is to focus on goals, not obstacles."};



    /* Please Put your API KEY here */
    String OPEN_WEATHER_MAP_API = "001ade5089a978cd383942ac275ac67c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // beginning of working code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Defining the variables we will use
        mantra_txt = findViewById(R.id.mantra_txt);
        mantra_txt.setText(mantraArray[n]);
        weather_txt = findViewById(R.id.tv_weatherText);
//        weatherIcon = (TextView) findViewById(R.id.tv_WeatherIcon);
//        weatherFont = Typeface.createFromAsset(getAssets(), "font/weathericonswebfont.ttf");
//        weatherIcon.setTypeface(weatherFont);


        //preferences sheet variables
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        //checks for mantra visibility status
        prioriDB = Room.databaseBuilder(getApplicationContext(),PrioriDB.class,"taskdb").allowMainThreadQueries().build();





        final ImageButton settingsButton = findViewById(R.id.btn_settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Settings.class);
                startActivity(intent);
            }
        });

        cal = findViewById(R.id.cv_calendar);
        cal.setDate(System.currentTimeMillis(),true,true);
        final Button btnCalendar = findViewById(R.id.btnCalendar);
        listView = findViewById(R.id.lv_ListView);

        List<TaskDB> mytasks;
        mytasks = Welcome.prioriDB.myTaskDai().getTasks();
        String holdTasks ="";
        int count = 0;

        TextView blank = findViewById(R.id.space);
        blank.setText("");
        TextView tsk1_view = findViewById(R.id.task1);
        tsk1_view.setText("");
        TextView tsk2_view = findViewById(R.id.task2);
        tsk2_view.setText("");
        TextView tsk3_view = findViewById(R.id.task3);
        tsk3_view.setText("");
        TextView tsk4_view = findViewById(R.id.task4);
        tsk4_view.setText("");
        TextView tsk5_view = findViewById(R.id.task5);
        tsk5_view.setText("");



        for(TaskDB tsk : mytasks){
            count = count + 1;

            //if(tsk.getUserID() == user.getUid()) {

                holdTasks = tsk.getTaskName() + " " + tsk.getDueDate() + " "+ tsk.getDueTime();

                switch(count){
                    case 1:
                        tsk1_view.setText(holdTasks);
                        break;
                    case 2:
                        tsk2_view.setText(holdTasks);
                        break;
                    case 3:
                        tsk3_view.setText(holdTasks);
                        break;
                    case 4:
                        tsk4_view.setText(holdTasks);
                        break;
                    case 5:
                        tsk5_view.setText(holdTasks);
                        break;

                }




            //}


            if (count == 5) {
                break;
            }

        }






        cal.setVisibility(View.GONE);
        viewTitle = findViewById(R.id.tv_listTitle);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView();
            }
        });
        final Button btnDaily = findViewById(R.id.btnDaily);
        btnDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailyView();
            }
        });
        final Button btnTop = findViewById(R.id.btn_top5);
        btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                top5View();
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
    private void calendarView(){
        cal.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        viewTitle.setText("");
    }
    private void top5View(){
        cal.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        viewTitle.setText("TOP 5");
    }
    private void dailyView(){
        cal.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        viewTitle.setText("Daily");
    }
    private Runnable updateWeather = new Runnable()
    {
        public void run()
        {
            //write here whaterver you want to repeat
            String citySet = mPreferences.getString("citySetting", "Tampa, US");
            taskLoadUp(citySet);
            //10 Minutes refresh rate
            handler.postDelayed(updateWeather,600000);
        }
    };
    protected void onResume(){
        super.onResume();
        checkSharedPreferences();

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

//                    cityField.setText(json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country"));
//                    detailsField.setText(details.getString("description").toUpperCase(Locale.US));
                    weather_txt.setText(String.format("%.0f", main.getDouble("temp")) + "°");
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Error, Check Weather Location", Toast.LENGTH_SHORT).show();
            }


        }


    }
    private void checkSharedPreferences(){
        Boolean mantraSet = mPreferences.getBoolean("mantraState", true);
        Boolean themeSet = mPreferences.getBoolean("darkState", true);
        Boolean weatherWidget = mPreferences.getBoolean("weatherWidget", true);
        Boolean backupSet = mPreferences.getBoolean("backupState", true);
        Boolean trackerSet = mPreferences.getBoolean("trackerState", true);
        Integer defaultView = mPreferences.getInt("defaultView", 0);

        if (defaultView == 0){
            top5View();
        } else if (defaultView == 1){
            calendarView();
        } else {
            dailyView();
        }
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
            handler.postDelayed(updateWeather,0);
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
