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
        String text_file_name = "example2.airline";
        File text_file = new File(text_file_name);
        if(!text_file.exists()) {
            Writer writer = new FileWriter(text_file_name);
            writer.write("Airline 1\n");
            writer.write("42 PDX 12/15/2017 12:39 am AMS 12/16/2017 9:25 am\n");
            writer.write("43 PDX 1/15/2017 12:39 am AMS 1/16/2017 12:25 pm\n");
            writer.flush();
            writer.close();
        }

        String xml_file_name = "example2.xml";
        TextParser tp = new TextParser(text_file_name);
        Airline air_save = tp.parse();
        XmlDumper xd = new XmlDumper(xml_file_name);
        xd.dump(air_save);

        XmlParser par = new XmlParser(xml_file_name);
        Airline airline = par.parse();
        Collection<Flight> flights = airline.getFlights();
        assertThat(airline.getName(), equalTo("Airline 1"));
        assertThat(flights.size(), equalTo(2));
    }
}
