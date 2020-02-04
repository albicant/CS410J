package edu.pdx.cs410J.sytov;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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

    @Test (expected = IllegalArgumentException.class)
    public void airlineAddsFlightWithArrivalTimeMalformatted() {
        Flight flight = new Flight(42, "pdx", "3/15/2017 10:39 am", "AMS", "3/16/20/17 9:25 pm");
        this.airline.addFlight(flight);
    }

    @Test
    public void airlineHasExpectedFlight() {
        Flight flight = new Flight(42, "pdx", "3/15/2017 10:39 am", "AMS", "3/16/2017 9:25 pm");
        this.airline.addFlight(flight);
        Collection<Flight> flights = this.airline.getFlights();
        assertThat(flights.contains(flight), equalTo(true));
    }

    @Test
    public void airlineSortsFlights() {
        Airline air = new Airline("Test air");
        Flight f1 = new Flight(42, "pdx", "3/15/2017 10:39 am", "AMS", "3/16/2017 9:25 pm");
        Flight f2 = new Flight(55, "AAA", "3/15/2017 10:39 am", "BBB", "3/16/2017 9:25 pm");
        Flight f3 = new Flight(2, "pdx", "3/15/2017 11:39 am", "AMS", "3/16/2017 9:25 pm");
        air.addFlight(f1);
        air.addFlight(f2);
        air.addFlight(f3);
        ArrayList<Flight> flights = new ArrayList<>(air.getFlights());
        Collections.sort(flights);
        for( Flight fl : flights) {
            System.out.println(fl.toString());
        }
    }

}
