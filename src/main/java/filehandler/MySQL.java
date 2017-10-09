package filehandler;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import model.Analyst;
import model.PublicPOI;
import model.Retailer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.lang.Exception;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Cyclist;
import model.Hotspot;
import helper.PasswordStorage;
import model.Route;
import model.Station;
import model.UserPOI;

/**
 * The class MySQL defines the type which queries the MySQL Database
 */
public class MySQL {
  // TODO: Optimisation by initialising one connection at beginning.
  /**
  public static void main(String[] args) throws Exception {

    EMPTY DATABASE CODE
    PreparedStatement stmt = conn.prepareStatement("TRUNCATE Stations");
    stmt.executeUpdate();
    PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM Stations");
    stmt2.executeUpdate();
  } **/



  /**
   * Inserts Retailer into Database. Mainly used for intial load of 700+ Retailers
   * But can also be used if a user wants to add databases later
   * @param conn Connection to Server
   * @param retailer Retailer Object
   * @throws Exception Exception thrown if insertion was unsuccessful.
   */
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


      inserted.executeUpdate(); //UPDATE = SEND, QUERY = Retrieve
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      System.out.println("Inserted retailer to MySQL database");
    }

  }



  // DATA INSERTING

  /**
   * Inserts Public POI into database
   * @param publicPOI Public POI Object
   * @throws Exception Exception thrown if insert is unsuccessful.
   */
  public static void insertPublicPOI(PublicPOI publicPOI) throws Exception {
    try {
      Connection conn = getConnection();
      PreparedStatement inserted = conn.prepareStatement(
          "INSERT INTO PublicPOI (Longitude,Latitude,Name,Description) VALUES (?,?,?,?)");
      inserted.setDouble(1,publicPOI.getLongitude());
      inserted.setDouble(2,publicPOI.getLatitude());
      inserted.setString(3,publicPOI.getName());
      inserted.setString(4,publicPOI.getDescription());
      inserted.executeUpdate(); //UPDATE = SEND QUERY = Retrieve
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      System.out.println("Inserted publicPOI to MySQL database");
    }

  }
  public static void insertUserPOI(UserPOI userPOI) throws Exception {
    try {
      Connection conn = getConnection();
      PreparedStatement inserted = conn.prepareStatement(
          "INSERT INTO UserPOI (Longitude,Latitude,Name,Description) VALUES (?,?,?,?)");
      inserted.setDouble(1,userPOI.getLongitude());
      inserted.setDouble(2,userPOI.getLatitude());
      inserted.setString(3,userPOI.getName());
      inserted.setString(4,userPOI.getDescription());
      inserted.executeUpdate(); //UPDATE = SEND QUERY = Retrieve
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      System.out.println("Insert Completed");
    }

  }

  public static void insertStation(Connection conn,Station station) {
    try {
      PreparedStatement insert = conn.prepareStatement("INSERT INTO Stations(stationID,name,availableDocks,"
          + "totalDocks,latitude,longitude,statusValue,statusKey,availableBikes,"
          + "streetAddress1,streetAddress2,postalCode,location,testStation,lastCommunicationTime,"
          + "landMark,altitude) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

      insert.setInt(1,station.getID());
      insert.setString(2,station.getName());
      insert.setInt(3,station.getAvailableDocs());
      insert.setInt(4,station.getTotalDocks());
      insert.setDouble(5,station.getLatitude());
      insert.setDouble(6,station.getLatitude());
      insert.setString(7,station.getStatusValue());
      insert.setInt(8,station.getStatusKey());
      insert.setInt(9,station.getAvailableBikes());
      insert.setString(10,station.getStreetAddress1());
      insert.setString(11,station.getStreetAddress2());
      insert.setString(12,station.getPostalCode());
      insert.setString(13,station.getLocation());
      int isTestStation = 0; /** 0 = Not Test Station, 1 = is Test Station **/
      if (station.isTestStation()) {
        isTestStation = 1; /** IS Test Station set to true. **/
      }
      insert.setInt(14,isTestStation);
      LocalDate dateForm = station.getLastCommunicationTime();
      String LastCommunicationTime = dateForm.toString();
      insert.setString(15,LastCommunicationTime);
      insert.setString(16,station.getLandMark());
      insert.setString(17,station.getAltitude());
      insert.executeUpdate();


    } catch(Exception e) {
      System.out.println(e);
    } finally {
      System.out.println("Insert Completed.");
    }



  }



  public static void insertRoute(Connection conn,Route route,String username){
    try {
      PreparedStatement insert = conn.prepareStatement("INSERT INTO RouteHistory (duration,startDate,"
          + "stopDate,startName,endName,bikeID,birthYear,gender,username) VALUES (?,?,?,?,?,?,?,?,?)");
      insert.setInt(1,route.getDuration());
      Date sDate = route.getStartDate(); // CONVERSION FOR DATABASE.
      String startDate = sDate.toString();
      Date eDate = route.getStopDate();
      String endDate = eDate.toString();
      insert.setString(2,startDate);
      insert.setString(3,endDate);
      insert.setString(4,route.getStartStationName());
      insert.setString(5,route.getStopStationName());
      insert.setInt(6,route.getBikeID());
      insert.setInt(7,route.getBirthYear());
      insert.setInt(8,route.getGender());
      insert.setString(9,username);
      insert.executeUpdate();



    } catch(Exception e) {
      System.out.println(e);
    } finally {
      System.out.println("Inserted route to MySQL database");
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
      System.out.println("Inserted hotspot to MySQL database");
    }
  }

  /**
   * Takes a Cyclist object and inserts the username,password
   * birthdate,gender weight and height into the database.
   * userType: Cyclist = 0, Analyst = 1.
   * @param cyclist Cyclist object given.
   */
  public static void insertCyclist(Cyclist cyclist){
    try {
      int cyclistID = 0;
      LocalDate dob = cyclist.getDOB();
      String strDOB = dob.toString();
      Connection conn = getConnection();
      PreparedStatement inserted = conn.prepareStatement(
          "INSERT INTO Users (username,password,birthDate,gender,weight,height,firstName,lastName,"
              + "userType) VALUES (?,?,?,?,?,?,?,?,?)");
      inserted.setString(1,cyclist.getUsername());
      PasswordStorage p = new PasswordStorage();
      String hashedPassword = p.createHash(cyclist.getPassword());
      inserted.setString(2,hashedPassword);
      inserted.setString(3,strDOB);
      inserted.setInt(4,cyclist.getGender());
      inserted.setDouble(5,cyclist.getWeight());
      inserted.setInt(6,cyclist.getHeight());
      inserted.setString(7,cyclist.getFirstName());
      inserted.setString(8,cyclist.getLastName());
      inserted.setInt(9,cyclistID);

      inserted.executeUpdate();
    }
    catch (Exception e) {
      System.out.println(e);
    } finally {
      System.out.println("Inserted cyclist to MySQL database");
    }
  }

  /**
   * Takes the given username and password of a Analyst object and appends them to the
   * database. userType: 1 = Analyst, 0 = Cyclist.
   * @param analyst Analyst object.
   */
  public static void insertAnalyst(Analyst analyst) {
    try {
      int analystIdentifier = 1; // IDENTIFIER FOR ANALYST is 1.
      Connection conn = getConnection();
      PasswordStorage p = new PasswordStorage();
      String hashedPassword = PasswordStorage.createHash(analyst.getPassword());
      PreparedStatement inserted = conn.prepareStatement(
          "INSERT INTO Users (username,password,userType) VALUES(?,?,?)");
      inserted.setString(1,analyst.getUsername());
      inserted.setString(2,hashedPassword);
      inserted.setInt(3,analystIdentifier);
      inserted.executeUpdate();
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      System.out.println("Inserted analyst to MySQL database");
    }
  }



  // DATA RETRIEVAL


  /**
   * Retrieves the longitude and latitude of a specific Public POI Location
   *
   * @return Returns an array of the longitude and latitude if found, else returns null
   * @throws Exception Exception is thrown if an error occurs with the database.
   */
  public static ArrayList<Hotspot> getHotspots(Connection conn) throws Exception {
    try {
      PreparedStatement statement = conn.prepareStatement("SELECT longitude,latitude,LocationAddress,"
          + "city,postcode,type,SSID,borough,remarks,provider,name FROM Hotspots");

      ResultSet result = statement.executeQuery();

      ArrayList<Hotspot> hotspots = new ArrayList<Hotspot>();
      while (result.next()) {
        Hotspot hotspot = new Hotspot(1,result.getDouble("latitude"),result.getDouble("longitude"),
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
    System.out.println("ERROR: Hotspot record was not found in MySQL database.");
    return null;
  }

  public static Station getStation(Connection conn,String name) {
    LocalDate lastCommunicationTime = LocalDate.now();
    try {
      PreparedStatement statement = conn.prepareStatement(" SELECT stationID,name,availableDocks,totalDocks,"
          + "latitude,longitude,statusValue,statusKey,availableBikes,streetAddress1,streetAddress2,city,"
          + "postalCode,location,testStation,lastCommunicationTime,landMark,altitude FROM Stations");
      ResultSet result = statement.executeQuery();
      while (result.next())
        if (result.getString("name").equals(name)) {
          Boolean testStation = false;
          if (result.getInt("testStation") == 1) {
            testStation = true;
          }
          //LocalDate lastCommunicationTime = LocalDate.parse(result.getString("lastCommunicationTime"));
          Station station = new Station(result.getInt("stationID"), result.getString("name"),
              result.getInt("availableDocks"), result.getInt("totalDocks"),
              result.getDouble("latitude"), result.getDouble("longitude"),
              result.getString("statusValue"), result.getInt("statusKey"),
              result.getInt("availableBikes"), result.getString("streetAddress1"),
              result.getString("streetAddress2"), result.getString("city"),
              result.getString("postalCode"), result.getString("location"),
              result.getString("altitude"), testStation, lastCommunicationTime,
              result.getString("landMark"));
          return station;
        }
    } catch(Exception e) {
      System.out.println(e);
    }
    return null;
  }

  public static ArrayList<Station> getStations(Connection conn) throws Exception{
    try {
      ArrayList<Station> stations = new ArrayList<Station>();
      PreparedStatement statement = conn.prepareStatement("SELECT stationID,name,availableDocks,"
          + "totalDocks,latitude,longitude,statusValue,statusKey,availableBikes,streetAddress1,"
          + "streetAddress2,city,postalCode,location,testStation,lastCommunicationTime,landMark,"
          + "altitude FROM Stations");
      ResultSet result = statement.executeQuery();
      LocalDate lastCommunicationTime  = LocalDate.now();
      /**ID, String name, int availableDocks, int totalDocks, double latitude,
      double longitude, String statusValue, int statusKey, int availableBikes,
      String streetAddress1, String streetAddress2, String city, String postalCode, String location,
          String altitude, boolean testStation, Date lastCommunicationTime, String landMark **/
      while (result.next()) {
        Boolean testStation = false;
        if (result.getInt("testStation") == 1) {
          testStation = true;
        }
        //LocalDate lastCommunicationTime = LocalDate.parse(result.getString("lastCommunicationTime"));
        Station station = new Station(result.getInt("stationID"),result.getString("name"),
            result.getInt("availableDocks"),result.getInt("totalDocks"),
            result.getDouble("latitude"),result.getDouble("longitude"),
            result.getString("statusValue"),result.getInt("statusKey"),
            result.getInt("availableBikes"),result.getString("streetAddress1"),
            result.getString("streetAddress2"),result.getString("city"),
            result.getString("postalCode"),result.getString("location"),
            result.getString("altitude"),testStation,lastCommunicationTime,
            result.getString("landMark"));
        stations.add(station);
      }
      return stations;
    }
    catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  public static ArrayList<Route> getAllRoutes(Connection conn) throws Exception {
    try {
      PreparedStatement statement = conn.prepareStatement("SELECT username,duration,startDate,stopDate,"
          + "startName,endName,bikeID,birthYear,gender FROM RouteHistory");
      ResultSet result = statement.executeQuery();
      ArrayList<Route> routes = new ArrayList<Route>();
      while (result.next()) {
          int duration = result.getInt("duration");
          SimpleDateFormat format = new SimpleDateFormat("E MMM dd H:mm:ss z yyyy");
          Date startDate = format.parse(result.getString("startDate"));
          Date stopDate = format.parse(result.getString("stopDate"));
          //String strDate = result.getString("startDate");
          //String stopDate = result.getString("stopDate");
          /** START STATION **/
          String startName = result.getString("startName");
          Station startStation = getStation(conn,startName);
          /** END STATION **/
          String stopName = result.getString("endName");
          Station stopStation = getStation(conn,stopName);
          int bikeID = result.getInt("bikeID");
          int birthYear = result.getInt("birthYear");
          int gender = result.getInt("gender");
          Route route = new Route(duration,startDate,stopDate,startStation,stopStation,bikeID,"",
              birthYear,gender);
          routes.add(route);
      } return routes;
    } catch (Exception e) {
      System.out.println(e);
    }
    System.out.println("Record was not found.");
    return null;
  }

  public static ArrayList<Route> getPastRoutes(Connection conn,String username) throws Exception {
    try {
      PreparedStatement statement = conn.prepareStatement("SELECT username,duration,startDate,stopDate,"
          + "startName,endName,bikeID,birthYear,gender FROM RouteHistory");
      ResultSet result = statement.executeQuery();
      ArrayList<Route> routes = new ArrayList<Route>();
      while (result.next()) {
        //int duration, Date startDate, Date stopDate, Station startStation,Station stopStation, int bikeID, String userType, int birthYear, int gender
        if (result.getString("username").equals(username)) {
          int duration = result.getInt("duration");
          SimpleDateFormat format = new SimpleDateFormat("E MMM dd H:mm:ss z yyyy");
          Date startDate = format.parse(result.getString("startDate"));
          Date stopDate = format.parse(result.getString("stopDate"));
          //String strDate = result.getString("startDate");
          //String stopDate = result.getString("stopDate");
          /** START STATION **/
          String startName = result.getString("startName");
          Station startStation = getStation(conn,startName);
          /** END STATION **/
          String stopName = result.getString("endName");
          Station stopStation = getStation(conn,stopName);
          int bikeID = result.getInt("bikeID");
          int birthYear = result.getInt("birthYear");
          int gender = result.getInt("gender");
          Route route = new Route(duration,startDate,stopDate,startStation,stopStation,bikeID,"",
              birthYear,gender);
          routes.add(route);


        }
      } return routes;
    } catch (Exception e) {
    System.out.println(e);
  }
    System.out.println("Record was not found.");
    return null;
  }


  public static ArrayList<Retailer> getRetailers(Connection conn) throws Exception {
    try {
      PreparedStatement statement = conn.prepareStatement("SELECT name,address,longitude,latitude,"
          + "floor,city,zipCode,state,block,secondaryDescription FROM Retailers");

      ResultSet result = statement.executeQuery();

      ArrayList<Retailer> retailers = new ArrayList<Retailer>();
      while (result.next()) {

        String name = result.getString("name");
        String address = result.getString("address");
        Double longitude = result.getDouble("longitude");
        Double latitude = result.getDouble("latitude");
        String floor = result.getString("floor");
        String city = result.getString("city");
        Integer zipCode = result.getInt("zipCode");
        String state = result.getString("state");
        String block = result.getString("block");
        String secondaryDescription = result.getString("secondaryDescription");
        Retailer retailer = new Retailer(name,address,floor,city,state,zipCode,
        block,secondaryDescription,"",latitude,longitude);
        retailers.add(retailer);


      }
      return retailers;


    } catch (Exception e) {
      System.out.println(e);
    }
    System.out.println("ERROR: Retailer record was not found in MySQL database.");
    return null;
  }

  /**
   * Takes a username parameter and returns the corresponding Cyclist object (password still hashed)
   * @param username username of desired cyclist object in database.
   * @return Cyclist object
   */
  public static Cyclist getCyclist(String username){
    try {
      Connection conn = getConnection();
      PreparedStatement statement = conn.prepareStatement("SELECT firstName,lastName,username,password,gender,height,"
          + "weight,birthDate,userType FROM Users");
      ResultSet result = statement.executeQuery();
      while (result.next()){
        if (result.getString("username").equals(username)) {
          String firstName = result.getString("firstName");
          String lastName = result.getString("lastName");
          String queryUsername = result.getString("username");
          String password = result.getString("password");
          String strDOB =  result.getString("birthDate");
          LocalDate birthDate = LocalDate.parse(strDOB);
          int gender = result.getInt("gender");
          int height = result.getInt("height");
          double weight = result.getDouble("weight");
          int userType = result.getInt("userType");
          Cyclist returnCyclist = new Cyclist(firstName,lastName,queryUsername,password,birthDate,gender,weight,height);
          return returnCyclist;
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    System.out.println("ERROR: Cyclist was not found in MySQL database.");
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
            isCyclist = true;
            LoginResult.add(isCyclist);
          }//CYCLIST
          else {
            LoginResult.add(isCyclist);
          }
          String RealPassword = result.getString("password");
          PasswordStorage p = new PasswordStorage();
          if (p.verifyPassword(password,RealPassword)) {
            successfulLogin = true;
            LoginResult.add(successfulLogin);
            System.out.println("Successfully logged in via MySQL");
            return LoginResult;
          } else {
            System.out.println("ERROR: Password is incorrect");
            LoginResult.add(successfulLogin);
            return LoginResult;
          }
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    if (!usernameExists) {
      System.out.println("ERROR: Username does not exist on MySQL database!");
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
      System.out.println("Connection established to MySQL database");
      return conn;
    } catch (Exception e) {
      System.out.println(e);
    }
    System.out.println("ERROR: Failed to establish connection to MySQL database");
    return null;
  }

}
