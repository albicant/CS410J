package edu.pdx.cs410j.sytov.airlineandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import edu.pdx.cs410J.ParserException;

public class MainActivity extends AppCompatActivity {

    private Airline airline;
//    private EditText et_airline_name;

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

        setContentView(R.layout.activity_main);
    }

    public void goToAddFlightMenu(View view) {
        if (this.airline == null) {
            setContentView(R.layout.create_airline);
        }
        else {
            setContentView(R.layout.add_flight);
            TextView airline_name = findViewById(R.id.airline_name);
            airline_name.setText("\"" + this.airline.getName() + "\"");
//            this.et_airline_name.setEnabled(false);
//            this.et_airline_name.setInputType(InputType.TYPE_NULL);
        }
    }

    public void inputAirlineName(View view) {
        EditText et_airline_name = findViewById(R.id.airlineName);
        String airline_name = et_airline_name.getText().toString();
        if (!airline_name.equals("") && airline_name != null) {
            this.createAirline(airline_name);
        }
        else {
            this.displayErrorMessage(view, "Invalid airline name");
//            Snackbar.make(view, "Invalid airline name", 10000).show();
        }

        setContentView(R.layout.activity_main);

//        EditText new_airline_name = findViewById(R.id.newAirlineName);
//        new_airline_name.setText(this.airline.getName());
    }

    private void displayErrorMessage(View view, String msg) {
        Snackbar.make(view, msg, 10000).show();
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
        }
        // else display error
        // add View view to the function argument
        // Snackbar.make(view, "I was clicked", 1000).show();
    }

    public void addFlightToAirline(View view) {
        EditText et_number = findViewById(R.id.enter_flight_number);
        EditText et_src = findViewById(R.id.enter_src);
        EditText et_depart = findViewById(R.id.enter_depart);
        EditText et_dest = findViewById(R.id.enter_dest);
        EditText et_arrive = findViewById(R.id.enter_arrive);

        String str_number = et_number.getText().toString();
        int number = 0;
        try {
            number = Integer.parseInt(str_number);
        } catch (Exception e) {
            // popup error message
        }
        String src = et_src.getText().toString();
        String depart = et_depart.getText().toString();
        String dest = et_dest.getText().toString();
        String arrive = et_arrive.getText().toString();
        // add verification for each variable

        Flight flight = null;
        try {
            flight = new Flight(number, src, depart, dest, arrive);
        }
        catch (Exception e) {
            // popup error message
        }
        if (flight != null) {
            this.airline.addFlight(flight);
        }

        setContentView(R.layout.activity_main);
        //popup sucsess message
    }

//    private boolean verifyArgument() {
//
//    }

    public void addFlight(int number, String src, String depart, String dest, String arrive) throws IllegalArgumentException {
        Flight flight = new Flight(number, src, depart, dest, arrive);
        if (this.airline != null) {
            this.airline.addFlight(flight);
        }
        //else display error
//            Snackbar.make
    }


    public void printReadme(View view) {
        setContentView(R.layout.readme);
    }

    public void mainMenu(View view) {
        setContentView(R.layout.activity_main);
    }

    public void deleteAirline(View view) {
        this.airline = null;
        // popup an approrpiate message
    }

    public void searchFlights(View view) throws IOException {
        // change it later to search flights
        setContentView(R.layout.pretty_print);
        TextView tv = findViewById(R.id.prettyPrint);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        PrettyPrinter pp = new PrettyPrinter(pw);
        pp.dump(this.airline);

        tv.setText(sw.toString());
        tv.setMovementMethod(new ScrollingMovementMethod());
//        this.mainMenu(view);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        this.airMan = new Intent(this, AirlineManager.class);
//    }
//
//    public void printReadme(View view) {
//        setContentView(R.layout.readme);
//    }
//
//    public void mainMenu(View view) {
//        setContentView(R.layout.activity_main);
//    }
//
//    public void goToAddFlightMenu(View view) {
////        Intent intent = new Intent(this, AirlineManager.class);
//        startActivity(this.airMan);
//    }
}
