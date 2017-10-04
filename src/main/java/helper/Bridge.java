package helper;

import static filehandler.Google.stringToLocation;

import model.Hotspot;
import model.Retailer;
import model.Station;

public class Bridge {

  public void outputValue(String test) { System.out.println(test); }

  public String getHotspotTitle(Hotspot hotspot) {
    return hotspot.getRemarks();
  }

  public Double getHotspotLat(Hotspot hotspot) {
    return hotspot.getLatitude();
  }

  public Double getHotspotLng(Hotspot hotspot) {
    return hotspot.getLongitude();
  }

  public String getStationTitle(Station station) { return station.getName(); }

  public Double getStationLat(Station station) { return station.getLatitude(); }

  public Double getStationLng(Station station) { return station.getLongitude(); }

  public double[] getRetailerLocation(Retailer retailer) { return stringToLocation(retailer.getAddress()); }

  public String getRetailerTitle(Retailer retailer) { return retailer.getDescription();}

  public double[] getDisplayRouteArray(String location) {return stringToLocation(location);}
}