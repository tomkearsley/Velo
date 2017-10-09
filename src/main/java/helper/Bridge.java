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
    return "<h3>Retailer</h3>\n";
  }

  public String getHotspotHTML(Hotspot hotspot) {
    return "<h3>Hotspot</h3>";
  }

  public String getPOIHTML(UserPOI userPOI) {
    return "<h3>User's point of interest</h3>";
  }

  public String getStationHTML(Retailer retailer) {
    return "<h3>Station</h3>";
  }

  public String getPPOIHTML(PublicPOI POI) {
    return "<h3>Public point of interest</h3>";
  }
}