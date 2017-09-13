package model;

import java.util.Arrays;

/**
 * Created by Thomas Kearsley on 8/22/17. Checked by Tyler Kennedy on 8/29/17.
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

  public POI(String name, String description){
    this.name = name;
    this.description = description;
  }

  /**
   * Sets the location of the POI
   * @param longitude
   * @param latidude
   */
  public void setLocation(double longitude, double latidude)
  {
    this.longitude = longitude;
    this.latidude = latidude;
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



