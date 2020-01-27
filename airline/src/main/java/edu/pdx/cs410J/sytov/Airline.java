package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AbstractAirline;

import java.util.Collection;
import java.util.HashSet;

/**
 * Airline class implements AbstractAirline class.
 */
public class Airline<T extends AbstractFlight> extends AbstractAirline<T> {

    /**
     *  @param name is the Airline name
     *  @param flights it the collection of flights represented as the HashSet of Flight class.
     */
    private final String name;
    private Collection<T> flights;

    /**
     * Class constructor. Creates an instance of the Airline class.
     * Assigns name to the airline and initializes the collection of Flights as a hashset.
     */
    public Airline(String name) {
        this.name = name;
        this.flights = new HashSet<T>();
    }

    /**
     * Returns the name of the airline.
     */
    @Override
    public String getName() {

        return this.name;
    }

    /**
     * Adds a new Flight to the collection of Flights
     */
    @Override
    public void addFlight(T flight) {
        this.flights.add(flight);
    }

    /**
     * Returns the collection of Flights within the Airline.
     */
    @Override
    public Collection<T> getFlights() {
        return this.flights;
    }
}
