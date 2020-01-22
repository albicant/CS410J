package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AbstractAirline;

//import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Airline<T extends AbstractFlight> extends AbstractAirline<T> {

    private final String name;
    private Collection<T> flights;

    public Airline(String name) {
        this.name = name;
        this.flights = new HashSet<T>();
    }

    @Override
    public String getName() {

        return this.name;
//        return "No name";
    }

    @Override
    public void addFlight(T flight) {
//        if (!this.flights.contains(flight))
        this.flights.add(flight);
//        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    @Override
    public Collection<T> getFlights() {

        return this.flights;
//        throw new UnsupportedOperationException("This method is not implemented yet");
    }
}
