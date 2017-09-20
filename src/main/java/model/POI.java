package model;

import java.util.Arrays;

/**
 * The class POI defines the superclass for Points of Interest
 */
public class POI implements Mappable {

  double longitude = 0;
  double latidude = 0;
  String name = "";
  String description = "";

  public POI(double longitude, double latidude, String name, String description) {
    this.longitude = longitude;
    this.latidude = latidude;
    this.name = name;
    this.description = description;

  }

  public POI(double[] location, String name, String description) {
    this.longitude = location[0];
    this.latidude = location[1];
    this.name = name;
    this.description = description;

  }

  public POI(String name, String description){
    this.name = name;
    this.description = description;
  }

  /**
   * Sets the location of the POI
   * @param latitude
   * @param longitude
   */
  public void setLocation(double latitude, double longitude)
  {
    this.latidude = latitude;
    this.longitude = longitude;
  }

  /**
   * Sets the description of the Public POI.
   *
   * @param description Desired description for Public POI
   */
  public void setDescription(String description) {
    this.description = description;
  }


  /**
   * Sets the Name of the POI
   *
   * @param name Desired name for POI
   */
  public void setName(String name) {
    this.name = name;
  }

  // GETTERS

  public double getLongitude() {
    return longitude;
  }

  public double getLatidude() {
    return latidude;
  }
  /**
   * Returns the name of POI Object
   *
   * @return Name of POI Object.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns the description of POI Object
   *
   * @return Description of POI Object
   */
  public String getDescription() {
    return this.description;
  }

  @Override
  public String toString() {
    return "Name: " + name + "\nLocation: " + longitude + ", " + latidude + "\nDescription: " + description;
  }

  public boolean equals(POI p) {
    return this.toString().equals(p.toString());
  }
}



