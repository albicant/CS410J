package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirportNames;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Flight class implements AbstractFlight class.
 */
public class Flight extends AbstractFlight implements Comparable<Flight> {

  /**
   * @param number is the flight number.
   * @param src is the three-letter code of departure airport
   * @param depart is the Departure date and time (24-hour time) represented as a String
   * @param dest is the three-letter code of arrival airport
   * @param arrive is the arrival date and time (24-hour time) represented as a String
   */
  private final int number;
  private final String src;
  private final String depart;
  private Date depart_date;
  private final String dest;
  private final String arrive;
  private Date arrive_date;
  private AirportNames air_names;

  /**
   * Helper function for time validation
   * @param time is the String to be validated
   * @return true if the time is in valid format, false otherwise
   */
  public boolean validateTime(String time) {
    if (!time.matches("[/: 0-9]+(am|pm)$"))
      return false;
    String[] tokens = time.split(" ");
    if (tokens.length != 3)
      return false;
    String[] tokens_date = tokens[0].split("/");
    String[] tokens_time = tokens[1].split(":");
    if(tokens_date.length != 3 || tokens_time.length != 2)
      return false;
    if(tokens_date[0].length() < 1 || tokens_date[0].length() > 2 ||
     tokens_date[1].length() < 1 || tokens_date[1].length() > 2 ||
     tokens_date[2].length() != 4 ||
     tokens_time[0].length() < 1 || tokens_time[0].length() > 2 ||
     tokens_time[1].length() != 2) {
      return false;
    }
    if(Integer.parseInt(tokens_date[0]) < 1 || Integer.parseInt(tokens_date[0]) > 12 ||
     Integer.parseInt(tokens_date[1]) < 1 || Integer.parseInt(tokens_date[1]) > 31 ||
     Integer.parseInt(tokens_date[2]) < 1900 ||
     Integer.parseInt(tokens_time[0]) < 1 || Integer.parseInt(tokens_time[0]) > 12 ||
     Integer.parseInt(tokens_time[1]) < 0 || Integer.parseInt(tokens_time[1]) > 59) {
      return false;
    }
    if(!tokens[2].equals("am") && !tokens[2].equals("pm"))
      return false;

    return true;
  }

  private void setDateAndTime() throws ParseException {

    String pattern = "MM/dd/yyyy hh:mm a";

    this.depart_date = new SimpleDateFormat(pattern).parse(this.depart);
    this.arrive_date = new SimpleDateFormat(pattern).parse(this.arrive);
  }

  /**
   * The Constructor of the Flight class
   * @param number is the flight number.
   * @param src is the three-letter code of departure airport
   * @param depart is the Departure date and time (24-hour time) represented as a String
   * @param dest is the three-letter code of arrival airport
   * @param arrive is the arrival date and time (24-hour time) represented as a String
   */
  public Flight(int number, String src, String depart, String dest, String arrive) {
    this.number = number;

    src = src.toUpperCase();
    if (src.length() != 3 || !src.matches("[A-Z]+")) {
      System.err.println("The source has to contain the three-letter code of departure airport!");
      throw new IllegalArgumentException();
    }
    if(this.air_names.getName(src) == null) {
      System.err.println("The source airport does not exist!");
      throw new IllegalArgumentException();
    }
    this.src = src;

    if(!validateTime(depart)) {
      System.err.println("The departure date and time is in the wrong format! Must be mm/dd/yyyy hh:mm am_pm");
      throw new IllegalArgumentException();
    }
    this.depart = depart;

    dest = dest.toUpperCase();
    if (dest.length() != 3 || !dest.matches("[A-Z]+")) {
      System.err.println("The destination has to contain the three-letter code of departure airport!");
      throw new IllegalArgumentException();
    }
    if(this.air_names.getName(dest) == null) {
      System.err.println("The destination airport does not exist!");
      throw new IllegalArgumentException();
    }
    this.dest = dest;

    if(!validateTime(arrive)) {
      System.err.println("The arrival date and time is in the wrong format! Must be mm/dd/yyyy hh:mm am_pm");
      throw new IllegalArgumentException();
    }
    this.arrive = arrive;

    try {
      this.setDateAndTime();
    } catch (ParseException e) {
      System.err.println("The date and time is in the wrong format! Must be mm/dd/yyyy hh:mm am_pm");
      throw new IllegalArgumentException();
    }

    if(this.arrive_date.getTime() - this.depart_date.getTime() <= 0) {
      System.err.println("Error: The arrival time is earlier than the departure time or they are the same!");
      throw new IllegalArgumentException();
    }

  }

  /**
   * Returns the flight number
   */
  @Override
  public int getNumber() {
    return this.number;
  }

  /**
   * Returns the three-letter code of departure airport
   */
  @Override
  public String getSource() {
    return this.src;
  }

  /**
   * Returns the Departure date and time (24-hour time) represented as a String
   */
  @Override
  public String getDepartureString() {
    DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.US);
    return dateFormat.format(this.depart_date);
  }

  public String getDepartureToSave() {
    return this.depart;
  }

  public String getArrivalToSave() {
    return this.arrive;
  }

  /**
   * Returns the three-letter code of arrival airport
   */
  @Override
  public String getDestination() {
    return this.dest;
  }

  /**
   * Returns the arrival date and time (24-hour time) represented as a String
   */
  @Override
  public String getArrivalString() {
    DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.US);
    return dateFormat.format(this.arrive_date);
  }

  @Override
  public Date getDeparture() {
    return this.depart_date;
  }

  @Override
  public Date getArrival() {
    return this.arrive_date;
  }

  @Override
  public int compareTo(Flight flight) {
    if(this.getSource().equals(flight.getSource())) {
      long dif = this.depart_date.getTime() - flight.getDeparture().getTime();
      if (dif < 0)
        return -1;
      else if (dif == 0)
        return 0;
      else
        return 1;
    }

    return this.getSource().compareTo(flight.getSource());
  }
}
