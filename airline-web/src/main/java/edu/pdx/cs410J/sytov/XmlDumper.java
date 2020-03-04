package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.AirlineDumper;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * XmlDumper class implements AirlineDumper class.
 */
public class XmlDumper implements AirlineDumper<Airline> {

    private final PrintWriter pw;

    /**
     * Constructor for the XmlDumper class
     * @param pw an instance of the PrintWriter class
     */
    public XmlDumper(PrintWriter pw) {
        this.pw = pw;
    }


    /**
     * Saves airline into the XML string and writes to the writer.
     * @param airline is an instance of the Airline class to be written to the file
     * @throws IOException
     */
    public void dump(Airline airline) throws IOException {
        if(airline == null) {
            System.err.println("Airline does not exist. Error saving it into the file!");
            throw new IOException();
        }
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            AirlineXmlHelper helper = new AirlineXmlHelper();
            builder.setErrorHandler(helper);
            builder.setEntityResolver(helper);

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
                Calendar cal = Calendar.getInstance();
                cal.setTime(depart);
                int depart_day = cal.get(Calendar.DAY_OF_MONTH);
                int depart_month = cal.get(Calendar.MONTH);
                int depart_year = cal.get(Calendar.YEAR);
                int depart_hour = cal.get(Calendar.HOUR_OF_DAY);
                int depart_minute = cal.get(Calendar.MINUTE);

                fl_depart_date.setAttribute("day", Integer.toString(depart_day));
                fl_depart_date.setAttribute("month", Integer.toString(depart_month));
                fl_depart_date.setAttribute("year", Integer.toString(depart_year));
                fl_depart_time.setAttribute("hour", Integer.toString(depart_hour));
                fl_depart_time.setAttribute("minute", Integer.toString(depart_minute));

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
                cal.setTime(arrive);
                int arrive_day = cal.get(Calendar.DAY_OF_MONTH);
                int arrive_month = cal.get(Calendar.MONTH);
                int arrive_year = cal.get(Calendar.YEAR);
                int arrive_hour = cal.get(Calendar.HOUR_OF_DAY);
                int arrive_minute = cal.get(Calendar.MINUTE);

                fl_arrive_date.setAttribute("day", Integer.toString(arrive_day));
                fl_arrive_date.setAttribute("month", Integer.toString(arrive_month));
                fl_arrive_date.setAttribute("year", Integer.toString(arrive_year));
                fl_arrive_time.setAttribute("hour", Integer.toString(arrive_hour));
                fl_arrive_time.setAttribute("minute", Integer.toString(arrive_minute));
            }


            Source src = new DOMSource(doc);
            Result res = new StreamResult(this.pw);

            TransformerFactory xFactory = TransformerFactory.newInstance();
            Transformer xform = xFactory.newTransformer();
            xform.setOutputProperty(OutputKeys.INDENT, "yes");
            xform.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, AirlineXmlHelper.SYSTEM_ID);
            xform.transform(src, res);

        } catch (ParserConfigurationException | TransformerException e) {
            System.err.println("Error: cannot dump the airline into xml file!");
            throw new IOException();
        }

    }
}
