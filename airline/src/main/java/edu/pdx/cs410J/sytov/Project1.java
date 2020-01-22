package edu.pdx.cs410J.sytov;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  public static void print(Flight flight) {
    System.out.println(flight.toString());
  }

  public static void printReadMe() {
    String str1 = "CS410J Winter2020 - Project 1 by Gennadii Sytov\n";
    String str2 = "This project parses the command line arguments to create an Airline class\n";
    String str3 = "and a Flight class. And then it adds the Flight to the Airline. The program has\n " +
            "\"-print\" and \"-README\" options.";
    System.out.println(str1+str2+str3);
  }

  public static void main(String[] args) {

    for (String arg : args) {
      if(arg.equals("-README")) {
        printReadMe();
        System.exit(1);
      }
    }
    int number_of_arguments = args.length;
    if (number_of_arguments < 8) {
      System.err.println("Missing command line arguments. Minimum 8!");
      System.exit(1);
    }

    String airline_name = args[0];
//    try catch
    int number = Integer.parseInt(args[1]);
    String src = args[2];
    String depart = args[3] + " " + args[4];
    String dest = args[5];
    String arrive = args[6] + " " + args[7];

    Airline airline = new Airline(airline_name);
    Flight flight = new Flight(number, src, depart, dest, arrive);
    airline.addFlight(flight);

    for (int i = 8; i < args.length; ++i) {
      if (args[i].equals("-print")) {
        print(flight);
      }
    }

    System.exit(1);

  }

}