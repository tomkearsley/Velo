package model;

import java.time.LocalDate;
import java.time.LocalTime;
import model.Station;
import java.util.Date;

/**
 * The class Route defines the object type for bicycle routes
 */
public class Route implements Mappable{


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
   * Start station
   */
  private Station startStation;

  /**
   * Stop station
   */
  private Station stopStation;

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
    return startStation;
  }

  public void setStartStation(Station newStartStation) {
    startStation = newStartStation;
  }

  public Station getStopStation() {
    return stopStation;
  }

  public void setStopStation(Station newStopStation) {
    stopStation = newStopStation;
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
    return startStation.getName();
  }

  public String getStopStationName() {
    return stopStation.getName();
  }

  public Route(int duration, Date startDate, Date stopDate, Station startStation,
      Station stopStation,
      int bikeID, String userType, int birthYear, int gender) {
    this.duration = duration;
    this.startDate = startDate;
    this.stopDate = stopDate;
    this.startStation = startStation;
    this.stopStation = stopStation;
    this.bikeID = bikeID;
    this.userType = userType;
    this.birthYear = birthYear;
    this.gender = gender;
  }

  public String toString() {
    return "Route duration: " + duration + " startDateTime: " + startDate + " stopDateTime: " +
        stopDate + " startStation: " + startStation + " stopStation: " + stopStation + " bikeID: " +
        bikeID + " userType: " + userType + " birthYear: " + birthYear + " gender: " + gender;
  }

  @Override
  public boolean equals(Object route) {
    return this.toString().equals(route.toString());
  }
}
