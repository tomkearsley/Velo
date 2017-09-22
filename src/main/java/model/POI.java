package model;

import java.util.Arrays;

/**
 * The class POI defines the superclass for Points of Interest
 */
public class POI implements Mappable {

  String name = "";
  String description = "";

  public POI(String name, String description) {
    this.name = name;
    this.description = description;

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

  @Override
  public String toString() {
    return "Name: " + name + "\nLocation: " + longitude + ", " + latitude + "\nDescription: " + description;
  }

  @Override
  public boolean equals(Object p) {
    return this.toString().equals(p.toString());
  }
}



