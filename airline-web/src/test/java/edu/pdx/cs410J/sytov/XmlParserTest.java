package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.ParserException;
import org.junit.Test;

import java.io.*;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class XmlParserTest {

    @Test
    public void xmlParserReadsFromXmlString() throws ParserException, IOException {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        XmlDumper dumper = new XmlDumper(pw);

        Airline airline = new Airline("Airline 1");
        Flight flight = new Flight(42, "pdx", "12/15/2017 10:39 am", "AMS", "12/16/2017 9:25 am");
        airline.addFlight(flight);
        Flight flight2 = new Flight(43, "pdx", "3/15/2017 10:39 am", "AMS", "3/16/2017 9:25 am");
        airline.addFlight(flight2);
        dumper.dump(airline);

        String xml = sw.toString();

        XmlParser par = new XmlParser(xml);
        Airline newAirline = par.parse();
        Collection<Flight> flights = newAirline.getFlights();
        assertThat(airline.getName(), equalTo("Airline 1"));
        assertThat(flights.size(), equalTo(2));
    }
}
