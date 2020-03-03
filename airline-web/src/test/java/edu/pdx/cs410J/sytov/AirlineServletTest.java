package edu.pdx.cs410J.sytov;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AirlineServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class AirlineServletTest {

  @Test
  public void initiallyServletContainsNoDictionaryEntries() throws ServletException, IOException {
    AirlineServlet servlet = new AirlineServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    int expectedWords = 0;
    verify(pw).println(Messages.formatWordCount(expectedWords));
    verify(response).setStatus(HttpServletResponse.SC_OK);
  }

  @Test
  public void addingFlightToServletStoresAirlineWithFlight() throws ServletException, IOException {


    String airlineName = "TEST AIRLINE";
    int flightNumber = 123;
    String src = "AMS";
    String depart = "3/1/2020 1:00 pm";
    String dest = "PDX";
    String arrive = "3/1/2020 10:20 pm";
    PrintWriter pw = mock(PrintWriter.class);

    AirlineServlet servlet = createFlight(airlineName, flightNumber, src, depart, dest, arrive);

    Airline airline = servlet.getAirline(airlineName);
    assertThat(airline, not(nullValue()));

    Flight flight = airline.getFlights().iterator().next();
    assertThat(flight.getNumber(), equalTo(flightNumber));
  }

  private AirlineServlet createFlight(String airlineName, int flightNumber, String src, String depart, String dest, String arrive) throws IOException, ServletException {
    AirlineServlet servlet = new AirlineServlet();
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("airline")).thenReturn(airlineName);
    when(request.getParameter("flightNumber")).thenReturn(String.valueOf(flightNumber));
    when(request.getParameter("src")).thenReturn(src);
    when(request.getParameter("depart")).thenReturn(depart);
    when(request.getParameter("dest")).thenReturn(dest);
    when(request.getParameter("arrive")).thenReturn(arrive);

    HttpServletResponse response = mock(HttpServletResponse.class);

    PrintWriter pw = mock(PrintWriter.class);
    when(response.getWriter()).thenReturn(pw);
    servlet.doPost(request, response);

    return servlet;
  }

  @Test
  public void getAirlineAsXml() throws ServletException, IOException {
    String airlineName = "TEST AIRLINE";
    int flightNumber = 123;
    String src = "AMS";
    String depart = "3/1/2020 1:00 pm";
    String dest = "PDX";
    String arrive = "3/1/2020 10:20 pm";

    AirlineServlet servlet = createFlight(airlineName, flightNumber, src, depart, dest, arrive);

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("airline")).thenReturn(airlineName);

    HttpServletResponse response = mock(HttpServletResponse.class);

    ArgumentCaptor<String> textWrittenToWriter = ArgumentCaptor.forClass(String.class);

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    String xml = sw.toString();
    assertThat(xml, containsString(airlineName));
    assertThat(xml, containsString(String.valueOf(flightNumber)));
  }

}
