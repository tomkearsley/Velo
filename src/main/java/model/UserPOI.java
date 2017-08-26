package model;
/**
 * Created by Thomas Kearsley on 8/22/17.
 *
 */
public class UserPOI extends POI{
  String description = "";
  public UserPOI(double location[],String name,String description){
    super(location,name);
  }

  /**
   * Sets the description of the User POI.
   * @param description Desired description of User POI.
   */
  public void setDescription(String description) {
    this.description = description;
  }
}
