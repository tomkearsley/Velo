package seng202.team9.model;

/**
 * The class Retailer defines the type for retailers
 * @author Kyle Lamb
 */
public class Retailer implements Mappable {
  /** The title of the retailer */
  private String title;

  /** The retailer's address ([block], street no, street, [floor], city, state, ZIP) */
  private String address;

  /** The primary description for the store */
  private String description;

  /** The secondary description for the store */
  private String secondaryDescription;

  public Retailer(String title, String address, String description, String secondaryDescription) {
    this.title = title;
    this.address = address;
    this.description = description;
    this.secondaryDescription = secondaryDescription;
  }

  public String getTitle() {
    return title;
  }

  public String getAddress() {
    return address;
  }

  public String getDescription() {
    return description;
  }

  public String getSecondaryDescription() {
    return secondaryDescription;
  }
}
