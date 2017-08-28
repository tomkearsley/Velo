package model;

/**
 * Created by Thomas Kearsley on 8/22/17.
 */
public class UserPOI extends POI {

  public UserPOI(double location[], String name, String description) {
    super(location, name, description);
  }


  public String toString() {
    return "Your saved location is: " + name + " " + description;
  }


}