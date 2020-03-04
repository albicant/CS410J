package edu.pdx.cs410J.sytov;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class AirlineRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";

    private final String url;


    /**
     * Creates a client to the airline REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public AirlineRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }


    /**
     * Returns XML String with information about the airline
     * @param airline_name String with the airline's name
     * @return XML string
     * @throws IOException
     */
    public String getAirlineAsXml(String airline_name) throws IOException {
      Response response = get(this.url, Map.of("airline", airline_name));
      throwExceptionIfNotOkayHttpStatus(response);
      String xml = response.getContent();
      return xml;
    }

    /**
     * Returns XML String with information about the airline with flights that originate and end at the desired airports
     * @param airline_name String with the airline's name
     * @param src Source airport
     * @param dest Destination airport
     * @return XML string
     * @throws IOException
     */
    public String searchFlights(String airline_name, String src, String dest) throws IOException {
      Response response = get(this.url, Map.of("airline", airline_name, "src", src, "dest", dest));
      throwExceptionIfNotOkayHttpStatus(response);
      String xml = response.getContent();
      return xml;
    }

    @VisibleForTesting
    Response postToMyURL(Map<String, String> dictionaryEntries) throws IOException {
      return post(this.url, dictionaryEntries);
    }

    public void removeAllDictionaryEntries() throws IOException {
      Response response = delete(this.url, Map.of());
      throwExceptionIfNotOkayHttpStatus(response);
    }

    private Response throwExceptionIfNotOkayHttpStatus(Response response) {
      int code = response.getCode();
      if (code != HTTP_OK) {
        throw new AirlineRestException(code);
      }
      return response;
    }

    public void addFlight(String airlineName, int flightNumber, String src, String depart, String dest, String arrive) throws IOException {
      Response response = postToMyURL(Map.of("airline", airlineName, "flightNumber", String.valueOf(flightNumber),
              "src", src, "depart", depart, "dest", dest, "arrive", arrive));
      throwExceptionIfNotOkayHttpStatus(response);
    }


    @VisibleForTesting
    class AirlineRestException extends RuntimeException {
      AirlineRestException(int httpStatusCode) {
        super("Got an HTTP Status Code of " + httpStatusCode);
      }
    }
}
