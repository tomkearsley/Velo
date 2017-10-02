package model;

/**
 * The class PublicPOI defines the object type for Public Points of Interest
 */
public class PublicPOI extends POI {

  private double latitude;
  private double longitude;


  public PublicPOI(double latitude, double longitude, String name, String description) {
    super(name, description);
    this.latitude = latitude;
    this.longitude = longitude;
  }

  @Override
  public String toString() {
    return  "Name: " + this.getName() + " Location: " + latitude + "," + longitude +
    " Description: " + description;
  }

//  @Override
//  public boolean equals(Object poi) {
//    System.out.println(poi.toString());
//    return this.toString().equals(poi.toString());
//  }

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
}