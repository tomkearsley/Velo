package helper;

import static filehandler.Google.stringToLocation;
import static filehandler.Google.locationToString;

import java.util.Date;
import model.Hotspot;
import model.POI;
import model.PublicPOI;
import model.Retailer;
import model.Route;
import model.Station;
import model.UserPOI;

public class Bridge {

  /**
   * Called by the window to alert. Simple test function that prints the input
   * @param test Prints this value
   */
  public void outputValue(String test) { System.out.println(test); }

  /**
   * Called by window to get the title for the marker to create for the given hotspot
   * @param hotspot The hotspot being passed in
   * @return Description Returns the description of a hotspot to set as the hovertext for the marker
   */
  public String getHotspotTitle(Hotspot hotspot) {
    return hotspot.getDescription();
  }

  /**
   * Called by window to get the title for the marker to create for the given station
   * @param station The station being passed in
   * @return Description Returns the description of a station to set as the hovertext for the marker
   */
  public String getStationTitle(Station station) { return station.getDescription(); }

  /**
   * Called by window to get the title for the marker to create for the given retailer
   * @param retailer The hotspot being passed in
   * @return Description Returns the description of a retailer to set as the hovertext for the marker
   */
  public String getRetailerTitle(Retailer retailer) { return retailer.getDescription();}

  /**
   * Called by window to get the latitude of a POI passed in
   * @param poi
   * @return latitude of the POI passed in
   */
  public Double getLat(POI poi) { return poi.getLatitude();}

  /**
   * Called by window to get the longitude of a POI passed in
   * @param poi
   * @return longitude of the POI passed in
   */
  public Double getLng(POI poi) {return poi.getLongitude();}

  /**
   * Called by window to get the description of a POI passed in
   * @param poi
   * @return decription of the POI passed in to set the title of the hoverMarker
   */
  public String getTitle(POI poi) {return poi.getDescription();}

  /**
   * Returns the lat,lng of a location
   * @param location
   * @return Returns the coordinates of a location as a double[]
   */
  public double[] getDisplayRouteArray(String location) {return stringToLocation(location);}

  /**
   * Converts a location as coordinates as a string
   * @param lat Latitude of point
   * @param lng Longitude of point
   * @return Returns the string address of a coordinate
   */
  public String locationToStringBridge(double lat,double lng) {return locationToString(lat,lng); }

  /**
   * Calculates the distance in m between two coordinates
   * @param startLat Start point latitude
   * @param startLng Start point longitude
   * @param endLat End point latitude
   * @param endLng End point longitude
   * @return Returns the distance in metres
   */
  public double getDistance(double startLat,double startLng,double endLat,double endLng) {

    int EARTH_RADIUS = 6371;
    double dLat  = Math.toRadians((endLat - startLat));
    double dLng = Math.toRadians((endLng - startLng));

    startLat = Math.toRadians(startLat);
    endLat   = Math.toRadians(endLat);

    double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(startLat) * Math.cos(endLat) * Math.pow(Math.sin(dLng / 2), 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return EARTH_RADIUS * c * 1000; // <-- d
  }

  /**
   * Gets the location of a retailer
   * @param retailer The retailer whose location to return
   * @return Returns the location of a retailer
   */
  public double[] getRetailerLocation(Retailer retailer) {
    return stringToLocation(retailer.getAddress() + ", New york");
  }

  /**
   * Adds a user POI to the data
   * @param lat Latitude of POI
   * @param lng Longitude of POI
   * @param name Name of POI
   * @param description Description of POI
   */
  public void addUserPOI(double lat,double lng,String name,String description) {
    outputValue("Adding user POI");
  }

  /**
   * Adds a route to the data
   * @param startLat Start point latitude
   * @param startLng Start point longitude
   * @param endLat End point latitude
   * @param endLng End point longitude
   * @param waypoints Arraylist of points that are in the waypoints
   * @return Returns the route to add to the table
   */
  public Route addRoute(double startLat,double startLng,double endLat,double endLng,double[][] waypoints) {
    double[] startLocation = {startLat,startLng};
    double[] endLocation = {endLat,endLng};

    return null; //Can't do this part because it relies on the coordinates being a station but that can't be guaranteed through the map -> This feature has not been implemented
  }

  /**
   * Gets a HTML description for a retailer
   * @param retailer The retailer to get a description for
   * @return The HTML description for a retailer as a String
   */
  public String getRetailerHTML(Retailer retailer) {
    return "<h3>Retailer</h3><b>Name:</b> " + retailer.getName() + "<br><b>Address:</b> " + retailer.getAddress() + "<br><b>Description:</b> " + retailer.getSecondaryDescription() + "<br><b>Coordinates:</b> " + String.format("%.5g%n", retailer.getLatitude()) + "," + retailer.getLongitude();
  }

  /**
   * Gets a HTML description for a hotspot
   * @param hotspot The retailer to get a description for
   * @return The HTML description for a hotspot as a String
   */
  public String getHotspotHTML(Hotspot hotspot) {
    return "<h3>Hotspot</h3><b>Name:</b> " + hotspot.getName() + "<br><b>Address:</b> " + hotspot.getLocationAddress() + "<br><b>Remarks:</b> " + hotspot.getDescription() + "<br><b>Coordinates:</b> " + String.format("%.5g%n", hotspot.getLatitude()) + "," + hotspot.getLongitude();
  }

  /**
   * Gets a HTML description for a userPOI
   * @param userPOI The userPOI to get a description for
   * @return The HTML description for a userPOI as a String
   */
  public String getPOIHTML(UserPOI userPOI) {
    return "<h3>User's Point of Interest</h3><b>Name:</b> " + userPOI.getName() + "<br><b>Address:</b> " + locationToString(userPOI.getLatitude(),userPOI.getLongitude()) + "<br><b>Description:</b> " + userPOI.getDescription() + "<br><b>Coordinates:</b> " + String.format("%.5g%n", userPOI.getLatitude()) + "," + userPOI.getLongitude();
  }

  /**
   * Gets a HTML description for a station
   * @param station The station to get a description for
   * @return The HTML description for a station as a String
   */
  public String getStationHTML(Station station) {
    return "<h3>Station</h3><b>Name:</b> " + station.getName() + "<br><b>Address:</b> " + station.getStreetAddress1() + "<br><b>Description:</b> " + station.getDescription() + "<br><b>Coordinates:</b> " + String.format("%.5g%n", station.getLatitude()) + "," + station.getLongitude();
  }

  /**
   * Gets a HTML description for a PublicPOI
   * @param PPOI The PublicPOI to get a description for
   * @return The HTML description for a PublicPOI as a String
   */
  public String getPPOIHTML(PublicPOI PPOI) {
    return "<h3>Public Point of Interest</h3><b>Name:</b> " + PPOI.getName() + "<br><b>Address:</b> " + locationToString(PPOI.getLatitude(),PPOI.getLongitude()) + "<br><b>Description:</b> " + PPOI.getDescription() + "<br><b>Coordinates:</b> " + String.format("%.5g%n", PPOI.getLatitude()) + "," + PPOI.getLongitude();
  }
}