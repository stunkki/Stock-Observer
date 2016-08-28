package com.example.olli.financeproject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {

    EditText setSymbol;
    TextView symbolHeader;
    TextView priceHeader;
    TextView changePercentageHeader;
    TextView symbolOut;
    TextView priceOut;
    TextView changePercentageOut;
    Button getQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSymbol = (EditText) findViewById(R.id.setSymbol);
        symbolOut = (TextView) findViewById(R.id.stockSymbolOutput);
        priceOut = (TextView) findViewById(R.id.stockPriceOutput);
        changePercentageOut = (TextView) findViewById(R.id.stockChangePercentageOutput);
        getQuote = (Button) findViewById(R.id.get_quote_button);


        getQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    List<String> results = new Task().execute(
                            setSymbol.getText().toString()).get();
                    setResult(results.get(0), results.get(1), results.get(2));
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }


    private void setResult(String fstockSymbol, String stockPrice, String fstockChangePercentage) {
        symbolOut.setText(fstockSymbol);
        priceOut.setText(stockPrice);
        changePercentageOut.setText(fstockChangePercentage);
    }


    class Task extends AsyncTask<String, Void, List<String>> {

        protected List<String> doInBackground(String... symbols) {
            try {
                URL url;

                try {
                    url = new URL(
                            "http://download.finance.yahoo.com/d/quotes.csv?s="
                                    + symbols[0] + "&f=sl1p2");

                    InputStream stream = url.openStream();

                    BufferedInputStream bis = new BufferedInputStream(stream);
                    ByteArrayBuffer baf = new ByteArrayBuffer(50);

                    int current = 0;
                    while ((current = bis.read()) != -1) {
                        baf.append((byte) current);
                    }

                    String stockTxt = new String(baf.toByteArray());

                    String[] tokens = stockTxt.split(",");

                    String stockSymbol = tokens[0];
                    String stockPrice = tokens[1];
                    String stockChangePercentage = tokens[2];

                    String fstockSymbol = stockSymbol.substring(1,
                            stockSymbol.length() - 1);
                    String fstockChangePercentage = stockChangePercentage
                            .substring(1, stockChangePercentage.length() - 3);

                    List<String> results = new ArrayList<String>();
                    results.add(fstockSymbol);
                    results.add(stockPrice);
                    results.add(fstockChangePercentage);

                    return results;

                } catch (MalformedURLException e) {

                    e.printStackTrace();
                } catch (IOException e) {

                    e.printStackTrace();
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}



