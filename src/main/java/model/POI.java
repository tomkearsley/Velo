package model;

import java.util.Arrays;

/**
 * Created by Thomas Kearsley on 8/22/17. Checked by Tyler Kennedy on 8/29/17.
 */
public class POI implements Mappable {

  double location[] = new double[2];
  String name = "";
  String description = "";

  public POI(double location[], String name, String description) {
    this.location = location;
    this.name = name;
    this.description = description;

  }

  public POI(String name, String description){
    this.name = name;
    this.description = description;
  }

  /**
   * Sets the Location of the POI
   *
   * @param location GPS Co-ordinates of POI
   */
  public void setLocation(double location[]) {
    this.location = location;
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
   * Returns location of POI object.
   *
   * @return Location of POI Object.
   */
  public double[] getLocation() {
    return this.location;
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
    return "Name: " + name + "\nLocation: " + location[0] + ", " + location[1] + "\nDescription: " + description;
  }

  public boolean equals(POI p) {
    return this.toString().equals(p.toString());
  }
}



