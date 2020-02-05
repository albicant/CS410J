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
     * @param file_name - The name of the provided file.
     * @param err - an instance of the PrintWriter class, used to check errors.
     */
    private String file_name;
    private static PrintWriter err;
    private AirportNames air_names;

    /**
     * Constructor, initialises file_name and file variables.
     * @param name The name of the provided file.
     */
    public PrettyPrinter(String name) {
        this.file_name = name;
    }

    /**
     * Creates a nicely-formatted textual presentation of an airline's flights.
     * @param airline is an instance of the Airline class to be written to the file
     * @throws IOException
     */
    public void dump(Airline airline) throws IOException {
        err = new PrintWriter(System.err, true);

        if(airline == null) {
            System.err.println("Airline does not exist. Error saving it into the file!");
            throw new IOException();
        }
        try {
            Writer writer = new FileWriter(this.file_name);
            String airline_name = airline.getName();

            ArrayList<Flight> flights = new ArrayList<>(airline.getFlights());
            int number_of_flights = flights.size();

            writer.write("Airline \"" + airline_name + "\" contains " + number_of_flights + " flight");
            if(number_of_flights > 1) {
                writer.write("s");
            }
            writer.write(".\n");

            int counter = 1;
            for(Flight flight : flights) {
                int number = flight.getNumber();
                String src = flight.getSource();
                Date depart = flight.getDeparture();
                String dest = flight.getDestination();
                Date arrive = flight.getArrival();
                long duration = arrive.getTime() - depart.getTime();
                duration = duration / (1000 * 60);

                writer.write("\n------------------------------------- " + counter + " -------------------------------------\n");
                writer.write("The Flight number is " + number + ".\n");
                writer.write("The Flight departs from " + this.air_names.getName(src) + " at ");
                writer.write(depart + ".\n");

                writer.write("The Flight arrives at " + this.air_names.getName(dest) + " at ");
                writer.write(arrive + ".\n");
                writer.write("The Flight's duration is " + duration + " minutes.\n");

                ++counter;
            }

            writer.flush();
            writer.close();
        } catch(IOException ex) {
            err.println("Cannot write airline to the file \"" + this.file_name + "\"");
        }

    }
}
