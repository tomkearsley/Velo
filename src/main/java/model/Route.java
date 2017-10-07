package model;

import java.util.ArrayList;
import java.util.Date;

/**
 * The class Route defines the object type for bicycle routes
 */
public class Route implements Mappable {


  /**
   * The trip duration in seconds
   */
  private int duration;

  /**
   * The time the route was started
   */
  private Date startDate;

  /**
   * The time the route was stopped
   */
  private Date stopDate;

  /**
   * ArrayList of POIs first and last should be stations
   */
  private ArrayList<POI> mapPoints;

  /**
   * Bike ID
   */
  private int bikeID;

  /**
   * User type Either "customer" or "subscriber"
   */
  private String userType;

  /**
   * User birth year
   */
  private int birthYear;

  /**
   * User gender 0 - male 1 - female 2 - not specified
   */
  private int gender;


  //Methods
  public int getDuration() {
    return duration;
  }

  public void setDuration(int newDuration) {
    duration = newDuration;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date newStartDate) {
    startDate = newStartDate;
  }

  public Date getStopDate() {
    return stopDate;
  }

  public void setStopDate(Date newstopDate) {
    stopDate = newstopDate;
  }

  public Station getStartStation() {
    if (mapPoints.get(0) instanceof Station) {
      Station start = (Station) mapPoints.get(0);
      return start;
    } else {
      return null;
    }
  }

  public void setStartStation(Station newStartStation) {
    mapPoints.set(0, newStartStation);
  }

  public Station getStopStation() {
    int len = mapPoints.size();
    if (mapPoints.get(len - 1) instanceof Station) {
      Station stop = (Station) mapPoints.get(len - 1);
      return stop;
    } else {
      return null;
    }
  }

  public void setStopStation(Station newStopStation) {
    int len = mapPoints.size();
    mapPoints.set(len - 1, newStopStation);
  }

  public int getBikeID() {
    return bikeID;
  }

  public void setBikeID(int newBikeID) {
    bikeID = newBikeID;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String newUserType) {
    userType = newUserType;
  }

  public int getBirthYear() {
    return birthYear;
  }

  public void setBirthYear(int newBirthYear) {
    birthYear = newBirthYear;
  }

  public int getGender() {
    return gender;
  }

  public void setGender(int newGender) {
    gender = newGender;
  }

  //Extra getters for tableview usability
  public String getStartStationName() {
    if (mapPoints.get(0) instanceof Station) {
      Station start = (Station) mapPoints.get(0);
      return start.getName();
    } else {
      return null;
    }
  }

  public String getStopStationName() {
    int len = mapPoints.size();
    if (mapPoints.get(len - 1) instanceof Station) {
      Station stop = (Station) mapPoints.get(len - 1);
      return stop.getName();
    } else {
      return null;
    }
  }
  //additional arrayList method should the be needed

  public ArrayList<POI> getMapPoints() {
    return mapPoints;
  }

  /**
   * sets the mapPoints arrayList of POIs to be the arrayList given ArrayList must be at least two
   * points long and the first and last points must be of type Station
   */
  public void setMapPoints(ArrayList<POI> mapPoints) {
    int len = mapPoints.size();
    if (len >= 2 && mapPoints.get(0) instanceof Station && mapPoints
        .get(len - 1) instanceof Station) {
      this.mapPoints = mapPoints;
    } else {
      System.out.println("First and last points must be stations");
    }
  }

  /**
   * Adds a POI in the second index of the mapPoints ArrayList
   */
  public void insertPointFirst(POI newPoint) {
    mapPoints.add(1, newPoint);
  }

  /**
   * Adds a POI in the second-last index of the mapPoints ArrayList If this is not possible, it will
   * be inserted as the second item (same as inserPointFirst
   */
  public void insertPointLast(POI newPoint) {
    int len = mapPoints.size();
    if (len - 2 != 0) {
      mapPoints.add(len - 1, newPoint);
    } else {
      mapPoints.add(1, newPoint);
    }
  }

  public Route(int duration, Date startDate, Date stopDate, Station startStation,
      Station stopStation,
      int bikeID, String userType, int birthYear, int gender) {
    this.duration = duration;
    this.startDate = startDate;
    this.stopDate = stopDate;
    this.mapPoints = new ArrayList<>(2);
    this.mapPoints.add(startStation);
    this.mapPoints.add(stopStation);
    this.bikeID = bikeID;
    this.userType = userType;
    this.birthYear = birthYear;
    this.gender = gender;
  }

  @Override
  public String toString() {
    try {
      int len = mapPoints.size();
      return "Start station:\t\t" + getStartStation().getName() + " (" + getStartStation().getID()
          + ")" +
          "\nStop station:\t\t" + getStopStation().getName() + " (" + getStopStation().getID() + ")"
          + "\nStart time:\t\t"
          + startDate + "\nStop time:\t\t" + stopDate + "\nRoute duration:\t" + duration
          + "\nBike ID:\t\t\t" + bikeID + "\nUser type:\t\t" + userType;
    } catch (Exception e) {
      e.printStackTrace();
      return "borked";
    }
  }

  @Override
  public boolean equals(Object route) {
    return this.toString().equals(route.toString());
  }
}
