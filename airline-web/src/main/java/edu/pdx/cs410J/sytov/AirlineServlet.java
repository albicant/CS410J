package edu.pdx.cs410J.sytov;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class AirlineServlet extends HttpServlet {
  static final String AIRLINE_NAME_PARAMETER = "airline";
  static final String FLIGHT_NUMBER_PARAMETER = "flightNumber";
  static final String SRC_PARAMETER = "src";
  static final String DEPART_PARAMETER = "depart";
  static final String DEST_PARAMETER = "dest";
  static final String ARRIVE_PARAMETER = "arrive";

  private final Map<String, Airline> airlines = new HashMap<>();

  /**
   * Handles an HTTP GET request from a client by writing the definition of the
   * word specified in the "word" HTTP parameter to the HTTP response.  If the
   * "word" parameter is not specified, all of the entries in the dictionary
   * are written to the HTTP response.
   */
  @Override
  protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
  {
      response.setContentType( "text/plain" );

      String airlineName = getParameter( AIRLINE_NAME_PARAMETER, request );
      String src = getParameter(SRC_PARAMETER, request);
      String dest = getParameter(DEST_PARAMETER, request);
      PrintWriter pw = response.getWriter();
      Airline airline = null;

      if (airlineName == null) {
          missingRequiredParameter(response, AIRLINE_NAME_PARAMETER);
          return;
      }
      else if (src == null && dest == null) {
          airline = getAirline(airlineName);

      }
      else if (src == null && dest != null) {
          missingRequiredParameter(response, SRC_PARAMETER);
          return;
      }
      else if (src != null && dest == null) {
          missingRequiredParameter(response, DEST_PARAMETER);
          return;
      }
      else {
          airline = getAirlineFlights(airlineName, src, dest);
      }

      if(airline == null) {
          missingRequiredParameter(response, "Airline with name \'" + airlineName + "\' does not exist!");
          return;
      }

      XmlDumper dumper = new XmlDumper(pw);
      dumper.dump(airline);

      pw.flush();
      response.setStatus( HttpServletResponse.SC_OK);
  }


    private Airline getAirlineFlights(String airlineName, String src, String dest) {
        Airline airline = getAirline(airlineName);
        if (airline == null) {
            return null;
        }

        Airline newAirline = new Airline(airlineName);
        Collection<Flight> flights = airline.getFlights();

        for (Flight flight : flights) {
            if (flight.getSource().equals(src) && flight.getDestination().equals(dest)) {
                newAirline.addFlight(flight);
            }
        }

        return newAirline;
    }

    /**
   * Handles an HTTP POST request by storing the dictionary entry for the
   * "word" and "definition" request parameters.  It writes the dictionary
   * entry to the HTTP response.
   */
  @Override
  protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
  {
      response.setContentType( "text/plain" );

      String airlineName = getParameter(AIRLINE_NAME_PARAMETER, request );

      if (airlineName == null) {
          missingRequiredParameter(response, AIRLINE_NAME_PARAMETER);
          return;
      }

      String flightNumber = getParameter(FLIGHT_NUMBER_PARAMETER, request );
      if ( flightNumber == null) {
          missingRequiredParameter( response, FLIGHT_NUMBER_PARAMETER );
          return;
      }
      String src = getParameter(SRC_PARAMETER, request );
      if ( src == null) {
          missingRequiredParameter( response, SRC_PARAMETER );
          return;
      }
      String depart = getParameter(DEPART_PARAMETER, request );
      if ( depart == null) {
          missingRequiredParameter( response, DEPART_PARAMETER );
          return;
      }
      String dest = getParameter(DEST_PARAMETER, request );
      if ( dest == null) {
          missingRequiredParameter( response, DEST_PARAMETER );
          return;
      }
      String arrive = getParameter(ARRIVE_PARAMETER, request );
      if ( arrive == null) {
          missingRequiredParameter( response, ARRIVE_PARAMETER );
          return;
      }

      int number = 0;
      try {
          number = Integer.parseInt(flightNumber);
      } catch (Exception e) {
          missingRequiredParameter( response, "Error: Flight number must be an integer!" );
          return;
      }

      try {
          Flight flight = new Flight(number, src, depart, dest, arrive);

          Airline airline = getOrCreateAirline(airlineName);
          airline.addFlight(flight);

          this.airlines.put(airlineName, airline);

          PrintWriter pw = response.getWriter();
          pw.println(flight.toString() + " has been added to the \'" + airlineName + "\' airline.");
          pw.flush();

          response.setStatus(HttpServletResponse.SC_OK);
      } catch (Exception e) {
          missingRequiredParameter( response, "Error: Cannot create the flight!" );
          return;
      }
  }

    private Airline getOrCreateAirline(String airlineName) {
        Airline airline = getAirline(airlineName);
        if (airline == null) {
            airline = new Airline(airlineName);
            this.airlines.put(airlineName, airline);
        }

        return airline;
    }

    /**
   * Handles an HTTP DELETE request by removing all dictionary entries.  This
   * behavior is exposed for testing purposes only.  It's probably not
   * something that you'd want a real application to expose.
   */
  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/plain");

      this.airlines.clear();

      PrintWriter pw = response.getWriter();
      pw.println(Messages.allDictionaryEntriesDeleted());
      pw.flush();

      response.setStatus(HttpServletResponse.SC_OK);

  }

  /**
   * Writes an error message about a missing parameter to the HTTP response.
   *
   * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
   */
  private void missingRequiredParameter( HttpServletResponse response, String parameterName )
      throws IOException
  {
      String message = Messages.missingRequiredParameter(parameterName);
      response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
  }


  /**
   * Returns the value of the HTTP request parameter with the given name.
   *
   * @return <code>null</code> if the value of the parameter is
   *         <code>null</code> or is the empty string
   */
  private String getParameter(String name, HttpServletRequest request) {
    String value = request.getParameter(name);
    if (value == null || "".equals(value)) {
      return null;

    } else {
      return value;
    }
  }

    @VisibleForTesting
    Airline getAirline(String airlineName){
      return this.airlines.get(airlineName);
    }
}
