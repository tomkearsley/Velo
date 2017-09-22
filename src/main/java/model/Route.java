package model;
import java.time.LocalDate;
import java.time.LocalTime;
import model.Station;
import java.util.Date;

/**
 * The class Route defines the object type for bicycle routes
 */
public class Route {


  /**The trip duration in seconds*/
  private int duration;

  /**The time the route was started*/
  private LocalDate startDate;
  private LocalTime startTime;

  /**The time the route was stopped*/
  private LocalDate stopDate;
  private LocalTime stopTime;

  /**Start station*/
  private Station startStation;

  /**Stop station*/
  private Station stopStation;

  /**Bike ID*/
  private int bikeID;

  /**User type
   * Either "customer" or "subscriber"
   */
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

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate newStartDate) {
    startDate = newStartDate;
  }
  
  public LocalTime getStartTime() { return startTime; }
  
  public void setStartTime(LocalTime newStartTime) { startTime = newStartTime; }

  public LocalDate getstopDate() {
    return stopDate;
  }

  public void setstopDate(LocalDate newstopDate) {
    stopDate = newstopDate;
  }

  public LocalTime getstopTime() { return stopTime; }

  public void setstopTime(LocalTime newstopTime) { stopTime = newstopTime; }

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

  public Route(int duration, LocalDate startDate, LocalTime startTime, LocalDate stopDate, LocalTime stopTime,
      Station startStation, Station stopStation, int bikeID, String userType, int birthYear, int gender) {
    this.duration = duration;
    this.startDate = startDate;
    this.startTime = startTime;
    this.stopDate = stopDate;
    this.stopTime = stopTime;
    this.startStation = startStation;
    this.stopStation = stopStation;
    this.bikeID = bikeID;
    this.userType = userType;
    this.birthYear = birthYear;
    this.gender = gender;
  }

  public String toString(){
    return "Route duration: " + duration + "startDateTime: " + startDate + " " + startTime + "stopDateTime: " +
        stopDate + " " + stopTime + "startStation: " + startStation + "stopStation: " + stopStation + "bikeID: " +
        bikeID + "userType: " + userType + "birthYear: " + birthYear + "gender: " + gender;
  }

}
