package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.AirlineDumper;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * TextDumper class implements AirlineDumper class.
 */
public class XmlDumper implements AirlineDumper<Airline> {

    /**
     * @param file_name - The name of the provided file.
     * @param err - an instance of the PrintWriter class, used to check errors.
     */
    private String file_name;
    private static PrintWriter err;

    /**
     * Constructor, initialises file_name and file variables.
     * @param name The name of the provided file.
     */
    public XmlDumper(String name) {
        this.file_name = name;
    }

    /**
     * Saves airline into the file.
     * @param airline is an instance of the Airline class to be written to the file
     * @throws IOException
     */
    public void dump(Airline airline) {
        if(airline == null) {
            System.err.println("Airline does not exist. Error saving it into the file!");
            System.exit(1);
        }
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true); // later change it to true
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation dom = builder.getDOMImplementation();
            DocumentType docType = dom.createDocumentType("airline", AirlineXmlHelper.PUBLIC_ID, AirlineXmlHelper.SYSTEM_ID);
            doc = dom.createDocument(null, "airline", docType);

            Element root = doc.getDocumentElement();
            Element name = doc.createElement("name");
            root.appendChild(name);
            String airline_name = airline.getName();
            name.appendChild(doc.createTextNode(airline_name));

            Collection<Flight> flights = airline.getFlights();
            for (Flight flight : flights) {
                Element fl = doc.createElement("flight");
                root.appendChild(fl);

                Element fl_number = doc.createElement("number");
                fl.appendChild(fl_number);
                int number = flight.getNumber();
                fl_number.appendChild(doc.createTextNode(Integer.toString(number)));

                Element fl_src = doc.createElement("src");
                fl.appendChild(fl_src);
                String src = flight.getSource();
                fl_src.appendChild(doc.createTextNode(src));

                Element fl_depart = doc.createElement("depart");
                fl.appendChild(fl_depart);
                Element fl_depart_date = doc.createElement("date");
                fl_depart.appendChild(fl_depart_date);
                Element fl_depart_time = doc.createElement("time");
                fl_depart.appendChild(fl_depart_time);

                Date depart = flight.getDeparture();
                SimpleDateFormat formatter = new SimpleDateFormat("dd");
                String depart_day = formatter.format(depart);
                formatter.applyPattern("M");
                String depart_month = formatter.format(depart);
                formatter.applyPattern("yyyy");
                String depart_year = formatter.format(depart);
                formatter.applyPattern("h");
                String depart_hour = formatter.format(depart);
                formatter.applyPattern("m");
                String depart_minute = formatter.format(depart);

                fl_depart_date.setAttribute("day", depart_day);
                fl_depart_date.setAttribute("month", depart_month);
                fl_depart_date.setAttribute("year", depart_year);
                fl_depart_time.setAttribute("hour", depart_hour);
                fl_depart_time.setAttribute("minute", depart_minute);

                Element fl_dest = doc.createElement("dest");
                fl.appendChild(fl_dest);
                String dest = flight.getDestination();
                fl_dest.appendChild(doc.createTextNode(dest));

                Element fl_arrive = doc.createElement("arrive");
                fl.appendChild(fl_arrive);
                Element fl_arrive_date = doc.createElement("date");
                fl_arrive.appendChild(fl_arrive_date);
                Element fl_arrive_time = doc.createElement("time");
                fl_arrive.appendChild(fl_arrive_time);

                Date arrive = flight.getArrival();
                formatter.applyPattern("dd");
                String arrive_day = formatter.format(arrive);
                formatter.applyPattern("M");
                String arrive_month = formatter.format(arrive);
                formatter.applyPattern("yyyy");
                String arrive_year = formatter.format(arrive);
                formatter.applyPattern("h");
                String arrive_hour = formatter.format(arrive);
                formatter.applyPattern("m");
                String arrive_minute = formatter.format(arrive);

                fl_arrive_date.setAttribute("day", arrive_day);
                fl_arrive_date.setAttribute("month", arrive_month);
                fl_arrive_date.setAttribute("year", arrive_year);
                fl_arrive_time.setAttribute("hour", arrive_hour);
                fl_arrive_time.setAttribute("minute", arrive_minute);
            }


            //Write the XML document to the file
            Source src = new DOMSource(doc);
            Result res = new StreamResult(new File(this.file_name));

            TransformerFactory xFactory = TransformerFactory.newInstance();
            Transformer xform = xFactory.newTransformer();
            xform.setOutputProperty(OutputKeys.INDENT, "yes");
            xform.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, AirlineXmlHelper.SYSTEM_ID);
            xform.transform(src, res);

        } catch (ParserConfigurationException | TransformerException e) {
//            e.printStackTrace();
            System.err.println("Error: cannot dump the airline into xml file!");
            System.exit(1);
        }

    }
}
