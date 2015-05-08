package com.example.jose.utplqr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class DisplayData extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private ProgressBar mProgressBar;
    private String mQRContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        //Assing progressBar
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);

        //Get QR contents from previous activity
        Intent intent = getIntent();
        mQRContents = intent.getStringExtra(MainActivity.INDIVIDUAL_NUMBER);

        setTitle("Test");

        String jsonUrl = "https://api.forecast.io/forecast/161fc4a073257857b499d246069eb4b3/37.8267,-122.423";

        getContents(jsonUrl);
    }

    /** Action Bar **/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first_activity_scanner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /***********************/

    public void getContents(String jsonUrl) {
        String testUrl = "http://es-la.dbpedia.org/sparql?default-graph-uri=&query=SELECT+*%0D%0AWHERE+%7B%0D%0A++%3Fs+a+%3Chttp%3A%2F%2Fdbpedia." +
                "org%2Fontology%2FPresident%3E+.%0D%0A++%3Fs+%3Chttp%3A%2F%2Fes-la.dbpedia.org%2Fproperty%2Ft%C3%ADtulo%3E+%3Chttp%3A%2F%2Fes-la.dbpedia." +
                "org%2Fresource%2FPresidente_de_Ecuador%3E+.%0D%0A++%3Fs+%3Fo+%3Fp%0D%0A%7D&format=application%2Fjson&timeout=0&debug=on";


        if (isNetworkAvailable()) {
            //Let the user know data is being loaded
            toggleRefresh();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(jsonUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    //Alert user about error
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });

                    try {
                        String jsonData = response.body().string();
                        //Diplay collected data on logcat
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            //TODO parse json data
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //TODO Update the display with the collected data
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    }
                    catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        } else {
            Toast.makeText(this, "Network is unavailable!", Toast.LENGTH_LONG).show();
            alertUserAboutError();
        }
    }

    public void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

}
