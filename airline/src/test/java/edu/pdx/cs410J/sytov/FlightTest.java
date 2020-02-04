package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.ParserException;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Flight} class.
 */
public class FlightTest {

  public Flight flight = new Flight(42, "pdx", "3/15/2017 10:39 am", "AMS", "3/16/2017 9:25 pm");

  @Test
  public void flightHasExpectedNumber() {
    int flight_number = 42;
    assertThat(this.flight.getNumber(), equalTo(flight_number));
  }

  @Test
  public void flightHasExpectedSource() {
    String src = "PDX";
    assertThat(this.flight.getSource(), equalTo(src));
  }

  @Test
  public void flightHasExpectedDeparture() {
    String depart = "3/15/17, 10:39 AM";
    assertThat(this.flight.getDepartureString(), equalTo(depart));
  }

  @Test
  public void flightHasExpectedDest() {
    String dest = "AMS";
    assertThat(this.flight.getDestination(), equalTo(dest));
  }

  @Test
  public void flightHasExpectedArrival() {
    String arrive = "3/16/17, 9:25 PM";
    assertThat(this.flight.getArrivalString(), equalTo(arrive));
  }

  @Test
  public void flightValidatesTimeTrue() {
    String time = "3/16/2017 9:25 pm";
    assertThat(this.flight.validateTime(time), equalTo(true));
  }

  @Test
  public void flightValidatesTimeFalse() {
    String time = "3/16/20/17 9:25 pm";
    assertThat(this.flight.validateTime(time), equalTo(false));
  }

  @Test
  public void flightDateTest1() throws ParseException {
    String d = "01/02/2020 9:16 pm";
    String pattern = "MM/dd/yyyy hh:mm a";
//    SimpleDateFormat sdf = new SimpleDateFormat(pattern);

    Date date = new SimpleDateFormat(pattern).parse(d);

    DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.US);
    System.out.println(dateFormat.format(date));
    System.out.println(date);
  }
  
}
