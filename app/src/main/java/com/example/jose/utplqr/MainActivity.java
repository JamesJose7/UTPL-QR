package com.example.jose.utplqr;

import android.content.Intent;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.zxing.integration.android.IntentIntegrator;


public class MainActivity extends Activity {

    IntentIntegrator integrator = new IntentIntegrator(this);

    public static final String INDIVIDUAL_NUMBER = "INDIVIDUAL_NUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonStartScan = (Button) findViewById(R.id.buttonStartScan);


        buttonStartScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Custom scan view
                integrator.setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                integrator.setScanningRectangle(500, 500);
                integrator.setCaptureLayout(R.layout.custom_capture_layout);
                integrator.setLegacyCaptureLayout(R.layout.custom_legacy_capture_layout);
                integrator.initiateScan();
            }
        });

        /** Test for emulators (Skip QR Scan) **/
        /*String test = "Virgen de la Sabiduria#http://icons.iconarchive.com/icons/mazenl77/I-like-buttons-3a/512/Cute-Ball-Go-icon.png#Ruth Simaluisa#2003#Capilla#Pintura#El valor espiritual, tambien denominado como simbolo universitario";

        startNewActivity(test);*/





    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {


        if (resultCode == RESULT_CANCELED) {
            //Handle cancel
        } else {
            String contents = intent.getStringExtra("SCAN_RESULT");
            String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

            startNewActivity(contents);
        }
    }

    public void startNewActivity(String contents) {

        Intent intent = new Intent(this, DisplayData.class);
        intent.putExtra(INDIVIDUAL_NUMBER, contents);
        startActivity(intent);

    }
}
