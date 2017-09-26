package model;

/**
 * The class UserPOI defines the object type for User Points of Interest
 */
public class UserPOI extends POI {

  private double latitude;

  private double longitude;

  public UserPOI(double latitude,double longitude, String name, String description) {
    super(name, description);
    this.latitude = latitude;
    this.longitude = longitude;
  }


  public String toString() {
    return "Your saved location is: " + name + " " + description;
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