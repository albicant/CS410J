package edu.pdx.cs410J.sytov;

import edu.pdx.cs410J.AbstractFlight;

public class Flight extends AbstractFlight {

  private final int number;
  private final String src;
  private final String depart;
  private final String dest;
  private final String arrive;

  private boolean validateTime(String time) {
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
     Integer.parseInt(tokens_time[0]) < 0 || Integer.parseInt(tokens_time[0]) > 24 ||
     Integer.parseInt(tokens_time[1]) < 0 || Integer.parseInt(tokens_time[1]) > 59) {
      return false;
    }

    return true;
  }

  public Flight(int number, String src, String depart, String dest, String arrive) {
    this.number = number;

    src = src.toUpperCase();
    if (src.length() != 3 || !src.matches("[A-Z]+")) {
      throw new IllegalArgumentException("The source has to contain the three-letter code of departure airport!");
    }
    this.src = src;

    if(!validateTime(depart)) {
      throw new IllegalArgumentException("The time is in the wrong format!");
    }
    this.depart = depart;

    dest = dest.toUpperCase();
    if (dest.length() != 3 || !dest.matches("[A-Z]+")) {
      throw new IllegalArgumentException("The destination has to contain the three-letter code of departure airport!");
    }
    this.dest = dest;

    if(!validateTime(arrive)) {
      throw new IllegalArgumentException("The time is in the wrong format!");
    }
    this.arrive = arrive;
  }

  @Override
  public int getNumber() {

    return this.number;
  }

  @Override
  public String getSource() {

//    throw new UnsupportedOperationException("This method is not implemented yet");
    return this.src;
  }

  @Override
  public String getDepartureString() {

//    throw new UnsupportedOperationException("This method is not implemented yet");
    return this.depart;
  }

  @Override
  public String getDestination() {

//    throw new UnsupportedOperationException("This method is not implemented yet");
    return this.dest;
  }

  @Override
  public String getArrivalString() {

//    throw new UnsupportedOperationException("This method is not implemented yet");
    return this.arrive;
  }
}
