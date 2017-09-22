package model;

/**
 * The class Hotspot defines the type for WiFi hotspot locations
 */
public class Hotspot extends POI {

  /**Hotspot ObjectID from csv file*/
  private int id;

  private double latitude;

  private double longitude;

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

  /** Name of hotspot
   * Different than the user-facing SSID!
   */
  private String name;

  /**ISP providing internet service to the hotspot*/
  private String provider;

  /** Comments on the hotspot*/
  private String remarks;


  /** Full constructor */
  public Hotspot(int id, double latitude, double longitude, String locationAddress, String borough,
      String city, int postcode, String type, String SSID, String name, String provider, String remarks) {
    super(name, remarks);
    this.id = id;
    this.latitude = latitude;
    this.longitude = longitude;
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
  public String toString(){

    return "ID: " + Integer.toString(id) + ", Latitude: " + Double.toString(latitude) +
        ", Longitude: " + Double.toString(longitude) + ", Address: " + locationAddress +
        ", Borough: " + borough + ", City: " + city + ", Postcode: " + Integer.toString(postcode) +
        ", Type: " + type + ", SSID: " + SSID + ", Name: " + name + ", Provider: " + provider +
        ", Remarks: " + remarks;
  }

  /** Used for the assert functions in JUnit testing
   *
   * @param hotspotToTest is the hotspot to be tested
   * @return boolean
   */
  public boolean equals(Hotspot hotspotToTest){
    return this.toString().equals(hotspotToTest.toString());
  }

  public int getID() {
    return id;
  }

  public void setID(int newID) {
    id = newID;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double newLatitude) {
    latitude = newLatitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double newLongitude) {
    longitude = newLongitude;
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
