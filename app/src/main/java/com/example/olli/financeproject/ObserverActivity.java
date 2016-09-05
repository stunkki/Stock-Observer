package com.example.olli.financeproject;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/**
 * Created by olli on 31.8.2016.
 */
public class ObserverActivity extends AppCompatActivity{

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
                    if (minValueEditText.getText() != null){
                        minValue = Double.parseDouble(minValueEditText.getText().toString());
                        String minValueString = Double.toString(minValue);
                        minValueTextView.setText(minValueString);
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
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }});
        }
    }
   /*
    int refreshInterval;

    public void Notice(double minValue, double maxValue){

        if(R.id.getText() > maxValue)
        {
            //Notice
        }
        else if (R.id.getText() < minValue)
        {
            //
        }
    }

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




