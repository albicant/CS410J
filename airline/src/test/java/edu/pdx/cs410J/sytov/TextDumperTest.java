package edu.pdx.cs410J.sytov;

import org.junit.Test;

public class TextDumperTest {

    @Test
    public void textDumperCanCreateSimpleFile() {
        String file_name = "example1.txt";
        TextDumper dumper = new TextDumper(file_name);

        Airline airline = new Airline("Airline 1");
        Flight flight = new Flight(42, "pdx", "3/15/2017 10:39", "AMS", "3/16/2017 9:25");
        airline.addFlight(flight);
        Flight flight2 = new Flight(43, "pdx", "3/15/2017 10:39", "AMS", "3/16/2017 9:25");
        airline.addFlight(flight2);
        try {
            dumper.dump(airline);
        } catch (Exception e) {
            System.err.println(e);
        }

    }
}
