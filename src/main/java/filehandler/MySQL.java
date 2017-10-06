package filehandler;

import java.time.LocalDate;
import model.Analyst;
import model.Retailer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.lang.Exception;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Retailer;
import model.Cyclist;
import model.Hotspot;
import sun.rmi.runtime.Log;

/**
 * The class MySQL defines the type which queries the MySQL Database
 */
public class MySQL {

  /**
  public static void main(String[] args) throws Exception {

    Reader rdr = new Reader();
    Connection conn = getConnection();
    ArrayList <Retailer> retailers = rdr.readRetailers("/file/InitialRetailers.csv", false);
    int size = retailers.size();
    for (int i = 11; i<size; i++) {
      insertRetailer(conn,retailers.get(i));
    }

  } **/

  public static void insertRetailer(Connection conn,Retailer retailer)  throws Exception{
    try {
      PreparedStatement inserted = conn.prepareStatement("INSERT INTO Retailers (name,address,"
          + "longitude,latitude,floor,city,zipCode,state,block,secondaryDescription) VALUES (?,?,?,?,?,?,?"
          + ",?,?,?)");
      inserted.setString(1,retailer.getName());
      inserted.setString(2,retailer.getAddress());
      inserted.setDouble(3,retailer.getLongitude());
      inserted.setDouble(4,retailer.getLatitude());
      inserted.setString(5,retailer.getFloor());
      inserted.setString(6,retailer.getCity());
      inserted.setInt(7,retailer.getZipcode());
      inserted.setString(8,retailer.getState());
      inserted.setString(9,retailer.getBlock());
      inserted.setString(10,retailer.getSecondaryDescription());


      inserted.executeUpdate(); //UPDATE = SEND QUERY = Retrieve
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      System.out.println("Insert Completed");
    }

  }


  // DATA INSERTING

  /**
   * Inserts a new Public POI Point into the database
   *
   * @param longitude Longitude of POI Point
   * @param latitude Latitude of POI Point
   * @param name Name given for Public POI
   * @param description Short description of POI
   * @throws Exception Thrown if insert fails, too many values, incorrect format ect.
   */
  public static void insertPublicPOI(double longitude, double latitude, String name,
      String description) throws Exception {
    try {
      Connection conn = getConnection();
      PreparedStatement inserted = conn.prepareStatement(
          "INSERT INTO PublicPOI (Longitude,Latitude,Name,Description) VALUES (" + longitude + ","
              + latitude + ",'" + name + "','" + description + "')");
      inserted.executeUpdate(); //UPDATE = SEND QUERY = Retrieve
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      System.out.println("Insert Completed");
    }

  }

