package model;

import java.util.Arrays;

/**
 * The class POI defines the superclass for Points of Interest
 */
public class POI implements Mappable {

  private String name = "";
  private String description = "";
  private Double latitude;
  private Double longitude;


  public POI(String name, String description, Double latitude, Double longitude) {
    this.name = name;
    this.description = description;
    this.latitude = latitude;
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

  public Double getLatitude() {
    return this.latitude;
  }

  public Double getLongitude() {
    return this.longitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  @Override
  public String toString() {;
    return "Name: " + name + "\nDescription: " + description;
  }

  @Override
  public boolean equals(Object object) {
    return this.toString().equals(object.toString());
  }
}



