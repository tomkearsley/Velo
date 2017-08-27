package model;
/**
 * Created by Thomas Kearsley on 8/22/17.
 */
public class PublicPOI extends POI{

  public PublicPOI(double location[], String name, String description) {
    super(location,name,description);
  }


  public String toString(){
    return "Attraction: " + name + " " + description;
  }

  /**
   * Sets the description of the Public POI.
   * @param description Desired description for Public POI
   */
  public void setDescription(String description) {
    this.description = description;
  }
}
