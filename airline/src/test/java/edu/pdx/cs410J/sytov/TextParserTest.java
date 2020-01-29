package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.ParserException;
import org.junit.Test;

import java.util.Collection;

public class TextParserTest {

    @Test
    public void airlineParserReadsFromFile() throws ParserException {
        String file_name = "example1.txt";
        TextParser par = new TextParser(file_name);
        Airline airline = par.parse();
        System.out.println(airline.getName());
        Collection<Flight> flights = airline.getFlights();
        for (Flight flight : flights) {
            System.out.println(flight.toString());
        }
    }
}
