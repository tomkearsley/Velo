package model;

/**
 * The class PublicPOI defines the object type for Public Points of Interest
 */
public class PublicPOI extends POI {

  public PublicPOI(double latitude, double longitude, String name, String description) {
    super(name, description, latitude, longitude);
  }
}