package seng202.team9.Models;

import java.util.ArrayList;
import java.util.*;

/**
 * Created by andrewspearman on 8/23/17.
 * The class Hotspot defines the type for WiFi hotspot locations
 * @author andrewspearman
 */
public class Hotspot {

  /**Hotspot ObjectID from csv file*/
  private int id;

  private double latitude;

  private double longitude;

  /** The address of the hotspot*/
  private String locationAddress;

  /**Particular region the hotspot is located i.e. Brooklyn*/
  private String borough;

  /**City in which the hotspot is located*/
  private String city;

  /**Type of service i.e. free, limited free*/
  private String type;

  /** WiFi hotspot name to the user i.e. "UCWireless"*/
  private String SSID;

  /** Name of hotspot
   * Different than the user-facing SSID!
   */
  private String name;

  /**ISP providing internet service to the hotspot*/
  private String provider;

  /** Comments on the hotspot*/
  private String remarks;



  /*Hotspot methods*/

  public int getID() {
    return id;
  }

  public void setID(int newID) {
    id = newID;
  }

}
