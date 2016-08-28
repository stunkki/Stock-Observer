package com.example.olli.financeproject;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class MainActivityold extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  TextView stock = (TextView) findViewById(R.id.stockTextView);

        /*
        WebView stocks = ((WebView) findViewById(R.id.webView));
        stocks.getSettings().setJavaScriptEnabled(true);
        stocks.loadUrl("www.google.com");
        */
        /*
        try {
            getStockData stockData = new getStockData();
            stock.setText((CharSequence) stockData.execute(" "));

        } catch (Exception e) {
            e.printStackTrace();
        }
    */

        }


    public String getData() throws Exception {

        String fullString = "";
        URL urlStock = new URL("http://www.google.com/");
        BufferedReader reader;
        reader = new BufferedReader(
                new InputStreamReader(
                        urlStock.openStream()));
        String line;
        while ((line = reader.readLine()) != null)
            fullString += line;
        reader.close();

        return fullString;

    }



    public void getStock() {
        Stock stock = null;
        try {
            stock = YahooFinance.get("INTC");
            BigDecimal price = stock.getQuote().getPrice();
            BigDecimal change = stock.getQuote().getChangeInPercent();
            BigDecimal peg = stock.getStats().getPeg();
            BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();

        } catch (IOException e) {
            e.printStackTrace();
        }

        stock.print();
    }

    private class getStockData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String fullString = null;
            try {
                fullString = "";
                URL urlStock = new URL("http://www.google.com/");
                BufferedReader reader;
                reader = new BufferedReader(
                        new InputStreamReader(
                                urlStock.openStream()));
                String line;
                while ((line = reader.readLine()) != null)
                    fullString += line;
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return fullString;
        }

        @Override
        protected void onPostExecute(String message) {
            //process message
        }
    }
}






