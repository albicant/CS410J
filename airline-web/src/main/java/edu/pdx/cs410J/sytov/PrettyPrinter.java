package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.AirlineDumper;
import edu.pdx.cs410J.AirportNames;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;

/**
 * PrettyPrinter class implements AirlineDumper class.
 */
public class PrettyPrinter implements AirlineDumper<Airline> {

    /**
     * @param err - an instance of the PrintWriter class, used to check errors.
     */
    private static PrintWriter err;
    private AirportNames air_names;


    /**
     * Creates a nicely-formatted textual presentation of an airline's flights or prints it to the standard output.
     * @param airline is an instance of the Airline class to be written to the file
     * @throws IOException
     */
    public void dump(Airline airline) throws IOException {
        err = new PrintWriter(System.err, true);

        if(airline == null) {
            System.err.println("Airline does not exist. Error saving it into the file!");
            throw new IOException();
        }

        String airline_name = airline.getName();

        ArrayList<Flight> flights = new ArrayList<>(airline.getFlights());
        int number_of_flights = flights.size();

        System.out.print("Airline \"" + airline_name + "\" contains " + number_of_flights + " flight");
        if(number_of_flights > 1) {
            System.out.print("s");
        }
        System.out.print(".\n");

        int counter = 1;
        for(Flight flight : flights) {
            // add checks for the -search option. Use src and dest for it
            int number = flight.getNumber();
            String src = flight.getSource();
            Date depart = flight.getDeparture();
            String dest = flight.getDestination();
            Date arrive = flight.getArrival();
            long duration = arrive.getTime() - depart.getTime();
            duration = duration / (1000 * 60);

            System.out.print("\n------------------------------------- " + counter + " -------------------------------------\n");
            System.out.print("The Flight number is " + number + ".\n");
            System.out.print("The Flight departs from " + this.air_names.getName(src) + " on ");
            System.out.print(depart + ".\n");

            System.out.print("The Flight arrives at " + this.air_names.getName(dest) + " on ");
            System.out.print(arrive + ".\n");
            System.out.print("The Flight's duration is " + duration + " minutes.\n");

            ++counter;
        }

    }
}
