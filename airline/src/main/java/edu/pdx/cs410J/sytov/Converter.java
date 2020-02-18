package edu.pdx.cs410J.sytov;

public class Converter {

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
                System.err.println("Cannot create airline from the file");
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
