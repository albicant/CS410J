package edu.pdx.cs410j.sytov.airlineandroidapp;

import edu.pdx.cs410J.AirlineDumper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;

/**
 * TextDumper class implements AirlineDumper class.
 */
public class TextDumper implements AirlineDumper<Airline> {

    /**
     * @param file_name - The name of the provided file.
     * @param err - an instance of the PrintWriter class, used to check errors.
     */
    private String file_name;
    private File dir;
    private static PrintWriter err;

    /**
     * Constructor, initialises file_name and file variables.
     * @param name The name of the provided file.
     */
    public TextDumper(File dir, String name) {
        this.file_name = name;
        this.dir = dir;
    }

    /**
     * Saves airline into the file.
     * @param airline is an instance of the Airline class to be written to the file
     * @throws IOException
     */
    public void dump(Airline airline) {
        err = new PrintWriter(System.err, true);

//        if(airline == null) {
//            System.err.println("Airline does not exist. Error saving it into the file!");
//            throw new IOException();
//        }
        try {
            File file = new File(this.dir, this.file_name);
            Writer writer = new FileWriter(file);
            if (airline == null) {
                writer.write("");
            }
            else {
                String airline_name = airline.getName();
                writer.write(airline_name + "\n");

                Collection<Flight> flights = airline.getFlights();
                for (Flight flight : flights) {
                    int number = flight.getNumber();
                    String src = flight.getSource();
                    String depart = flight.getDepartureToSave();
                    String dest = flight.getDestination();
                    String arrive = flight.getArrivalToSave();

                    writer.write(Integer.toString(number) + " ");
                    writer.write(src + " ");
                    writer.write(depart + " ");
                    writer.write(dest + " ");
                    writer.write(arrive + "\n");
                }
            }

            writer.flush();
            writer.close();
        } catch(IOException ex) {
            err.println("Cannot write airline to the file \"" + this.file_name + "\"");
        }

    }
}
