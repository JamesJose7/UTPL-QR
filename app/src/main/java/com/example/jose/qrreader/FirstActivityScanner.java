package com.example.jose.qrreader;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import SampleDataBase.Individual;
import SampleDataBase.IndividualCreator;


public class FirstActivityScanner extends ActionBarActivity {

    public IndividualCreator creator = new IndividualCreator();

    private TextView nameTextView;
    private TextView backgroundTextview;
    private TextView idTextView;
    private TextView plateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_activity_scanner);

        Individual[] individuals = creator.getIndividuals();

        nameTextView = (TextView) findViewById(R.id.nameTextView);
        backgroundTextview = (TextView) findViewById(R.id.backgroundTextView);
        idTextView = (TextView) findViewById(R.id.idTextView);
        plateTextView = (TextView) findViewById(R.id.plateTextView);


        Intent intent = getIntent();
        String contents = intent.getStringExtra(MainActivity.INDIVIDUAL_NUMBER);
        int individualNumber = Integer.parseInt(contents);
        setData(individuals[individualNumber]);

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
        backgroundTextview.setText(individual.getBackground());
        idTextView.setText(individual.getID() + "");
        plateTextView.setText(individual.getPlate());

    }
}
