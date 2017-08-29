package model;

/**
 * The class Retailer describes the attributes and methods of a Retail store object
 */
public class Retailer implements model.Mappable {
  /** The title of the retailer */
  private String title;

  /** The Street no + Road name */
  private String address;

  /** The Floor (Concourse, Third floor) */
  private String floor;

  /** The city in which the retailer is located */
  private String city;

  /** The retailer's state location */
  private String state;

  /** The zipcode for the retailer's area */
  private int zipcode;

  /** The block that contains the Retailer */
  private String block;

  /** The primary description for the store */
  private String description;

  /** The secondary description for the store */
  private String secondaryDescription;


  //Methods

  public Retailer(String title, String address, String floor, String city, String state, int zipcode, String block, String description, String secondaryDescription) {
    this.title = title;
    this.address = address;
    this.floor = floor;
    this.city = city;
    this.state = state;
    this.zipcode = zipcode;
    this.block = block;
    this.description = description;
    this.secondaryDescription = secondaryDescription;
  }

  public String toString(){
    return "Retailer: " + title + " Address: " + address + ", " + floor + ", " + block
        + ", " + city + ", " + state + ", " + Integer.toString(zipcode) + " Description(s): "
        + description + ", " + secondaryDescription;
  }

  public boolean equals(Retailer r){
    return this.toString().equals(r.toString());
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String newTitle) { title = newTitle; }

  public String getAddress() {
    return address;
  }

  public void setAddress(String newAddress) { address = newAddress; }

  public String getFloor() { return floor; }

  public void setFloor(String newFloor) { floor = newFloor; }

  public String getCity() { return city; }

  public void setCity(String newCity) { city = newCity; }

  public String getState() { return state; }

  public void setState(String newState) { state = newState; }

  public int getZipcode() { return zipcode; }

  public void setZipcode(int newZipcode) { zipcode = newZipcode; }

  public String getBlock() { return block; }

  public void setBlock(String newBlock) { block = newBlock; }

  public String getDescription() {
    return description;
  }

  public void setDescription(String newDescription) { description = newDescription; }

  public String getSecondaryDescription() {
    return secondaryDescription;
  }

  public void setSecondaryDescription(String newSecondaryDescription) { secondaryDescription = newSecondaryDescription; }
}
