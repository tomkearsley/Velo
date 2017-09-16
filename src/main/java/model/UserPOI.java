package model;

/**
 * The class UserPOI defines the object type for User Points of Interest
 */
public class UserPOI extends POI {

  public UserPOI(double longitude,double latidude, String name, String description) {
    super(longitude,latidude, name, description);
  }


  public String toString() {
    return "Your saved location is: " + name + " " + description;
  }


}