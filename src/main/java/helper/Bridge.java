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

  public void outputValue(String test) { System.out.println(test); }

  public String getHotspotTitle(Hotspot hotspot) {
    return hotspot.getDescription();
  }

  public String getStationTitle(Station station) { return station.getName(); }

  public String getRetailerTitle(Retailer retailer) { return retailer.getDescription();}

  public Double getLat(POI poi) { return poi.getLatitude();}

  public Double getLng(POI poi) {return poi.getLongitude();}

  public String getTitle(POI poi) {return poi.getDescription();}

  public double[] getDisplayRouteArray(String location) {return stringToLocation(location);}

  public String locationToStringBridge(double lat,double lng) {return locationToString(lat,lng); }

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

  public double[] getRetailerLocation(Retailer retailer) {
    return stringToLocation(retailer.getAddress() + ", New york");
  }

  public void addUserPOI(double lat,double lng,String name,String description) {
    outputValue("Adding user POI");
  }

  public Route addRoute(double startLat,double startLng,double endLat,double endLng,double[][] waypoints) {
    double[] startLocation = {startLat,startLng};
    double[] endLocation = {endLat,endLng};

    return null; //Can't do this part
  }

  public String getRetailerHTML(Retailer retailer) {
    return "<h3>Retailer</h3><b>Name:</b> " + retailer.getName() + "<br><b>Address:</b> " + retailer.getAddress() + "<br><b>Description:</b> " + retailer.getSecondaryDescription() + "<br><b>Coordinates:</b> " + String.format("%.5g%n", retailer.getLatitude()) + "," + retailer.getLongitude();
  }

  public String getHotspotHTML(Hotspot hotspot) {
    return "<h3>Hotspot</h3><b>Name:</b> " + hotspot.getName() + "<br><b>Address:</b> " + hotspot.getLocationAddress() + "<br><b>Remarks:</b> " + hotspot.getDescription() + "<br><b>Coordinates:</b> " + String.format("%.5g%n", hotspot.getLatitude()) + "," + hotspot.getLongitude();
  }

  public String getPOIHTML(UserPOI userPOI) {
    return "<h3>User's Point of Interest</h3><b>Name:</b> " + userPOI.getName() + "<br><b>Address:</b> " + locationToString(userPOI.getLatitude(),userPOI.getLongitude()) + "<br><b>Description:</b> " + userPOI.getDescription() + "<br><b>Coordinates:</b> " + String.format("%.5g%n", userPOI.getLatitude()) + "," + userPOI.getLongitude();
  }

  public String getStationHTML(Station station) {
    return "<h3>Station</h3><b>Name:</b> " + station.getName() + "<br><b>Address:</b> " + station.getStreetAddress1() + "<br><b>Description:</b> " + station.getDescription() + "<br><b>Coordinates:</b> " + String.format("%.5g%n", station.getLatitude()) + "," + station.getLongitude();
  }

  public String getPPOIHTML(PublicPOI PPOI) {
    return "<h3>Public Point of Interest</h3><b>Name:</b> " + PPOI.getName() + "<br><b>Address:</b> " + locationToString(PPOI.getLatitude(),PPOI.getLongitude()) + "<br><b>Description:</b> " + PPOI.getDescription() + "<br><b>Coordinates:</b> " + String.format("%.5g%n", PPOI.getLatitude()) + "," + PPOI.getLongitude();
  }
}