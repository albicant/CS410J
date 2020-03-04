package edu.pdx.cs410J.sytov;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PrettyPrinter} class.
 */
public class PrettyPrinterTest {

    @Test
    public void prettyPrinterCanPrettyPrintAirline() throws IOException {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        PrettyPrinter pp = new PrettyPrinter(pw);

        Airline airline = new Airline("Airline 1");
        Flight flight = new Flight(42, "pdx", "3/15/2017 10:39 am", "AMS", "3/16/2017 9:25 am");
        airline.addFlight(flight);
        Flight flight2 = new Flight(43, "pdx", "3/15/2017 10:39 am", "AMS", "3/16/2017 9:25 am");
        airline.addFlight(flight2);
        pp.dump(airline);

        String str = sw.toString();
        assertThat(str, containsString("Airline 1"));
    }
}
