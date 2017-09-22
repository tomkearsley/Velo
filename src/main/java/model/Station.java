package model;

import java.util.Date;

/**
 * The class Station defines the object type for CitiBike rental locations
 */
public class Station {

  /**Station ID*/
  private int ID;

  /**Station name*/
  private String stationName;

  /**Number of available docks*/
  private int availableDocs;

  /**Number of total docks*/
  private int totalDocks;

  /**Station latitude*/
  private double latitude;

  /**Station longitude*/
  private double longitude;

  /**Status of Station*/
  private String statusValue;

  /**Status of the Station as a key (Different int values have different meaning)*/
  private int statusKey;

  /**Number of available bikes*/
  private int availableBikes;

  /***/
  private String streetAddress1;

  /***/
  private String streetAddress2;

  /***/
  private int postalCode;

  /***/
  private String location;

  /***/
  private int altitude;

  /**Is a test station*/
  private boolean testStation;

  /***/
  private Date lastCommunicationTime;

  /**Name of landmark if it a landmark*/
  private String landMark;


  //Methods
  public int getID() {
    return ID;
  }

  public void setID(int newID) {
    ID = newID;
  }

  public String getName() {
    return stationName;
  }

  public void setName(String newName) {
    stationName = newName;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double newLatitude) {
    latitude = newLatitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double newLongitude) {
    longitude = newLongitude;
  }

  public int getAvailableDocs() {
    return availableDocs;
  }

  public void setAvailableDocs(int availableDocs) {
    this.availableDocs = availableDocs;
  }

  public int getTotalDocks() {
    return totalDocks;
  }

  public void setTotalDocks(int totalDocks) {
    this.totalDocks = totalDocks;
  }

  public String getStatusValue() {
    return statusValue;
  }

  public void setStatusValue(String statusValue) {
    this.statusValue = statusValue;
  }

  public int getStatusKey() {
    return statusKey;
  }

  public void setStatusKey(int statusKey) {
    this.statusKey = statusKey;
  }

  public int getAvailableBikes() {
    return availableBikes;
  }

  public void setAvailableBikes(int availableBikes) {
    this.availableBikes = availableBikes;
  }

  public String getStreetAddress1() {
    return streetAddress1;
  }

  public void setStreetAddress1(String streetAddress1) {
    this.streetAddress1 = streetAddress1;
  }

  public String getStreetAddress2() {
    return streetAddress2;
  }

  public void setStreetAddress2(String streetAddress2) {
    this.streetAddress2 = streetAddress2;
  }

  public int getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(int postalCode) {
    this.postalCode = postalCode;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public int getAltitude() {
    return altitude;
  }

  public void setAltitude(int altitude) {
    this.altitude = altitude;
  }

  public boolean isTestStation() {
    return testStation;
  }

  public void setTestStation(boolean testStation) {
    this.testStation = testStation;
  }

  public Date getLastCommunicationTime() {
    return lastCommunicationTime;
  }

  public void setLastCommunicationTime(Date lastCommunicationTime) {
    this.lastCommunicationTime = lastCommunicationTime;
  }

  public String getLandMark() {
    return landMark;
  }

  public void setLandMark(String landMark) {
    this.landMark = landMark;
  }

  //Constructors
  public Station(int ID, String name, double latitude, double longitude) {
    this.ID = ID;
    this.stationName = name;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public Station(int ID, String name, int availableDocs, int totalDocks, double latitude, double longitude, String statusValue, int statusKey, int availableBikes, String streetAddress1, String streetAddress2, int postalCode, String location, int altitude, boolean testStation, Date lastCommunicationTime, String landMark) {
    this.ID = ID;
    this.stationName = name;
    this.availableDocs = availableDocs;
    this.totalDocks = totalDocks;
    this.latitude = latitude;
    this.longitude = longitude;
    this.statusValue = statusValue;
    this.statusKey = statusKey;
    this.availableBikes = availableBikes;
    this.streetAddress1 = streetAddress1;
    this.streetAddress2 = streetAddress2;
    this.postalCode = postalCode;
    this.location = location;
    this.altitude = altitude;
    this.testStation = testStation;
    this.lastCommunicationTime = lastCommunicationTime;
    this.landMark = landMark;
  }
}
