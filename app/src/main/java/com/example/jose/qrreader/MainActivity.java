package com.example.jose.qrreader;

import android.content.Context;
import android.content.Intent;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class MainActivity extends Activity {

    IntentIntegrator integrator = new IntentIntegrator(this);

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

            startNewActivity(Integer.parseInt(contents));
        }
    }

    public void startNewActivity(int newActivityID) {

        Class[] classes = {FirstActivityScanner.class, SecondActivityScanner.class};

        Intent intent = new Intent(this, classes[newActivityID]);
        startActivity(intent);

    }
}
