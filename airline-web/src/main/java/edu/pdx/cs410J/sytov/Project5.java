package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.ParserException;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project5 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    private static int number_of_arguments = 10;

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
        String str1 = "CS410J Winter2020 - Project 5 by Gennadii Sytov\n";
        String str2 = "This project parses the command line arguments to connect to the server, create airlines, flights, add flights to the airlines, search and print airline and flights information.\n"+
                      "The following options can be specified: \n" +
                      "\t-host hostname                         Host computer on which the server runs\n" +
                      "\t-port port                             Port on which the server is listening\n" +
                      "\t-search airline source destination     Search for flights\n" +
                      "\t-print                                 Prints a description of the new flight\n" +
                      "\t-README                                Prints the README for this project.";
        System.out.println(str1+str2);
    }

    /**
     * The main function. Parses the string of arguments from the console. Communicates with the client to connect to the server,
     * creates an instance of the Airline class and adds a Flight to the Airline,
     * if sufficient arguments provided. Optionally prints the "README" and/or the information about the added Flight.
     * @param args a string of console arguments
     */
    public static void main(String... args) {

        for (String arg : args) {
            if(arg.equals("-README")) {
                printReadMe();
                System.exit(0);
            }
        }

        String hostName = null;
        String portString = null;
        boolean print_flag = false;
        boolean search_flag = false;

        List<String> arguments = new ArrayList<String>(Arrays.asList(args));
        Iterator<String> it = arguments.iterator();
        while(it.hasNext()) {
            String arg = it.next();
            if(arg.equals("-print")) {
                print_flag = true;
                it.remove();
            }
            else if(arg.equals("-host")) {
                it.remove();
                if(!it.hasNext()) {
                    System.err.println("-host option is specified but the host computer is not provided!");
                    System.exit(1);
                }
                arg = it.next();
                if(arg.startsWith("-")) {
                    System.err.println("-host option is specified but the host computer is not provided!");
                    System.exit(1);
                }
                hostName = arg;
                it.remove();
            }
            else if(arg.equals("-port")) {
                it.remove();
                if(!it.hasNext()) {
                    System.err.println("-port option is specified but the port is not provided!");
                    System.exit(1);
                }
                arg = it.next();
                if(arg.startsWith("-")) {
                    System.err.println("-host option is specified but the port is not provided!");
                    System.exit(1);
                }
                portString = arg;
                it.remove();
            }
            else if(arg.equals("-search")) {
                search_flag = true;
                it.remove();
                if(!it.hasNext()) {
                    System.err.println("-search option is specified but the required arguments are not provided!");
                    System.exit(1);
                }
            }
            else if (arg.startsWith("-")) {
                System.err.println("Unknown option \'" + arg + "\'.");
                System.exit(1);
            }
        }


        if (hostName == null) {
            usage( MISSING_ARGS );

        } else if ( portString == null) {
            usage( "Missing port" );
        }

        if(search_flag && print_flag) {
            System.err.println("-print and -search options cannot be specified together");
            System.exit(1);
        }


        args = arguments.toArray(new String[0]);
        if (args.length < 1) {
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

        if(search_flag) {
            number_of_arguments = 3;
        }
        else if (args.length == index) {
            number_of_arguments = 1;
        }

        if(args.length < index + (number_of_arguments - 1)) {
            System.err.println("Missing command line arguments.");
            System.exit(1);
        }
        else if(args.length > index + (number_of_arguments - 1)) {
            System.err.println("Unknown command line arguments.");
            System.exit(1);
        }


        int port;
        try {
            port = Integer.parseInt( portString );

        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        AirlineRestClient client = new AirlineRestClient(hostName, port);

        int number = 0;
        String src = null;
        String depart = null;
        String dest = null;
        String arrive = null;
        if (search_flag) {
            src = args[index];
            dest = args[index + 1];
        }
        else if (number_of_arguments > 1) {
            try {
                number = Integer.parseInt(args[index]);
            } catch (Exception e) {
                System.err.println("Error: Cannot convert \'" + args[index] + "\' to type int!");
                System.exit(1);
            }

            src = args[index + 1];
            depart = args[index + 2] + " " + args[index + 3] + " " + args[index + 4];
            dest = args[index + 5];
            arrive = args[index + 6] + " " + args[index + 7] + " " + args[index + 8];
        }


        try {
            if (search_flag) {
                String xml = null;
                try {
                    xml = client.searchFlights(airline_name, src, dest);
                } catch (AirlineRestClient.AirlineRestException e) {
                    System.err.println("Error: such airline doesn't exist!");
                    System.exit(1);
                }
                try {
                    prettyPrint(xml);
                } catch (Exception e) {
                    System.err.println("Cannot pretty print the airline!");
                    System.exit(1);
                }
            }
            else if (number_of_arguments == 1) {
                String xml = null;
                try {
                    xml = client.getAirlineAsXml(airline_name);
                } catch (AirlineRestClient.AirlineRestException e) {
                    System.err.println("Error: such airline doesn't exist!");
                    System.exit(1);
                }
                try {
                    prettyPrint(xml);
                } catch (Exception e) {
                    System.err.println("Cannot pretty print the airline!");
                    System.exit(1);
                }
            }
            else {
                try {
                    client.addFlight(airline_name, number, src, depart, dest, arrive);
                } catch (Exception e) {
                    System.err.println("Error: cannot create the flight.");
                    System.exit(1);
                }
                if (print_flag) {
                    try {
                        Flight flight = new Flight(number, src, depart, dest, arrive);
                        print(flight);
                    } catch (Exception e) {
                        System.err.println("Cannot create the flight!");
                        System.exit(1);
                    }
                }
            }
        } catch ( IOException ex ) {
            error("Cannot connect to the server.");
            return;
        }

        System.exit(0);
    }

    /**
     * Pretty prints the information about the airline to standard out.
     * @param xml -String which contains information about an airline in XML format
     * @throws ParserException
     * @throws IOException
     */
    private static void prettyPrint(String xml) throws ParserException, IOException {
        XmlParser xmlpar = new XmlParser(xml);
        Airline airline = xmlpar.parse();

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        PrettyPrinter pp = new PrettyPrinter(pw);
        pp.dump(airline);

        System.out.print(sw.toString());
    }

    /**
     * Helper function to print error messages
     * @param message String with error message
     */
    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project5 -host host -port port airline");
        err.println("  host         Host of web server");
        err.println("  port         Port of web server");
        err.println("  airline      Airline name");
        err.println("  For more options use -README");
        err.println();

        System.exit(1);
    }
}