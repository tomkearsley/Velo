package helper;

public enum DataType {
  HOTSPOT("Hotspot"),
  RETAILER("Retailer"),
  PUBLICPOI("Public POI"),
  USERPOI("User POI"),
  STATION("Station"),
  ROUTE("Route");
  private String type;

  DataType(String type) {
    this.type = type;
  }

  public String Type() {
    return type;
  }
}