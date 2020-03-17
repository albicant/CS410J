package edu.pdx.cs410j.sytov.airlineandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;

public class MainActivity extends AppCompatActivity {

    private Airline airline;
    private AirportNames air_names;
//    private EditText et_airline_name;

    private boolean verifyAirportCode(String code) {
        if(this.air_names.getName(code) == null) {
            return false;
        }
        return true;
    }

    private boolean verifyDateTime(String time) {
        if (!time.matches("[/: 0-9]+(am|pm)$"))
            return false;
        String[] tokens = time.split(" ");
        if (tokens.length != 3)
            return false;
        String[] tokens_date = tokens[0].split("/");
        String[] tokens_time = tokens[1].split(":");
        if(tokens_date.length != 3 || tokens_time.length != 2)
            return false;
        if(tokens_date[0].length() < 1 || tokens_date[0].length() > 2 ||
                tokens_date[1].length() < 1 || tokens_date[1].length() > 2 ||
                tokens_date[2].length() != 4 ||
                tokens_time[0].length() < 1 || tokens_time[0].length() > 2 ||
                tokens_time[1].length() != 2) {
            return false;
        }
        if(Integer.parseInt(tokens_date[0]) < 1 || Integer.parseInt(tokens_date[0]) > 12 ||
                Integer.parseInt(tokens_date[1]) < 1 || Integer.parseInt(tokens_date[1]) > 31 ||
                Integer.parseInt(tokens_date[2]) < 1900 ||
                Integer.parseInt(tokens_time[0]) < 1 || Integer.parseInt(tokens_time[0]) > 12 ||
                Integer.parseInt(tokens_time[1]) < 0 || Integer.parseInt(tokens_time[1]) > 59) {
            return false;
        }
        if(!tokens[2].equals("am") && !tokens[2].equals("pm"))
            return false;

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        }
    }

    public void inputAirlineName(View view) {
        EditText et_airline_name = findViewById(R.id.airlineName);
        String airline_name = et_airline_name.getText().toString();
        if (!airline_name.equals("") && airline_name != null) {
            this.createAirline(view, airline_name);
            setContentView(R.layout.activity_main);
        }
        else {
            this.displayPopupMessage(view, "Invalid airline name");
        }

    }

    private void displayPopupMessage(View view, String msg) {
        Snackbar.make(view, msg, 10000).show();
    }

    @Override
    protected void onStop() {
        File dir = getFilesDir();
        TextDumper td = new TextDumper(dir,"airline.txt");
        td.dump(this.airline);
        super.onStop();
    }

    private void createAirline(View view, String airline_name) {
        if (this.airline == null) {
            this.airline = new Airline(airline_name);
            this.displayPopupMessage(view, "You have successfully created the airline.");
        }
        else {
            this.displayPopupMessage(view, "Cannot create the airline. An airline already exist!");
        }
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
            displayPopupMessage(view, "Flight number must be an integer!");
            return;
        }
        String src = et_src.getText().toString().toUpperCase();
        if (!verifyAirportCode(src)) {
            displayPopupMessage(view, "Invalid airport source code!");
            return;
        }
        String depart = et_depart.getText().toString();
        if (!verifyDateTime(depart)) {
            displayPopupMessage(view, "Invalid departure time! Time must use the following format: MM/dd/yyyy hh:mm a");
            return;
        }
        String dest = et_dest.getText().toString().toUpperCase();
        if (!verifyAirportCode(dest)) {
            displayPopupMessage(view, "Invalid airport destination code!");
            return;
        }
        String arrive = et_arrive.getText().toString();
        if (!verifyDateTime(arrive)) {
            displayPopupMessage(view, "Invalid arrival time! Time must use the following format: MM/dd/yyyy hh:mm a");
            return;
        }

        Flight flight = null;
        try {
            flight = new Flight(number, src, depart, dest, arrive);
        }
        catch (Exception e) {
            displayPopupMessage(view, "Cannot create the flight.");
            return;
        }
        if (flight != null) {
            this.airline.addFlight(flight);
        }

        displayPopupMessage(view, "You have successfully added the flight #" + str_number + " to airline \"" + this.airline.getName() + "\".");
        setContentView(R.layout.activity_main);
    }

    public void printReadme(View view) {
        setContentView(R.layout.readme);
        TextView tv = findViewById(R.id.printReadMe);
        String str = "This project provides the following functionality:\n\n" +
                "-\"HELP\" prints the README.\n\n" +
                "-\"ADD A FLIGHT\" allows the user to create a new airline or create a new flight.\n\n" +
                "-\"SEARCH FLIGHTS\" allows the user to display flights that start and end at the certain airports. If no airports are provided, all of the airline's flights will be displayed.\n\n" +
                "-\"DELETE AIRLINE\" allows the user to delete the airline.";
        tv.setText(str);
    }

    public void mainMenu(View view) {
        setContentView(R.layout.activity_main);
    }

    public void deleteAirline(View view) {
        if (this.airline == null) {
            displayPopupMessage(view, "The airline you are trying to delete does not exist.");
            return;
        }
        String name = this.airline.getName();
        this.airline = null;
        displayPopupMessage(view, "You have successfully deleted \"" + name + "\" airline.");
    }

    public void goToSearchFlightsMenu(View view) throws IOException {
        if(this.airline == null) {
            prettyPrintFlights(view, null);
        }
        else
            setContentView(R.layout.search_flights);
    }

    public void searchFlights(View view) throws IOException {
        EditText et_src = findViewById(R.id.search_src);
        EditText et_dest = findViewById(R.id.search_dest);
        String src = et_src.getText().toString().toUpperCase();
        String dest = et_dest.getText().toString().toUpperCase();

        if ((src == null || src.equals("")) && (dest == null || dest.equals(""))) {
            displayPopupMessage(view, "No airports were specified, all flights have been displayed.");
            this.prettyPrintFlights(view, this.airline);
        }
        else if (src == null || src.equals("")) {
            this.displayPopupMessage(view, "Source has to be a 3 letter airport code");
        }
        else if (dest == null || dest.equals("")) {
            this.displayPopupMessage(view, "Destination has to be a 3 letter airport code");
        }
        else {
            if(!verifyAirportCode(src)) {
                displayPopupMessage(view, "Invalid airport source code!");
                return;
            }
            if(!verifyAirportCode(dest)) {
                displayPopupMessage(view, "Invalid airport destination code!");
                return;
            }
            Airline air = constructAirlineSearch(src, dest);
            this.prettyPrintFlights(view, air);
        }

    }

    private Airline constructAirlineSearch(String src, String dest) {
        if(this.airline == null) {
            return null;
        }
        Collection<Flight> flights = this.airline.getFlights();
        Airline air = new Airline(this.airline.getName());
        for(Flight flight : flights) {
            if(flight.getSource().equals(src) && flight.getDestination().equals(dest)) {
                air.addFlight(flight);
            }
        }
        return air;

    }

    public void prettyPrintFlights(View view, Airline air) throws IOException {
        setContentView(R.layout.pretty_print);
        TextView tv = findViewById(R.id.prettyPrint);

        if(this.airline == null) {
            tv.setText("The airline does not exist. Go to the \"Add a flight\" menu to create an airline.");
            return;
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        PrettyPrinter pp = new PrettyPrinter(pw);
        pp.dump(air);

        tv.setText(sw.toString());
        tv.setMovementMethod(new ScrollingMovementMethod());
    }

}
