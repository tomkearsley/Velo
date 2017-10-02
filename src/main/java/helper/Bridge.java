package helper;

import model.Hotspot;

public class Bridge {

  public void outputValue(String test) {
    System.out.println(test);
  }

  public String getTitle(Hotspot hotspot) {
    return hotspot.getRemarks();
  }

  public Double getLat(Hotspot hotspot) {
    return hotspot.getLatitude();
  }

  public Double getLng(Hotspot hotspot) {
    return hotspot.getLongitude();
  }
}