package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * XmlParser class implements AirlineParser class.
 */
public class XmlParser implements AirlineParser<Airline> {

    /**
     * @param file_name The name of the provided file.
     * @param file is the instance of the File class with file_name
     */
    private final String file_name;
    private final File file;
    private final int number_of_arguments = 9;

    /**
     * Constructor, initialises file_name and file variables.
     * @param file_name The name of the provided file.
     */
    public XmlParser(String file_name) {
        this.file_name = file_name;
        this.file = new File(file_name);
    }

    /**
     * Helper function to check whether the file already exists or not.
     */
    public boolean checkFileExistence() {
        return this.file.exists();
    }

    /**
     * Parses the string and creates an insnance of the Flight class.
     * @param str contains information to create a flight.
     * @return created flight.
     */
    private Flight createFlight(String str) {
        System.out.println(str);
        String[] args = str.split(" ");

        if(args.length < number_of_arguments) {
            System.err.println("Missing flight arguments.");
            throw new IllegalArgumentException();
        }
        else if(args.length > number_of_arguments) {
            System.err.println("Unknown flight arguments.");
            throw new IllegalArgumentException();
        }

        int number = 0;
        try {
            number = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.err.println("Error: Cannot convert \'" + args[0] + "\' to type int!");
            throw new IllegalArgumentException();
        }

        String src = args[1];
        String depart = args[2] + " " + args[3] + " " + args[4];
        String dest = args[5];
        String arrive = args[6] + " " + args[7] + " " + args[8];

        Flight flight;
        try {
            flight = new Flight(number, src, depart, dest, arrive);
        } catch (Exception e) {
            System.err.println("Error: Cannot create the flight.");
            throw new IllegalArgumentException();
        }
        return flight;
    }


    private String parseDayTime(Element root) {
        String date_time_str = "";
        NodeList entries = root.getChildNodes();
        //assert length


        int counter = 0;
        String date = null;
        String time = null;
        String append = "am";

        for (int i = 0; i < entries.getLength(); ++i) {
            if (counter > 2) {
                break;
            }
            Node node = entries.item(i);
            if (!(node instanceof Element)) {
                continue;
            }
            Element e = (Element) node;
            switch (e.getNodeName()) {
                case "date": {
                    if (counter != 0) {
                        // throw exception
                        System.exit(1);
                    }
                    counter++;
                    date = e.getAttribute("month") + "/" + e.getAttribute("day") + "/" + e.getAttribute("year");
                    break;
                }
                case "time": {
                    if (counter != 1) {
                        // throw exception
                        System.exit(1);
                    }
                    counter++;
                    time = e.getAttribute("hour");
                    int hour = -1;
                    try {
                        hour = Integer.parseInt(time);
                    } catch (Exception ex) {
                        System.err.println("Error: Cannot convert \'" + time + "\' to type int!");
                        throw new IllegalArgumentException();
                    }
                    if (hour == 0) {
                        hour = 12;
                    } else if (hour > 12) {
                        hour -= 12;
                        append = "pm";
                    }
                    time = Integer.toString(hour);
                    String minute = e.getAttribute("minute");
                    if (minute.length() == 1) {
                        minute = "0" + minute;
                    }
                    time += ":" + minute + " " + append;
                    break;
                }
            }
        }

        date_time_str = date + " " + time;
        return date_time_str;

    }

    private Flight parseFlight(Element root) {
        NodeList entries = root.getChildNodes();
        String flight_str = "";

        int counter = 0;

        for (int i = 0; i < entries.getLength(); ++i) {
            if(counter > 4) {
                break;
            }
            Node node = entries.item(i);
            if (!(node instanceof Element)) {
                continue;
            }
            Element e = (Element) node;
            switch(e.getNodeName()) {
                case "number": {
                    if (counter != 0) {
                        // throw exception
                        System.exit(1);
                    }
                    counter++;
                    flight_str += e.getTextContent();// change it to getNodeValue and change to put value in XmLDumper
                    break;
                }
                case "src": {
                    if (counter != 1) {
                        // throw exception
                        System.exit(1);
                    }
                    counter++;
                    flight_str += " " + e.getTextContent();
                    break;
                }
                case "depart": {
                    if (counter != 2) {
                        // throw exception
                        System.exit(1);
                    }
                    counter++;
                    flight_str += " " + this.parseDayTime(e);
                    break;
                }
                case "dest": {
                    if (counter != 3) {
                        // throw exception
                        System.exit(1);
                    }
                    counter++;
                    flight_str += " " + e.getTextContent();
                    break;
                }
                case "arrive": {
                    if (counter != 4) {
                        // throw exception
                        System.exit(1);
                    }
                    counter++;
                    flight_str += " " + this.parseDayTime(e);
                    break;
                }
            }
        }


        Flight flight = this.createFlight(flight_str);
        return flight;
    }

    private Airline createAirline(Element root) {
        String airline_name = "";
        NodeList entries = root.getChildNodes();
        Node a_name = entries.item(1);
        Element entry_name = (Element) a_name;

        if(entry_name.getNodeName() == "name") {
            airline_name = entry_name.getTextContent();
        } else {
            System.err.println("Cannot create an airline from the xml file: missing airline name!");
            System.exit(1); // change it for exceptions
        }
        Airline airline = new Airline(airline_name);
        for(int i = 2; i < entries.getLength(); ++i) {
            Node n_fl = entries.item(i);
            if (!(n_fl instanceof Element)) {
                continue;
            }
            Element e_fl = (Element) n_fl;
            if (e_fl.getNodeName() == "flight") {
                Flight flight = parseFlight(e_fl);
                airline.addFlight(flight);
            }
            else {
                System.err.println("Unknown entry!");
                System.exit(1); // change it for exception
            }

        }
        return airline;
    }

    /**
     * Reads from the file and creates an instance of the Airline class.
     * @return an instance of the Airline class
     * @throws ParserException
     */
    public Airline parse() throws ParserException {

        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(this.file);



        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        Element root = (Element) doc.getChildNodes().item(1);
        Airline airline = this.createAirline(root);

        return airline;

    }
}
