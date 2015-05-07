package com.example.jose.utplqr;

import android.content.Intent;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.google.zxing.integration.android.IntentIntegrator;

import SampleDataBase.IndividualCreator;


public class MainActivity extends Activity {

    IntentIntegrator integrator = new IntentIntegrator(this);

    public static final IndividualCreator mCreator = new IndividualCreator();

    public static final String INDIVIDUAL_NUMBER = "INDIVIDUAL_NUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonStartScan = (Button) findViewById(R.id.buttonStartScan);


        buttonStartScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new IntentIntegrator(this).initiateScan();

                integrator.setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                integrator.setScanningRectangle(500, 500);
                integrator.setCaptureLayout(R.layout.custom_capture_layout);
                integrator.setLegacyCaptureLayout(R.layout.custom_legacy_capture_layout);
                integrator.initiateScan();
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        /**Does Not Work**/

        /*IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanResult != null) {

            String result = intent.getStringExtra("SCAN_RESULT");
            String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
            //Toast.makeText(this, result, Toast.LENGTH_LONG).show();

            startNewActivity(Integer.parseInt(result));
        }


         if (requestCode == 0) {
            String contents = intent.getStringExtra("SCAN_RESULT");
            String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

            //startNewActivity(Integer.parseInt(contents));
        } */

        /****/

        if (resultCode == RESULT_CANCELED) {
            //Handle cancel
        } else {
            String contents = intent.getStringExtra("SCAN_RESULT");
            String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

            startNewActivity(contents);
        }
    }

    public void startNewActivity(String contents) {

        Intent intent = new Intent(this, FirstActivityScanner.class);
        intent.putExtra(INDIVIDUAL_NUMBER, contents);
        startActivity(intent);

    }
}
