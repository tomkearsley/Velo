package model;

/**
 * Created by Thomas Kearsley on 8/22/17.
 */
public class UserPOI extends POI {

  public UserPOI(double longitude,double latidude, String name, String description) {
    super(longitude,latidude, name, description);
  }


  public String toString() {
    return "Your saved location is: " + name + " " + description;
  }


}