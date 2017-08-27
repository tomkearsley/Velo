package model;
/**
 * Created by Thomas Kearsley on 8/22/17.
 *
 */
public class UserPOI extends POI{
  public UserPOI(double location[],String name,String description){
    super(location,name,description);
  }



  public String toString(){
    return "Your saved location is: " + name + " " + description;
  }

  /**
   * Sets the description of the User POI.
   * @param description Desired description of User POI.
   */
  public void setDescription(String description) {
    this.description = description;
  }
}
