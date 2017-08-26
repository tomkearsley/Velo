package model;
/**
 * Created by Thomas Kearsley on 8/22/17.
 */
public class PublicPOI extends POI{
  String description = "";

  public PublicPOI(double location[], String name, String description) {
    super(location,name);
  }

  /**
   * Sets the description of the Public POI.
   * @param description Desired description for Public POI
   */
  public void setDescription(String description) {
    this.description = description;
  }
}
