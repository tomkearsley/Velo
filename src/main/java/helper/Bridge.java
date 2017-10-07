package helper;

import static filehandler.Google.stringToLocation;

import model.Hotspot;
import model.POI;
import model.Retailer;
import model.Station;

public class Bridge {

  public void outputValue(String test) { System.out.println(test); }

  public String getHotspotTitle(Hotspot hotspot) {
    return hotspot.getRemarks();
  }

  public String getStationTitle(Station station) { return station.getName(); }

  public String getRetailerTitle(Retailer retailer) { return retailer.getDescription();}

  public Double getLat(POI poi) { return poi.getLatitude();}

  public Double getLng(POI poi) {return poi.getLongitude();}

  public String getTitle(POI poi) {return poi.getDescription();}

  public double[] getDisplayRouteArray(String location) {return stringToLocation(location);}

  public String locationToString(Double[] location) {return locationToString(location); }
}