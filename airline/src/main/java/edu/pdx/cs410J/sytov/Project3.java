package edu.pdx.cs410J.sytov;

import java.util.*;

/**
 * The main class for the CS410J airline Project
 */
public class Project3 {

  private static final int number_of_arguments = 10;


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
    String str1 = "CS410J Winter2020 - Project 3 by Gennadii Sytov\n";
    String str2 = "This project parses the command line arguments to create an Airline class\n";
    String str3 = "and a Flight class. And then it adds the Flight to the Airline.\n"  +
            "Optionally, the file can be specified to upload and save the Airline from/into it.\n" +
            "The program has \"-print\", \"-textFile\" followed by the file name, and \"-README\" options.";
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
    boolean text_file_flag = false;
    boolean pretty_flag = false;
    String file_name = null;
    String pretty_name = null;

    List<String> arguments = new ArrayList<String>(Arrays.asList(args));
    Iterator<String> it = arguments.iterator();
    while(it.hasNext()) {
      String arg = it.next();
      if(arg.equals("-print")) {
        print_flag = true;
        it.remove();
      }
      else if(arg.equals("-textFile")) {
        text_file_flag = true;
        it.remove();
        if(!it.hasNext()) {
          System.err.println("-textFile flag is specified but the file name is not provided!");
          System.exit(1);
        }
        arg = it.next();
        if(arg.startsWith("-")) {
          System.err.println("-textFile flag is specified but the file name is not provided!");
          System.exit(1);
        }
        file_name = arg;
        it.remove();
      }
      else if(arg.equals("-pretty")) {
        pretty_flag = true;
        it.remove();
        if(!it.hasNext()) {
          System.err.println("-pretty flag is specified but the file name is not provided!");
          System.exit(1);
        }
        arg = it.next();
        if(arg.startsWith("-")) {
          System.err.println("-pretty flag is specified but the file name is not provided!");
          System.exit(1);
        }
        pretty_name = arg;
        it.remove();
      }
      else if (arg.startsWith("-")) {
        System.err.println("Unknown option \'" + arg + "\'.");
        System.exit(1);
      }
    }

    args = arguments.toArray(new String[0]);
    if (args.length < number_of_arguments) {
      System.err.println("Missing command line arguments.");
      System.exit(1);
    }

    if(file_name != null && pretty_name != null & file_name.equals(pretty_name)) {
      System.err.println("Error: text file name and pretty file name cannot be the same!");
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

    if(args.length < index + (number_of_arguments - 1)) {
      System.err.println("Missing command line arguments.");
      System.exit(1);
    }
    else if(args.length > index + (number_of_arguments - 1)) {
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
    String depart = args[index + 2] + " " + args[index + 3] + " " + args[index + 4];
    String dest = args[index + 5];
    String arrive = args[index + 6] + " " + args[index + 7] + " " + args[index + 8];

    Airline airline = null;
    if(text_file_flag) {
      TextParser tp = new TextParser(file_name);
      if(tp.checkFileExistence()) {
        try {
          airline = tp.parse();
        } catch (Exception e) {
          System.err.println("Cannot create airline from the file");
          System.exit(1);
        }
      }
    }
    if(airline == null) {
      airline = new Airline(airline_name);
    }

    if(!airline_name.equals(airline.getName())) {
      System.err.println("Error: airline name from the file does not match the airline name from the console!");
      System.exit(1);
    }

    Flight flight = null;
    try {
      flight = new Flight(number, src, depart, dest, arrive);
    } catch (Exception e) {
      System.err.println("Error: Cannot create the flight.");
      System.exit(1);
    }

    airline.addFlight(flight);

    if(text_file_flag) {
      TextDumper td = new TextDumper(file_name);
      try {
        td.dump(airline);
      } catch (Exception e) {
        System.err.println("Cannot save the airline to the file!");
        System.exit(1);
      }
    }

    if(pretty_flag) {
      //print pretty file
    }

    if(print_flag) {
      print(flight);
    }


    System.exit(0);
  }

}