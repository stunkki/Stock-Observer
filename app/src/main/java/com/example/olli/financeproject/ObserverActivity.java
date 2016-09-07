package com.example.olli.financeproject;


import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

/**
 * Created by olli on 31.8.2016.
 */
public class ObserverActivity extends AppCompatActivity {

    /* UI Elements */
    TextView symbolViewText;
    EditText minValueEditText = null;
    EditText maxValueEditText = null;
    TextView minValueTextView;
    TextView maxValueTextView;
    Button setMinValueBtn;
    Button setMaxValueBtn;

    /* Logic variables */
    double minValue, maxValue;
    int refreshInterval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer);


        symbolViewText = (TextView) findViewById(R.id.symbol_view_text);
        minValueEditText = (EditText) findViewById(R.id.min_value);
        maxValueEditText = (EditText) findViewById(R.id.max_value);
        setMinValueBtn = (Button) findViewById(R.id.set_min_value);
        setMaxValueBtn = (Button) findViewById(R.id.set_max_value);
        minValueTextView = (TextView) findViewById(R.id.min_value_text_view);
        maxValueTextView = (TextView) findViewById(R.id.max_value_text_view);

        setMinValueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    if (minValueEditText.getText() != null) {
                        minValue = Double.parseDouble(minValueEditText.getText().toString());
                        String minValueString = Double.toString(minValue);
                        minValueTextView.setText(minValueString);
                        noticeMin(minValue);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });


        setMaxValueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    if (maxValueEditText.getText() != null) {
                        maxValue = Double.parseDouble(maxValueEditText.getText().toString());
                        String maxValueString = Double.toString(maxValue);
                        maxValueTextView.setText(maxValueString);
                        noticeMax(maxValue);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
            }
        });
    }


    public void noticeMax(final double maxValue) {
        //Declare the timer
        Timer myTimer = new Timer();
        //Set the schedule function and rate
        myTimer.scheduleAtFixedRate(new TimerTask() {
                                        @Override
                                        public void run() {
                                            //Called at every 120 000 milliseconds (120 second, 2 min)
                                            Log.i("MainActivity", "Repeated task");
                                            Bundle extras = getIntent().getExtras();
                                            Log.d("extras.getString(sym):", extras.getString("symbol"));
                                                if (extras != null){
                                                String stockName = extras.getString("symbol");
                                                try {
                                                    List<String> results = new MainActivity.Task().execute(stockName).get();
                                                    //Toast.makeText(getApplicationContext(), results.get(1), Toast.LENGTH_SHORT).show();
                                                    if (Double.parseDouble(results.get(1)) > maxValue) {
                                                        Log.d("Log value over shit", results.get(1));
                                                        makeNotificationMax();
                                                    }



                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                } catch (ExecutionException e) {
                                                    e.printStackTrace();
                                                }

                                            }


                                        }
                                    },
                //set the amount of time in milliseconds before first execution
                10000, // 10 seconds
                //Set the amount of time between each execution (in milliseconds)
                60000); // 60 seconds
    }
    public void noticeMin(final double minValue) {
        //Declare the timer
        Timer myTimer = new Timer();
        //Set the schedule function and rate
        myTimer.scheduleAtFixedRate(new TimerTask() {
                                        @Override
                                        public void run() {
                                            //Called at every 120 000 milliseconds (120 second, 2 min)
                                            Log.i("MainActivity", "Repeated task");
                                            Bundle extras = getIntent().getExtras();
                                            Log.d("extras.getString(sym):", extras.getString("symbol"));
                                            if (extras != null){
                                                String stockName = extras.getString("symbol");
                                                try {
                                                    List<String> results = new MainActivity.Task().execute(stockName).get();
                                                    //Toast.makeText(getApplicationContext(), results.get(1), Toast.LENGTH_SHORT).show();

                                                    if (Double.parseDouble(results.get(1)) < minValue) {
                                                        Log.d("Log value under shit", results.get(1));
                                                        makeNotificationMin();
                                                    }

                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                } catch (ExecutionException e) {
                                                    e.printStackTrace();
                                                }

                                            }


                                        }
                                    },
                //set the amount of time in milliseconds before first execution
                10000, // 10 seconds
                //Set the amount of time between each execution (in milliseconds)
                60000); // 60 seconds
    }
    public void makeNotificationMax(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Stock Observer")
                        .setContentText("Stock price over maximum!");
    }
    public void makeNotificationMin(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Stock Observer")
                        .setContentText("Stock price over minimum!");
    }
    /*
    public void setMinValue(double min)
    {
        minValueEditText = min;
    }
    public void setMaxValue(double max)
    {
        maxValueEditText = max;
    }

    public void setRefreshInterval(int minutes)
    {
        refreshInterval = minutes;
    }
    */

}


