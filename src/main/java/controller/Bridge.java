package controller;

import model.Hotspot;

public class Bridge {
  public String atest(String test) {
    //System.out.println("Bridge succesfull - Methods done");
    return test;
  }

  public String testFunction2(String test) {
    System.out.println("Function 2 works");
    return "test1";
  }

  public void testValue(String test) {
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