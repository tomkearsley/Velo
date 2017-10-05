package model;

/**
 * The class UserPOI defines the object type for User Points of Interest
 */
public class UserPOI extends POI {

  public UserPOI(Double latitude,Double longitude, String name, String description) {
    super(name, description, latitude, longitude);
  }


  public String toString() {
    return "Your saved location is: " + getName() + " " + getDescription();
  }
}