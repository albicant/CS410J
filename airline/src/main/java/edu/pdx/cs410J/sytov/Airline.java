package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Airline class implements AbstractAirline class.
 */
public class Airline extends AbstractAirline<Flight> {

    /**
     *  @param name is the Airline name
     *  @param flights it the collection of flights represented as the HashSet of Flight class.
     */
    private final String name;
    private Collection<Flight> flights;

    /**
     * Class constructor. Creates an instance of the Airline class.
     * Assigns name to the airline and initializes the collection of Flights as a hashset.
     */
    public Airline(String name) {
        this.name = name;
        this.flights = new ArrayList<>();
    }

    /**
     * Returns the name of the airline.
     */
    @Override
    public String getName() {

        return this.name;
    }

    public boolean checkIfCollectionContainsFlights(Flight flight) {
        for(Flight fl : this.flights) {
            if(fl.getNumber() == flight.getNumber() && fl.getSource().equals(flight.getSource()) &&
               fl.getDepartureString().equals(flight.getDepartureString()) &&
               fl.getDestination().equals(flight.getDestination()) &&
               fl.getArrivalString().equals(flight.getArrivalString())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a new Flight to the collection of Flights
     */
    @Override
    public void addFlight(Flight flight) {
        if(!this.checkIfCollectionContainsFlights(flight))
            this.flights.add(flight);
    }

    /**
     * Returns the collection of Flights within the Airline.
     */
    @Override
    public Collection<Flight> getFlights() {
        return this.flights;
    }
}
