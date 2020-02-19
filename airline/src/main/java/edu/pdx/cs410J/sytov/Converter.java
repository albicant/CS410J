package edu.pdx.cs410J.sytov;

/**
 * The Converter class that converts the representation of an Airline in a text file to an XML file.
 */
public class Converter {

    /**
     * The main method for the Converter class that parses command line arguments.
     * Expects to get text_file_name as the first argument and xml_file_name as the second argument
     * @param args is a string of console arguments
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Error: Missing command line arguments!");
            System.exit(1);
        } else if(args.length > 2) {
            System.err.println("Error: Unknown command line arguments!");
            System.exit(1);
        }

        String textFile = args[0];
        String xmlFile = args[1];

        Airline airline = null;

        TextParser tp = new TextParser(textFile);
        if(tp.checkFileExistence()) {
            try {
                airline = tp.parse();
            } catch (Exception e) {
                System.err.println("Cannot create airline from the fil");
                System.exit(1);
            }
        } else {
            System.err.println("Error: Cannot create airline from the text file. The file does not exist!");
            System.exit(1);
        }


        XmlDumper xd = new XmlDumper(xmlFile);
        try {
            xd.dump(airline);
        } catch (Exception e) {
            System.err.println("Cannot save the airline to the xml file!");
            System.exit(1);
        }

        System.exit(0);
    }
}
