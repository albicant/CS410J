package edu.pdx.cs410J.sytov;

import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unit tests for the {@link Airline} class.
 */
public class AirlineTest {

    public Airline airline = new Airline("Delta");

    @Test
    public void airlineHasExpectedName() {
        String name = "Delta";
        assertThat(this.airline.getName(), equalTo(name));
    }

    @Test
    public void airlineHasExpectedFlight() {

        Flight flight = new Flight(42, "pdx", "3/15/2017 10:39", "AMS", "3/16/2017 9:25");
        this.airline.addFlight(flight);
        Collection<Flight> flights = this.airline.getFlights();
        assertThat(flights.contains(flight), equalTo(true));
    }

}