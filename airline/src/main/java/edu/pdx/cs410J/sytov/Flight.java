package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.AbstractFlight;

/**
 * Flight class implements AbstractFlight class.
 */
public class Flight extends AbstractFlight {

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
  private final String dest;
  private final String arrive;

  /**
   * Helper function for time validation
   * @param time is the String to be validated
   * @return true if the time is in valid format, false otherwise
   */
  public boolean validateTime(String time) {
    if (!time.matches("[/: 0-9]+"))
      return false;
    String[] tokens = time.split(" ");
    if (tokens.length != 2)
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
     Integer.parseInt(tokens_time[0]) < 0 || Integer.parseInt(tokens_time[0]) > 23 ||
     Integer.parseInt(tokens_time[1]) < 0 || Integer.parseInt(tokens_time[1]) > 59) {
      return false;
    }

    return true;
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
      throw new IllegalArgumentException("The source has to contain the three-letter code of departure airport!\n");
    }
    this.src = src;

    if(!validateTime(depart)) {
      throw new IllegalArgumentException("The departure date and time is in the wrong format! Must be dd/mm/yyyy hh:mm\n");
    }
    this.depart = depart;

    dest = dest.toUpperCase();
    if (dest.length() != 3 || !dest.matches("[A-Z]+")) {
      throw new IllegalArgumentException("The destination has to contain the three-letter code of departure airport!\n");
    }
    this.dest = dest;

    if(!validateTime(arrive)) {
      throw new IllegalArgumentException("The arrival date and time is in the wrong format! Must be dd/mm/yyyy hh:mm\n");
    }
    this.arrive = arrive;
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
    return this.depart;
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
    return this.arrive;
  }

}
