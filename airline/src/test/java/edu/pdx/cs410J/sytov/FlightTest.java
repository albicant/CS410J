package edu.pdx.cs410J.sytov;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Flight} class.
 */
public class FlightTest {

  public Flight flight = new Flight(42, "pdx", "3/15/2017 10:39", "AMS", "3/16/2017 9:25");
  
//  @Test(expected = UnsupportedOperationException.class)
//  public void getArrivalStringNeedsToBeImplemented() {
//    this.flight.getArrivalString();
//  }
//
//  @Test
//  public void initiallyAllFlightsHaveTheSameNumber() {
//    assertThat(this.flight.getNumber(), equalTo(42));
//  }
//
//  @Test
//  public void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
//    assertThat(this.flight.getDeparture(), is(nullValue()));
//  }

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
    String depart = "3/15/2017 10:39";
    assertThat(this.flight.getDepartureString(), equalTo(depart));
  }

  @Test
  public void flightHasExpectedDest() {
    String dest = "AMS";
    assertThat(this.flight.getDestination(), equalTo(dest));
  }

  @Test
  public void flightHasExpectedArrival() {
    String arrive = "3/16/2017 9:25";
    assertThat(this.flight.getArrivalString(), equalTo(arrive));
  }
  
}
