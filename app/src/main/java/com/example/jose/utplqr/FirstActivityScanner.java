package com.example.jose.utplqr;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import SampleDataBase.Individual;
import SampleDataBase.IndividualCreator;


public class FirstActivityScanner extends Activity {

    public IndividualCreator mCreator = MainActivity.mCreator;

    Individual[] mIndividuals;
    Individual mCurrentIndividual;

    private TextView nameTextView;
    private TextView infraccionesTextView;
    private TextView registerDates;
    private TextView plateTextView;
    private LinearLayout infraccionesLayout;

    private int individualNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_activity_scanner);

        Intent intent = getIntent();
        String contents = intent.getStringExtra(MainActivity.INDIVIDUAL_NUMBER);
        individualNumber = Integer.parseInt(contents);

        mIndividuals = mCreator.getIndividuals();
        mCurrentIndividual = mIndividuals[individualNumber];


        //Views assignment

        infraccionesLayout = (LinearLayout) findViewById(R.id.infraccionesLayout);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        infraccionesTextView = (TextView) findViewById(R.id.infraccionesTextView);
        registerDates = (TextView) findViewById(R.id.fechaRegistro);
        plateTextView = (TextView) findViewById(R.id.plateTextView);
        Button agregarInfracciones = (Button) findViewById(R.id.agregarInfracciones);

        //Add infractions button
        agregarInfracciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infraccionesLayout.setVisibility(View.VISIBLE);
            }
        });

        infraccionesLayout.setVisibility(View.INVISIBLE);


        setData(mCurrentIndividual);

    }


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

    public void setData(Individual individual) {


        nameTextView.setText(individual.getName());
        plateTextView.setText(individual.getPlate());

        if(individual.getBackground().equals(IndividualCreator.NO_ANTECEDENTES)) {
            infraccionesTextView.setText("Antecedentes\n" +
                                        individual.getBackground());
        } else {
            infraccionesTextView.setText(individual.getNuevosAntecedentes());
            registerDates.setText(individual.getDates());

        }

    }

    public void onClick(View view) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM | hh:mm a");
        Date date = new Date();

        String nuevosAntecedentes = mCurrentIndividual.getNuevosAntecedentes();
        String dates = mCurrentIndividual.getDates();

        //Set Dates
        dates += formatter.format(date) + "\n";
        mCurrentIndividual.setDates(dates);

        switch (view.getId()) {
            case R.id.semaforoEnRojo:
                nuevosAntecedentes += "Semaforo en rojo\n";
                mCurrentIndividual.setNuevosAntecedentes(nuevosAntecedentes);
                mCurrentIndividual.setBackground("Antecedentes");
                break;

            case R.id.noEstacionar:
                nuevosAntecedentes += "No estacionar\n";
                mCurrentIndividual.setNuevosAntecedentes(nuevosAntecedentes);
                mCurrentIndividual.setBackground("Antecedentes");
                break;
            default:
                break;

        }

        infraccionesLayout.setVisibility(View.INVISIBLE);

        setData(mCurrentIndividual);
    }
}
