package edu.pdx.cs410j.sytov.airlineandroidapp;

import edu.pdx.cs410J.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Airline class implements AbstractAirline class.
 */
public class Airline extends AbstractAirline<Flight> {

    /**
     *  @param name is the Airline name
     *  @param flights it the collection of flights represented as the HashSet of Flight class.
     */
    private final String name;
    private ArrayList<Flight> flights;

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

    /**
     * Checks if Flight contains the flight
     * @param flight is an instance of the Flight class
     * @return true if the flight already exists, false otherwise
     */
    private boolean checkIfCollectionContainsFlights(Flight flight) {
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
     * Adds a new Flight to the collection of Flights and sorts them
     */
    @Override
    public void addFlight(Flight flight) {
        if(!this.checkIfCollectionContainsFlights(flight)) {
            this.flights.add(flight);
            Collections.sort(this.flights);
        }
    }

    /**
     * Returns the collection of Flights within the Airline.
     */
    @Override
    public Collection<Flight> getFlights() {
        return this.flights;
    }
}
