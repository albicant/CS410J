package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.ParserException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class XmlParserTest {

    @Test
    public void xmlParserReadsFromFile() throws ParserException, IOException {
        String file_name = "valid-airline.xml";
        File file = new File(file_name);
//        if(!file.exists()) {
//            Writer writer = new FileWriter(file_name);
//            writer.write("Airline 1\n");
//            writer.write("42 PDX 3/15/2017 10:39 am AMS 3/16/2017 9:25 am\n");
//            writer.write("43 PDX 3/15/2017 10:39 am AMS 3/16/2017 9:25 am\n");
//            writer.flush();
//            writer.close();
//        }

        XmlParser par = new XmlParser(file_name);
        Airline airline = par.parse();
//        String name = airline.getName();
        Collection<Flight> flights = airline.getFlights();
        assertThat(airline.getName(), equalTo("Valid Airlines"));
        assertThat(flights.size(), equalTo(2));
    }
}
