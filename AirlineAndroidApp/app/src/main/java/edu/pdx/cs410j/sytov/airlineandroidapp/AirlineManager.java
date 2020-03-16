package edu.pdx.cs410j.sytov.airlineandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.view.View.OnKeyListener;
import android.view.View;
import android.view.KeyEvent;

import java.io.File;

import edu.pdx.cs410J.ParserException;

public class AirlineManager extends AppCompatActivity {

    private Airline airline;
    private EditText et_airline_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.create_airline);

        File dir = getFilesDir();
        TextParser tp = new TextParser(dir,"airline.txt");
        if (tp.checkFileExistence()) {
            try {
                this.airline = tp.parse();
            } catch (ParserException e) {
                e.printStackTrace();
            }
        }

        this.et_airline_name = findViewById(R.id.airlineName);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (this.airline == null) {
            setContentView(R.layout.create_airline);
        }
        else {
            setContentView(R.layout.add_flight);
        }
    }

    public void inputAirlineName(View view) {
        String airline_name = this.et_airline_name.getText().toString();
        this.createAirline(airline_name);

//        EditText new_airline_name = findViewById(R.id.newAirlineName);
//        new_airline_name.setText(this.airline.getName());
    }

    @Override
    protected void onStop() {
        File dir = getFilesDir();
        TextDumper td = new TextDumper(dir,"airline.txt");
        td.dump(this.airline);
        super.onStop();
    }

    private void createAirline(String airline_name) {
        if (this.airline == null) {
            this.airline = new Airline(airline_name);
            Log.i("Airline name", "Airline name = " + this.airline.getName());
        }
        // else display error
    }

    public void addFlight(int number, String src, String depart, String dest, String arrive) throws IllegalArgumentException {
        Flight flight = new Flight(number, src, depart, dest, arrive);
        if (this.airline != null) {
            this.airline.addFlight(flight);
        }
        //else display error
//            Snackbar.make
    }

    public void deleteAirline() {
        this.airline = null;
    }

}
