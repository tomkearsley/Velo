package model;

/**
 * Created by Thomas Kearsley on 8/22/17.
 */
public class POI implements Mappable{
  double location[] = new double[2];
  String name = "";
  String description = "";

  public POI(double location[],String name,String description) {
    this.location = location;
    this.name = name;
    this.description = description;

  }

  /**
   * Sets the Location of the POI
   * @param location GPS Co-ordinates of POI
   *
   */
  public void setLocation(double location[]){
    this.location = location;
  }

  /**
   * Sets the Name of the POI
   * @param name Desired name for POI
   */
  public void setName(String name) {
    this.name = name;
  }

}