  /**
   * Inserts a hotspot into the database.
   * @param conn Connection to server. Reduces Insert time by initialising one connection.
   * @param hotspot Given Hotspot object
   */
  public static void insertHotspot(Connection conn,Hotspot hotspot) {
    try {
      PreparedStatement insert = conn.prepareStatement("INSERT INTO Hotspots (longitude,latitude,LocationAddress,city,postcode,type,"
              + "SSID,borough,remarks,provider,name) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
      insert.setDouble(1,hotspot.getLongitude());
      insert.setDouble(2,hotspot.getLatitude());
      insert.setString(3,hotspot.getLocationAddress());
      insert.setString(4,hotspot.getCity());
      insert.setInt(5,hotspot.getPostcode());
      insert.setString(6,hotspot.getType());
      insert.setString(7,hotspot.getSSID());
      insert.setString(8,hotspot.getBorough());
      insert.setString(9,hotspot.getRemarks());
      insert.setString(10,hotspot.getProvider());
      insert.setString(11,hotspot.getName());
      insert.executeUpdate();
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      System.out.println("Insert Completed");
    }
  }

  /**
   * Takes a Cyclist object and inserts the username,password
   * birthdate,gender weight and height into the database.
   * @param cyclist Cyclist object given.
   */
  public static void insertCyclist(Cyclist cyclist){
    try {
      Connection conn = getConnection();
      PreparedStatement inserted = conn.prepareStatement(
          "INSERT INTO Users (username,password,birthDate,gender,weight,height) VALUES "
              + "('" + cyclist.getUsername() + "','" + cyclist.getPassword() + "',"
              + ""+ cyclist.getDOB() + ", "+ cyclist.getGender() +", "+ cyclist.getWeight() +", "+ cyclist.getHeight() +")");
      inserted.executeUpdate();
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      System.out.println("Insert Completed");
    }
  }

  /**
   * Takes the given username and password of a Analyst object and appends them to the
   * database.
   * @param analyst Analyst object.
   */
  public static void insertAnalyst(Analyst analyst) {
    try {
      Connection conn = getConnection();
      PreparedStatement inserted = conn.prepareStatement(
          "INSERT INTO Users (username,password) VALUES "
              + "('" + analyst.getUsername() + "','" + analyst.getPassword() + "')");
      inserted.executeUpdate();
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      System.out.println("Insert Completed");
    }
  }



  // DATA RETRIEVAL

  /**
   * Retrieves the longitude and latitude of a specific Public POI Location
   *
   * @return Returns an array of the longitude and latitude if found, else returns null
   * @throws Exception Exception is thrown if an error occurs with the database.
   */
  public static ArrayList<Hotspot> getHotspots() throws Exception {
    try {
      Connection conn = getConnection();
      PreparedStatement statement = conn.prepareStatement("SELECT longitude,latitude,LocationAddress,"
          + "city,postcode,type,SSID,borough,remarks,provider,name FROM Hotspots");

      ResultSet result = statement.executeQuery();

      ArrayList<Hotspot> hotspots = new ArrayList<Hotspot>();
      while (result.next()) {
        Hotspot hotspot = new Hotspot(1,result.getDouble("longitude"),result.getDouble("latitude"),
            result.getString("LocationAddress"),result.getString("borough"),
            result.getString("city"),result.getInt("postcode"),result.getString("type"),
            result.getString("SSID"),result.getString("name"),
            result.getString("provider"),result.getString("remarks"));
        hotspots.add(hotspot);


      }
      return hotspots;


    } catch (Exception e) {
      System.out.println(e);
    }
    System.out.println("Record was not found.");
    return null;
  }


  /**
   * Used within the login screen returns true if login is successful otherwise false. Additionally
   * tells if username exists
   *
       * @param username Users entered username
   * @param password Users entered password
   * @return Boolean Array List with two booleans, 1. If the User is a Cyclist 2. If the login was
   * sucessful
   * @throws Exception
   */
  public static ArrayList<Boolean> login(String username, String password) throws Exception {
    Connection conn = getConnection();
    boolean usernameExists = false;
    boolean successfulLogin = false;
    boolean isCyclist = false;
    ArrayList<Boolean> LoginResult = new ArrayList<Boolean>();

    try {
      PreparedStatement statement = conn.prepareStatement("SELECT username,password,userType FROM Users");

      ResultSet result = statement.executeQuery();
      while (result.next()) {
        //CHECK USERNAME EXISTS
        if (result.getString("username").equals(username)) {
          usernameExists = true;
          if(result.getInt("userType") == 0){
            System.out.println("going here");
            isCyclist = true;
            LoginResult.add(isCyclist);
          }//CYCLIST
          else {
            LoginResult.add(isCyclist);
          }
          if (result.getString("password").equals(password)) {
            successfulLogin = true;
            LoginResult.add(successfulLogin);
            System.out.println("Successfully Logged in");
            return LoginResult;
          } else {
            System.out.println("Password is incorrect");
            LoginResult.add(successfulLogin);
            return LoginResult;
          }
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    if (usernameExists == false) {
      System.out.println("Username does not exist!");
      LoginResult.add(isCyclist);
      LoginResult.add(successfulLogin);
    }
    LoginResult.add(isCyclist);
    LoginResult.add(successfulLogin);
    return LoginResult;
  }

  /**
   * Creates a connection between Computer and database. Connects to the Velo Schema.
   *
   * @return Returns null
   * @throws Exception Exception is thrown if connection fails.
   */
  public static Connection getConnection() throws Exception {
    try {
      String driver = "com.mysql.jdbc.Driver";
      String url = "jdbc:mysql://velodb-database.cigiei3mw3rc.us-east-1.rds.amazonaws.com:3306/Velo Schema";
      String username = "admin";
      String password = "seng202team9";
      Class.forName(driver);

      Connection conn = DriverManager.getConnection(url, username, password);
      System.out.println("Connected");
      return conn;
    } catch (Exception e) {
      System.out.println(e);
    }

    return null;
  }

}
