package seng202.team9.Models;

/**
 * Created by andrewspearman on 8/23/17.
 * The class Retailer defines the type for retailers
 * @author Kyle Lamb
 */
public class Retailer {
  /** The title of the retailer */
  private String title;

  /** The retailer's address ([block], street no, street, [floor], city, state, ZIP) */
  private String address;

  /** The description for the store (Primary + Secondary) */
  private String description;

  public Retailer(){}

  public String getTitle(){
    return title;
  }

  public String getAddress(){
    return address;
  }

  public String getDescription(){
    return description;
  }
}
