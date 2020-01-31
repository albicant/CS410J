//package edu.pdx.cs410J.sytov;
//
//import edu.pdx.cs410J.ParserException;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.IOException;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.MatcherAssert.assertThat;
//
///**
// * Unit tests for the {@link TextDumper} class.
// */
//public class TextDumperTest {
//
//    @Test
//    public void textDumperCanCreateSimpleFile() throws IOException {
//        String file_name = "example1.txt";
//        TextDumper dumper = new TextDumper(file_name);
//
//        Airline airline = new Airline("Airline 1");
//        Flight flight = new Flight(42, "pdx", "3/15/2017 10:39", "AMS", "3/16/2017 9:25");
//        airline.addFlight(flight);
//        Flight flight2 = new Flight(43, "pdx", "3/15/2017 10:39", "AMS", "3/16/2017 9:25");
//        airline.addFlight(flight2);
//        dumper.dump(airline);
//
//        File file = new File(file_name);
//        assertThat(file.exists(), equalTo(true));
//    }
//}
