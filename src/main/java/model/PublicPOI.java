package model;

/**
 * Created by Thomas Kearsley on 8/22/17.
 */
public class PublicPOI extends POI {

  public PublicPOI(double longitude, double latitude, String name, String description) {
    super(longitude, latitude, name, description);
  }


  public String toString() {
    return "Attraction: " + name + " " + description;
  }


}