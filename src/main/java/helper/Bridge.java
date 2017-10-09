package helper;

import static filehandler.Google.stringToLocation;
import static filehandler.Google.locationToString;

import model.Hotspot;
import model.POI;
import model.Retailer;
import model.Station;

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
    return stringToLocation(retailer.getAddress());
  }
}