package model;
/**
 * Created by Thomas Kearsley on 8/22/17.
 */
public class PublicPOI extends POI {

  public PublicPOI(double location[], String name, String description) {
    super(location, name, description);
  }


  public String toString() {
    return "Attraction: " + name + " " + description;
  }


}