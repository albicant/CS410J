package edu.pdx.cs410J.sytov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {


  /**
   * Prints the information about the latest added flight.
   * @param flight is an object of the Flight class
   */
  public static void print(Flight flight) {
    System.out.println(flight.toString());
  }

  /**
   * Prints the README for the Project.
   */
  public static void printReadMe() {
    String str1 = "CS410J Winter2020 - Project 1 by Gennadii Sytov\n";
    String str2 = "This project parses the command line arguments to create an Airline class\n";
    String str3 = "and a Flight class. And then it adds the Flight to the Airline. The program has\n " +
            "\"-print\" and \"-README\" options.";
    System.out.println(str1+str2+str3);
  }

  /**
   * The main function. Parses the string of arguments from the console. Creates an instance of the Airline class and adds a Flight to the Airline,
   * if sufficient arguments provided. Optionally prints the "README" and/or the information about the added Flight.
   * @param args a string of console arguments
   */
  public static void main(String[] args) {

    for (String arg : args) {
      if(arg.equals("-README")) {
        printReadMe();
        System.exit(0);
      }
    }

    boolean print_flag = false;

    List<String> arguments = new ArrayList<String>(Arrays.asList(args));
    Iterator<String> it = arguments.iterator();
    while(it.hasNext()) {
      String arg = it.next();
      if(arg.equals("-print")) {
        print_flag = true;
        it.remove();
      }
      else if (arg.startsWith("-")) {
        System.err.println("Unknown option \'" + arg + "\'.");
        System.exit(1);
      }
    }

    args = arguments.toArray(new String[0]);
    if (args.length < 8) {
      System.err.println("Missing command line arguments.");
      System.exit(1);
    }

    int index = 1;
    String airline_name = "";
    if(args[0].startsWith("\"")) {
      airline_name = args[0].substring(1);
      for(int i = 1; i < args.length; ++i) {
        airline_name += " " + args[i];
        ++index;
        if(airline_name.endsWith("\"")) {
          airline_name = airline_name.substring(0, airline_name.length() - 1);
          break;
        }
      }
    }
    else {
      airline_name = args[0];
    }

    if(args.length < index + 7) {
      System.err.println("Missing command line arguments.");
      System.exit(1);
    }
    else if(args.length > index + 7) {
      System.err.println("Unknown command line arguments.");
      System.exit(1);
    }

    int number = 0;
    try {
      number = Integer.parseInt(args[index]);
    } catch (Exception e) {
      System.err.println("Error: Cannot convert \'" + args[index] + "\' to type int!");
      System.exit(1);
    }

    String src = args[index + 1];
    String depart = args[index + 2] + " " + args[index + 3];
    String dest = args[index + 4];
    String arrive = args[index + 5] + " " + args[index + 6];

    Airline airline = new Airline(airline_name);
    Flight flight = null;
    try {
      flight = new Flight(number, src, depart, dest, arrive);
    } catch (Exception e) {
      System.err.println(e + "Error: Cannot create the flight.");
      System.exit(1);
    }

    airline.addFlight(flight);

    if(print_flag) {
      print(flight);
    }

    System.exit(0);
  }

}