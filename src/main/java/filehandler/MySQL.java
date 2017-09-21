package filehandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.lang.Exception;
import java.sql.PreparedStatement;

/**
 * The class MySQL defines the type which queries the MySQL Database
 */
public class MySQL {




  public static void PublicPOIInsert(double longitude,double latitude,String name,String description) throws Exception{
    try {
      Connection conn = getConnection();
      PreparedStatement inserted = conn.prepareStatement("INSERT INTO PublicPOI (Longitude,Latitude,Name,Description) VALUES ("+longitude+","+latitude+",'"+name+"','"+description+"')");
      inserted.executeUpdate(); //UPDATE = SEND QUERY = Retrieve
    } catch(Exception e) {System.out.println(e);}
    finally {
      System.out.println("Insert Completed");
    }

  }

  public static void HotspotsInsert(double longitude,double latitude,String identifier) {
    try {
      Connection conn = getConnection();
      PreparedStatement inserted = conn.prepareStatement("INSERT INTO Hotspots (longitude,latitude,identifier) VALUES ("+longitude+","+latitude+",'"+identifier+"')");
      inserted.executeUpdate();
    } catch(Exception e) {System.out.println(e);}
    finally {
      System.out.println("Insert Completed");
    }
  }


  public static void UserInsert(String username,String password) {
    try {
      Connection conn = getConnection();
      PreparedStatement inserted = conn.prepareStatement("INSERT INTO Users (username,password) VALUES ('"+username+"','"+password+"')");
      inserted.executeUpdate();
    } catch(Exception e) {System.out.println(e);}
    finally {
      System.out.println("Insert Completed");
    }
  }


  public static Connection getConnection() throws Exception{
    try{
      String driver = "com.mysql.jdbc.Driver";
      String url = "jdbc:mysql://velodb-database.cigiei3mw3rc.us-east-1.rds.amazonaws.com:3306/Velo Schema";
      String username = "admin";
      String password = "seng202team9";
      Class.forName(driver);

      Connection conn = DriverManager.getConnection(url,username,password);
      System.out.println("Connected");
      return conn;
    } catch(Exception e){System.out.println(e);}


    return null;
  }

}
