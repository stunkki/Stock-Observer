package com.example.olli.financeproject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.util.ByteArrayBuffer;


import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView setSymbol;
    TextView symbolHeader;
    TextView priceHeader;
    TextView changePercentageHeader;
    TextView symbolOut;
    TextView priceOut;
    TextView changePercentageOut;
    TextView allStocks;
    Button getQuote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adding toolbar to Main view
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar(toolbar);
       // setSupportActionBar(toolbar);

        setSymbol = (AutoCompleteTextView) findViewById(R.id.setSymbol);
        // Get the string array
        String[] tickers = getResources().getStringArray(R.array.tickers_array);
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tickers);
        setSymbol.setAdapter(adapter);

        symbolHeader = (TextView) findViewById(R.id.symbolHeader);
        priceHeader = (TextView) findViewById(R.id.priceHeader);
        changePercentageHeader = (TextView) findViewById(R.id.changePercentageHeader);
        symbolOut = (TextView) findViewById(R.id.stockSymbolOutput);
        priceOut = (TextView) findViewById(R.id.stockPriceOutput);
        changePercentageOut = (TextView) findViewById(R.id.stockChangePercentageOutput);

        getQuote = (Button) findViewById(R.id.get_quote_button);






        getQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    String segments[] = setSymbol.getText().toString().split("\\|");
                    String firstSegment = segments[0];
                    List<String> results = new Task().execute(
                            firstSegment).get();
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


    public static class Task extends AsyncTask<String, Void, List<String>> {

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

    public void initToolbar(Toolbar toolbar) {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.searchItem:
                        Toast.makeText(getApplicationContext(), "Setting Reminder", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getBaseContext(), ObserverActivity.class);
                        /*
                        EditText editText = (EditText) findViewById(R.id.edit_message);
                        String message = editText.getText().toString();
                        intent.putExtra(EXTRA_MESSAGE, message);
                        */
                        String symbolString = symbolOut.getText().toString();
                        Log.d("symbolString:", symbolString);
                        intent.putExtra("symbol", symbolString);
                        startActivity(intent);

                        break;
                    case R.id.filterItem:

                        Toast.makeText(getApplicationContext(), "Filter", Toast.LENGTH_SHORT).show();
                        setContentView(R.layout.symbols);
                        allStocks = (TextView) findViewById(R.id.symbolsList);

                        StringBuilder text = new StringBuilder();
                        try {
                            File sdcard = Environment.getExternalStorageDirectory();
                            File file = new File(sdcard,"nasdaqListed.txt");

                            BufferedReader br = new BufferedReader(new FileReader(file));
                            String line;
                            while ((line = br.readLine()) != null) {
                                text.append(line);

                                text.append('\n');
                            }
                            br.close() ;
                        }catch (IOException e) {
                            e.printStackTrace();
                        }

                        /*
                            FileInputStream fis=null;
                            final StringBuffer buffer= new StringBuffer();

                            try {
                                fis = openFileInput("nasdaqListed.txt");
                                DataInputStream dataIO = new DataInputStream(fis);
                                String strLine;

                                if ((strLine = dataIO.readLine()) != null) {
                                    buffer.append(strLine);
                                }

                                dataIO.close();
                                fis.close();
                            }
                            catch  (Exception e) {
                            }
                        */
                            allStocks.setText(text.toString());
                        break;
                }
                return true;
            }
        });
        // Inflate a menu to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.toolbar_menu);
    }

}



