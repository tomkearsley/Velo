package model;

/**
 * The class Retailer defines the object type of retailer locations
 */
public class Retailer extends POI {
  /** The street no + Road name */
  private String address;

  /** The floor (Concourse, Third floor) */
  private String floor;

  /** The city in which the retailer is located */
  private String city;

  /** The retailer's state location */
  private String state;

  /** The zipcode for the retailer's area */
  private int zipcode;

  /** The block that contains the Retailer */
  private String block;

  /** The secondary description for the store */
  private String secondaryDescription;


  //Methods

  public Retailer(String title, String address, String floor, String city, String state, int zipcode,
      String block, String description, String secondaryDescription) {
    super(title, description, null, null);
    this.address = address;
    this.floor = floor;
    this.city = city;
    this.state = state;
    this.zipcode = zipcode;
    this.block = block;
    this.secondaryDescription = secondaryDescription;
  }

  public Retailer(String title, String address, String floor, String city, String state, int zipcode,
      String block, String description, String secondaryDescription, Double latitude, Double longitude) {
    super(title, description, latitude, longitude);
    this.address = address;
    this.floor = floor;
    this.city = city;
    this.state = state;
    this.zipcode = zipcode;
    this.block = block;
    this.secondaryDescription = secondaryDescription;
    this.setLatitude(latitude);
    this.setLongitude(longitude);
  }

  @Override
  public String toString(){
    String coords = getLatitude() == null ? "Not stored" : String.format("(%.3f, %.3f)", getLatitude(), getLongitude());
    String address = getAddress() + (floor.equals("") ? "" : (", " + floor)) +  (block.equals("") ? "" : ", " + block)
        + ", " + Integer.toString(zipcode);
    return "Name:\t\t" + getName() + "\nAddress:\t\t" + address + "\nCity:\t\t\t" + city + "\nState:\t\t" + state +
        "\nDescription:\t" + getDescription() + ", " + secondaryDescription + "\nCoordinates:\t" + coords;
  }

//  @Override
//  public String toString() {
//    return "\nName: " + getName() + "\nDescription: " + getDescription() + "\nCoordinates: ("
//        + getLatitude() + "," + getLongitude() + ")";
//  }

//  //TODO add lat and long fields derived from street address using google api
//  public boolean equals(Retailer r){
//    return this.toString().equals(r.toString());
//  }

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

  public String getSecondaryDescription() {
    return secondaryDescription;
  }

  public void setSecondaryDescription(String newSecondaryDescription) { secondaryDescription = newSecondaryDescription; }
}
