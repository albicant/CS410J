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
     * @param number_of_arguments is a constant to assert the number of arguments from a string to create a flight
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
     * Parses the string and creates an instance of the Flight class.
     * @param str contains information to create a flight.
     * @return created flight.
     */
    private Flight createFlight(String str) {
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

    /**
     * Parses the XML element and creates an string with date and time in the following format: M/d/yyyy hh:mm a.
     * @param root contains information an element from XML with date and time.
     * @return created string.
     */
    private String parseDayTime(Element root) throws ParserException {
        String date_time_str = "";
        NodeList entries = root.getChildNodes();


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
                        System.err.println("Error parsing the XML file: missing date!");
                        throw new ParserException("");
                    }
                    counter++;
                    String month = "";
                    try {
                        month = Integer.toString(Integer.parseInt(e.getAttribute("month")) + 1);
                    }
                    catch (Exception ex) {
                        System.err.println("Error: Cannot convert \'" + month + "\' to type int!");
                        throw new IllegalArgumentException();
                    }

                    date = month + "/" + e.getAttribute("day") + "/" + e.getAttribute("year");
                    break;
                }
                case "time": {
                    if (counter != 1) {
                        System.err.println("Error parsing the XML file: missing time!");
                        throw new ParserException("");
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
                    }
                    else if (hour == 12) {
                        append = "pm";
                    }
                    else if (hour > 12) {
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

    /**
     * Parses the XML element and creates an instance of the Flight class.
     * @param root contains information to create a flight.
     * @return created flight.
     * @throws ParserException
     */
    private Flight parseFlight(Element root) throws ParserException  {
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
                        System.err.println("Error parsing the XML file: missing flight number!");
                        throw new ParserException("");
                    }
                    counter++;
                    flight_str += e.getTextContent();// change it to getNodeValue and change to put value in XmLDumper
                    break;
                }
                case "src": {
                    if (counter != 1) {
                        System.err.println("Error parsing the XML file: missing source airport!");
                        throw new ParserException("");
                    }
                    counter++;
                    flight_str += " " + e.getTextContent();
                    break;
                }
                case "depart": {
                    if (counter != 2) {
                        System.err.println("Error parsing the XML file: missing departure time!");
                        throw new ParserException("");
                    }
                    counter++;
                    flight_str += " " + this.parseDayTime(e);
                    break;
                }
                case "dest": {
                    if (counter != 3) {
                        System.err.println("Error parsing the XML file: missing destination airport!");
                        throw new ParserException("");
                    }
                    counter++;
                    flight_str += " " + e.getTextContent();
                    break;
                }
                case "arrive": {
                    if (counter != 4) {
                        System.err.println("Error parsing the XML file: missing arrival time!");
                        throw new ParserException("");
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

    /**
     * Parses the XML element and creates an instance of the Airline class.
     * @param root contains information to create an airline.
     * @return created airline.
     * @throws ParserException
     */
    private Airline createAirline(Element root) throws ParserException {
        String airline_name = "";
        NodeList entries = root.getChildNodes();
        Node a_name = entries.item(1);
        Element entry_name = (Element) a_name;

        if(entry_name.getNodeName() == "name") {
            airline_name = entry_name.getTextContent();
        } else {
            System.err.println("Cannot create an airline from the xml file: missing airline name!");
            throw new ParserException("");
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
                throw new ParserException("");
            }

        }
        return airline;
    }

    /**
     * Reads from the XML file and creates an instance of the Airline class.
     * @return an instance of the Airline class
     * @throws ParserException
     */
    public Airline parse() throws ParserException {

        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            AirlineXmlHelper helper = new AirlineXmlHelper();
            builder.setErrorHandler(helper);
            builder.setEntityResolver(helper);

            doc = builder.parse(this.file);



        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.err.println("Error: cannot create an airline from the xml file!");
            throw new ParserException("");
        }

        NodeList entries = doc.getChildNodes();
        Airline airline = null;
        for(int i = 1; i < entries.getLength(); ++i) {
            Node n_fl = entries.item(i);
            if (!(n_fl instanceof Element)) {
                continue;
            }
            Element e_fl = (Element) n_fl;
            if (e_fl.getNodeName() == "airline") {
                airline = this.createAirline(e_fl);
            }
            else {
                System.err.println("Unknown entry!");
                throw new ParserException("");
            }

        }

        return airline;
    }
}
