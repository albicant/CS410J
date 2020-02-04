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

/**
 * Unit tests for the {@link TextParser} class.
 */
public class TextParserTest {

    @Test
    public void airlineParserReadsFromFile() throws ParserException, IOException {
        String file_name = "example1.txt";
        File file = new File(file_name);
        if(!file.exists()) {
            Writer writer = new FileWriter(file_name);
            writer.write("Airline 1\n");
            writer.write("42 PDX 3/15/2017 10:39 am AMS 3/16/2017 9:25 am\n");
            writer.write("43 PDX 3/15/2017 10:39 am AMS 3/16/2017 9:25 am\n");
            writer.flush();
            writer.close();
        }

        TextParser par = new TextParser(file_name);
        Airline airline = par.parse();
        Collection<Flight> flights = airline.getFlights();
        assertThat(airline.getName(), equalTo("Airline 1"));
        assertThat(flights.size(), equalTo(2));
    }
}
