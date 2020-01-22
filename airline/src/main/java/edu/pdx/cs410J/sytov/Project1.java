package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.AbstractAirline;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  public static void print(Flight flight) {
    System.out.println(flight.toString());
  }

  public static void main(String[] args) {

//    System.err.println("Missing command line arguments");
    int number_of_arguments = args.length;
    if (number_of_arguments < 8)
      System.exit(-1);

    boolean used_print = false;
    boolean used_readme = false;

    String airline_name = args[0];
    int number = Integer.parseInt(args[1]);
    String src = args[2];
    String depart = args[3] + " " + args[4];
//    System.out.println(depart);
    String dest = args[5];
    String arrive = args[6] + " " + args[7];

    Airline airline = new Airline(airline_name);
    Flight flight = new Flight(number, src, depart, dest, arrive);
    airline.addFlight(flight);

    if (number_of_arguments >= 9) {
      if(args[8].equals("-print")) {
        print(flight);
        used_print = true;
      }
      else if(args[8].equals("-README")) {
        // do something
        used_readme = true;
      }
    }

    if (number_of_arguments == 10) {
      if(args[9].equals("-print") && !used_print) {
        print(flight);
        used_print = true;
      }
      else if (args[9].equals("-README") && !used_readme) {
        // do something
        used_readme = true;
      }
    }

//    for (String arg : args) {
//      System.out.println(arg);
//    }
//    Flight flight = new Flight(43, "PDX", "3/15/2017 10:39", "AMS", "3/16/2017 9:25");  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    System.exit(1);
  }

}