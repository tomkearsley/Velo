package model;

/**
 * The class Hotspot defines the type for WiFi hotspot locations
 */
public class Hotspot extends POI {

  /**Hotspot ObjectID from csv file*/
  private int id;

  /** The address of the hotspot*/
  private String locationAddress;

  /**Particular region the hotspot is located i.e. Brooklyn*/
  private String borough;

  /**City in which the hotspot is located*/
  private String city;

  /**Postal code*/
  private int postcode;

  /**Type of service i.e. free, limited free*/
  private String type;

  /** WiFi hotspot name to the user i.e. "UCWireless"*/
  private String SSID;

  /**ISP providing internet service to the hotspot*/
  private String provider;

  /** Comments on the hotspot*/
  private String remarks;


  /** Full constructor
   * @param id ID of hotspot
   * @param latitude Latitude coordinate of hotspot
   * @param longitude Longitude coordinate of hotspot
   * @param locationAddress Address of hotspot
   * @param borough  Borough where hotspot is located
   * @param city City where hotspot is located
   * @param postcode Postcode of hotspot
   * @param type Type of hotspot
   * @param SSID Name of the wifi being broadcast
   * @param name Name of the actual physical device
   * @param provider Name of the internet-provider
   * @param remarks Remarks about the hotspot
   * */
  public Hotspot(int id, double latitude, double longitude, String locationAddress, String borough,
      String city, int postcode, String type, String SSID, String name, String provider, String remarks) {
    super(name, remarks, latitude, longitude);
    this.id = id;
    this.locationAddress = locationAddress;
    this.borough = borough;
    this.city = city;
    this.postcode = postcode;
    this.type = type;
    this.SSID = SSID;
    this.provider = provider;
  }

  /*Hotspot methods*/

  /** Print method for Hotspot
   * @return String
   */
  @Override
  public String toString() {
    System.out.println(locationAddress);
    String address = locationAddress + (borough.equals("") ? "" : ", " + borough) + ", " + Integer.toString(postcode);
    return "ID:\t\t\t" + Integer.toString(id) + "\nName:\t\t" + getName() + "\nSSID:\t\t" + SSID +
        "\nProvider:\t\t" + provider + "\nRemarks:\t\t" + getDescription() + "\nAddress:\t\t" + address +
        "\nCity:\t\t\t" + city + "\nCoordinates:\t" + String.format("(%.3f, %.3f)", getLatitude(), getLongitude());
  }

//  public String toString(){
//
//    return "ID: " + Integer.toString(id) + ", Latitude: " + getLatitude() +
//        ", Longitude: " + getLongitude() + ", Address: " + locationAddress +
//        ", Borough: " + borough + ", City: " + city + ", Postcode: " + Integer.toString(postcode) +
//        ", Type: " + type + ", SSID: " + SSID + ", Name: " + name + ", Provider: " + provider +
//        ", Remarks: " + remarks;
//  }

//  /** Used for the assert functions in JUnit testing
//   *
//   * @param hotspotToTest is the hotspot to be tested
//   * @return boolean
//   */
//  public boolean equals(Hotspot hotspotToTest){
//    return this.toString().equals(hotspotToTest.toString());
//  }

  public int getID() {
    return id;
  }

  public void setID(int newID) {
    id = newID;
  }

  public String getLocationAddress() {
    return locationAddress;
  }

  public void setLocationAdress(String newLocationAddress) {
    locationAddress = newLocationAddress;
  }

  public String getBorough() {
    return borough;
  }

  public void setBorough(String newBorough) {
    borough = newBorough;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String newCity) {
    city = newCity;
  }

  public int getPostcode() {
    return postcode;
  }

  public void setPostcode(int newPostcode) {
    postcode = newPostcode;
  }

  public String getType() {
    return type;
  }

  public void setType(String newType) {
    type = newType;
  }

  public String getSSID() {
    return SSID;
  }

  public void setSSID(String newSSID) {
    SSID = newSSID;
  }

  public String getProvider() {
    return provider;
  }

  public void setProvider(String newProvider) {
    provider = newProvider;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String newRemarks) {
    remarks = newRemarks;
  }

}
