package model;
import model.Station;
import java.util.Date;

/**
 * The class Route defines the object type for bicycle routes
 */
public class Route {


  /**The trip duration in seconds*/
  private int duration;

  /**The time the route was started*/
  private Date startDateTime;

  /**The time the route was stopped*/
  private Date stopDateTime;

  /**Start station*/
  private Station startStation;

  /**Stop station*/
  private Station stopStation;

  /**Bike ID*/
  private int bikeID;

  /**User type*/
  private String userType;

  /**User birth year*/
  private int birthYear;

  /**User gender
   * 0 - male
   * 1 - female
   * 2 - not specified
   * */
  private int gender;


  //Methods
  public int getDuration() {
    return duration;
  }

  public void setDuration(int newDuration) {
    duration = newDuration;
  }

  public Date getStartDateTime() {
    return startDateTime;
  }

  public void setStartDateTime(Date newStartDateTime) {
    startDateTime = newStartDateTime;
  }

  public Date getStopDateTime() {
    return stopDateTime;
  }

  public void setStopDateTime(Date newStopDateTime) {
    stopDateTime = newStopDateTime;
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

  public Route(int duration, Date startDateTime, Date stopDateTime, Station startStation,
      Station stopStation, int bikeID, String userType, int birthYear, int gender) {
    this.duration = duration;
    this.startDateTime = startDateTime;
    this.stopDateTime = stopDateTime;
    this.startStation = startStation;
    this.stopStation = stopStation;
    this.bikeID = bikeID;
    this.userType = userType;
    this.birthYear = birthYear;
    this.gender = gender;
  }

  public String toString(){
    return "Route duration: " + duration + "startDateTime: " + startDateTime + "stopDateTime: " +
        stopDateTime + "startStation: " + startStation + "stopStation: " + stopStation + "bikeID: " +
        bikeID + "userType: " + userType + "birthYear: " + birthYear + "gender: " + gender;
  }

}
