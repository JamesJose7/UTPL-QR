package com.example.jose.qrreader;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import SampleDataBase.Individual;
import SampleDataBase.IndividualCreator;


public class FirstActivityScanner extends ActionBarActivity {

    public IndividualCreator mCreator = MainActivity.mCreator;

    Individual[] mIndividuals;

    private TextView nameTextView;
    private TextView infraccionesTextView;
    private TextView plateTextView;
    private Button agregarInfracciones;
    private LinearLayout infraccionesLayout;

    private int individualNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_activity_scanner);

        mIndividuals = mCreator.getIndividuals();

        infraccionesLayout = (LinearLayout) findViewById(R.id.infraccionesLayout);

        nameTextView = (TextView) findViewById(R.id.nameTextView);
        infraccionesTextView = (TextView) findViewById(R.id.infraccionesTextView);
        plateTextView = (TextView) findViewById(R.id.plateTextView);
        agregarInfracciones = (Button) findViewById(R.id.agregarInfracciones);


        agregarInfracciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infraccionesLayout.setVisibility(View.VISIBLE);
            }
        });

        infraccionesLayout.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        String contents = intent.getStringExtra(MainActivity.INDIVIDUAL_NUMBER);
        individualNumber = Integer.parseInt(contents);
        setData(mIndividuals[individualNumber]);

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
        }

    }

    public void onClick(View view) {

        String nuevosAntecedentes = mIndividuals[individualNumber].getNuevosAntecedentes();

        switch (view.getId()) {
            case R.id.semaforoEnRojo:
                nuevosAntecedentes += "Semaforo en rojo\n";
                mIndividuals[individualNumber].setNuevosAntecedentes(nuevosAntecedentes);
                mIndividuals[individualNumber].setBackground("Antecedentes");
                break;

            case R.id.noEstacionar:
                nuevosAntecedentes += "No estacionar\n";
                mIndividuals[individualNumber].setNuevosAntecedentes(nuevosAntecedentes);
                mIndividuals[individualNumber].setBackground("Antecedentes");
                break;
            default:
                break;

        }

        infraccionesLayout.setVisibility(View.INVISIBLE);

        setData(mIndividuals[individualNumber]);
    }
}
