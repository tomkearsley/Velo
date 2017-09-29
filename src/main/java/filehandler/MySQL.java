package filehandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.lang.Exception;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * The class MySQL defines the type which queries the MySQL Database
 */
public class MySQL {

  public static void main(String[] args) throws Exception {
    //getPublicPOILocation("Charging Bull");
    login("user1", "password1");
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
   * Inserts a Hotspot into the Hotspot Database
   *
   * @param longitude Longitude of Hotspot
   * @param latitude Latitude of Hotspot
   * @param identifier Hotspots Identifier. Words + number
   */
  public static void insertHotspot(double longitude, double latitude, String identifier) {
    try {
      Connection conn = getConnection();
      PreparedStatement inserted = conn.prepareStatement(
          "INSERT INTO Hotspots (longitude,latitude,identifier) VALUES (" + longitude + ","
              + latitude + ",'" + identifier + "')");
      inserted.executeUpdate();
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      System.out.println("Insert Completed");
    }
  }

  /**
   * Inserts users name and password into database. Currently not encrypted
   *
   * @param username Username for said user
   * @param password Password for given user
   */
  public static void insertUser(String username, String password) {
    try {
      Connection conn = getConnection();
      PreparedStatement inserted = conn.prepareStatement(
          "INSERT INTO Users (username,password) VALUES ('" + username + "','" + password + "')");
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
   * @param name Name of the POI used to identify POI
   * @return Returns an array of the longitude and latitude if found, else returns null
   * @throws Exception Exception is thrown if an error occurs with the database.
   */
  public static ArrayList<Double> getPublicPOILocation(String name) throws Exception {
    try {
      Connection conn = getConnection();
      PreparedStatement statement = conn
          .prepareStatement("SELECT longitude,latitude,name FROM PublicPOI");

      ResultSet result = statement.executeQuery();

      ArrayList<Double> location = new ArrayList<Double>();
      while (result.next()) {
        if (result.getString("Name").equals(name)) {
          System.out.println(result.getDouble("longitude"));
          System.out.println(result.getDouble("latitude"));

          location.add(result.getDouble("longitude"));
          location.add(result.getDouble("latitude"));
          return location;
        }

      }


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
   * @throws Exception In case connection falses due to unknown reasons
   * @return Boolean true if password and username is correct. Otherwise returns false
   */
  public static boolean login(String username, String password) throws Exception {
    boolean usernameExists = false;
    boolean successfulLogin = false;
    try {
      Connection conn = getConnection();
      PreparedStatement statement = conn.prepareStatement("SELECT username,password FROM Users");

      ResultSet result = statement.executeQuery();
      while (result.next()) {
        //CHECK USERNAME EXISTS
        if (result.getString("username").equals(username)) {
          usernameExists = true;
          if (result.getString("password").equals(password)) {
            successfulLogin = true;
            System.out.println("Successfully Logged in");
            return successfulLogin;
          } else {
            System.out.println("Password is incorrect");
            return successfulLogin;
          }
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    if (usernameExists == false) {
      System.out.println("Username does not exist!");
    }
    return successfulLogin;
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

  /** Breaks code
   public static void main(String[] args) throws Exception{
   getPublicPOILocation("Charging Bull");
   }
   */
}
