package model;

/**
 * The class PublicPOI defines the object type for Public Points of Interest
 */
public class PublicPOI extends POI {

  private double latitude;
  private double longitude;


  public PublicPOI(double longitude, double latitude, String name, String description) {
    super(name, description);
    this.latitude = latitude;
    this.longitude = longitude;
  }


  public String toString() {
    return "Attraction: " + name + " " + description;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double newLatitude) {
    latitude = newLatitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double newLongitude) {
    longitude = newLongitude;
  }
}