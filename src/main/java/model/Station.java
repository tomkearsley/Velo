package model;

public class Station {

  /**Station ID*/
  private int ID;

  /**Station name*/
  private String name;

  /**Station latitude*/
  private double latitude;

  /**Station longitude*/
  private double longitude;


  //Methods
  public int getID() {
    return ID;
  }

  public void setID(int newID) {
    ID = newID;
  }

  public String getNme() {
    return name;
  }

  public void setName(String newName) {
    name = newName;
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


  //Constructors
  public Station(int ID, String name, double latitude, double longitude) {
    this.ID = ID;
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
  }

}
