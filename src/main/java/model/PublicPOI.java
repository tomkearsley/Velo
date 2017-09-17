package model;

/**
 * The class PublicPOI defines the object type for Public Points of Interest
 */
public class PublicPOI extends POI {

  public PublicPOI(double longitude, double latitude, String name, String description) {
    super(longitude, latitude, name, description);
  }


  public String toString() {
    return "Attraction: " + name + " " + description;
  }


}