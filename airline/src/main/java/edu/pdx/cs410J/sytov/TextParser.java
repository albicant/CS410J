package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TextParser implements AirlineParser<Airline> {

    private final String file_name;
    private final File file;

    public TextParser(String file_name) {
        this.file_name = file_name;
        this.file = new File(file_name);
    }

    public boolean checkFileExistence() {
        return this.file.exists();
    }

    private Flight createFlight(String str) {
        String[] args = str.split(" ");

        if(args.length < 7) {
            throw new IllegalArgumentException("Missing flight arguments.");
        }
        else if(args.length > 7) {
            throw new IllegalArgumentException("Unknown flight arguments.");
        }

        int number = 0;
        try {
            number = Integer.parseInt(args[0]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error: Cannot convert \'" + args[0] + "\' to type int!");
        }

        String src = args[1];
        String depart = args[2] + " " + args[3];
        String dest = args[4];
        String arrive = args[5] + " " + args[6];

        Flight flight;
        try {
            flight = new Flight(number, src, depart, dest, arrive);
        } catch (Exception e) {
            throw new IllegalArgumentException(e + "Error: Cannot create the flight.");
        }
        return flight;
    }

    private Airline readFromFile() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(this.file));

        String airline_name = br.readLine();
        if (airline_name == null) {
            br.close();
            throw new IOException("Cannot create Airline. The file is empty!");
        }
        Airline airline = new Airline(airline_name);

        String st;
        while((st = br.readLine()) != null) {
            Flight flight = this.createFlight(st);
            airline.addFlight(flight);
        }
        br.close();

        return airline;
    }

    public Airline parse() throws ParserException {

        if(!this.file.exists()) {
            throw new ParserException("Unable to load from file " + this.file_name);
        }

        Airline airline;
        try {
            airline = this.readFromFile();
        }
        catch (Exception e) {
            throw new ParserException("Cannot create the airline from this file.\n" + e);
        }

        return airline;
    }
}
