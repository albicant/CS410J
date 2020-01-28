package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.AirlineDumper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;

public class TextDumper implements AirlineDumper<Airline> {

    private String file_name;
    private static PrintWriter err;

    public TextDumper(String name) {
        this.file_name = name;
    }

    public void dump(Airline airline) throws IOException {
        err = new PrintWriter(System.err, true);

        try {
            Writer writer = new FileWriter(this.file_name);
            String airline_name = airline.getName();
            writer.write(airline_name + "\n");

            Collection<Flight> flights = airline.getFlights();
            for(Flight flight : flights) {
                int number = flight.getNumber();
                String src = flight.getSource();
                String depart = flight.getDepartureString();
                String dest = flight.getDestination();
                String arrive = flight.getArrivalString();

                writer.write(Integer.toString(number) + " ");
                writer.write(src + " ");
                writer.write(depart + " ");
                writer.write(dest + " ");
                writer.write(arrive + "\n");
            }

            writer.flush();
            writer.close();
        } catch(IOException ex) {
            err.println(ex + "Cannot write airline to the file \"" + this.file_name + "\"");
        }

    }
}
