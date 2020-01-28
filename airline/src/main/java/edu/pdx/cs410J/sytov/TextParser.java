package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TextParser implements AirlineParser<Airline> {

    private String file_name;

    public TextParser(String name) {
        this.file_name = name;
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

    public Airline parse() throws ParserException {

        File file = new File(this.file_name);
        if(!file.exists()) {
            throw new ParserException("Unable to load from file " + this.file_name);
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            System.err.println(e + " Cannot read from the file " + this.file_name);
        }

        String airline_name = br.readLine();
        if (airline_name == null) {
            br.close();
            throw new ParserException("Cannot create Airline. The file is empty!");
        }
        Airline airline = new Airline(airline_name);

        String st;
        while((st = br.readLine()) != null) {
            Flight flight = this.createFlight(st);
            airline.addFlight(flight);
        }
        br.close();

        return airline;

//        } catch (ParserException ex) {
//            System.err.println(ex + ": Unable to load from file " + this.file_name);
//        }

    }
}
