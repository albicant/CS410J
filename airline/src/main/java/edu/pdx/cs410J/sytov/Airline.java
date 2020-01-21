package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AbstractAirline;

import java.util.Collection;

public class Airline<T extends AbstractFlight> extends AbstractAirline<T> {

//    private Collection<T> flights;

    @Override
    public String getName() { return "No name"; }

    @Override
    public void addFlight(T flight) {
//        this.flights.add(flight);
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    @Override
    public Collection<T> getFlights() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
}
