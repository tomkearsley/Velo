package filehandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import model.Hotspot;
import model.POI;
import model.Retailer;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import model.Route;
import model.Station;

/**
 * The class Reader is used to load files and data
 */

public class Reader {

  /**
   * Reads WiFi hotspots from a csv file
   *
   * <p> Needs to be tested to ensure it works. </p>
   *
   * @param filename the name of file to open
   * @return ArrayList<Hotspot> Hotspots
   * @throws FileNotFoundException if the file cannot be found
   */
  public ArrayList<Hotspot> readHotspots(String filename) throws FileNotFoundException {

    // Column indexes for the appropriate value per row
    int idIndex = 0;
    int typeIndex = 3;
    int providerIndex = 4;
    int nameIndex = 5;
    int locationAddressIndex = 6;
    int latitudeIndex = 7;
    int longitudeIndex = 8;
    int remarksIndex = 12;
    int cityIndex = 13;
    int ssidIndex = 14;
    int boroughIndex = 19;
    int postcodeIndex = 22;

    // Initialize scanner and Hotspots array
    BufferedReader br = null;
    String line;
    ArrayList<Hotspot> Hotspots = new ArrayList<Hotspot>();

    try {

      br = new BufferedReader(new FileReader(filename));
      while ((line = br.readLine()) != null) {

        // Separate by comma
        String[] csvHotspot = line.split(",");

        // Get Hotspot attributes from the buffered row
        int id = Integer.valueOf(csvHotspot[idIndex]);
        Double latitude = Double.valueOf(csvHotspot[latitudeIndex]);
        Double longitude = Double.valueOf(csvHotspot[longitudeIndex]);
        String location = csvHotspot[locationAddressIndex];
        String borough = csvHotspot[boroughIndex];
        String city = csvHotspot[cityIndex];
        int postcode = Integer.valueOf(csvHotspot[postcodeIndex]);
        String type = csvHotspot[typeIndex];
        String ssid = csvHotspot[ssidIndex];
        String name = csvHotspot[nameIndex];
        String provider = csvHotspot[providerIndex];
        String remarks = csvHotspot[remarksIndex];

        // Create Hotspot with the attributes
        Hotspot newHotspot = new Hotspot(id, latitude, longitude, location, borough,
            city, postcode, type, ssid, name, provider, remarks);

        //add newHotspot to Hotspots array
        Hotspots.add(newHotspot);

      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return Hotspots;
  }

  /**
   * Reads retailers from a csv file
   *
   * <p> Needs to be tested to ensure it works. </p>
   *
   * @param filename the name of file to open
   * @return ArrayList<Retailer> Retailers
   * @throws FileNotFoundException if the file cannot be found
   */
  public ArrayList<Retailer> readRetailers(String filename) throws FileNotFoundException {

    // Column indexes for the appropriate value per row
    int titleIndex = 0;
    int addressIndex = 1;
    int floorIndex = 2;
    int cityIndex = 3;
    int stateIndex = 4;
    int zipcodeIndex = 5;
    int blockIndex = 6;
    int descriptionIndex = 7;
    int description2Index = 8;

    // Initialize scanner and Retailers array
    BufferedReader br = null;
    String line;
    ArrayList<Retailer> Retailers = new ArrayList<Retailer>();

    try {

      br = new BufferedReader(new FileReader(filename));
      while ((line = br.readLine()) != null) {
        // Separate by comma
        String[] csvRetailer = line.split(",", -1);

        // Set Retailer attributes from the buffered row
        String title = csvRetailer[titleIndex];
        String address = csvRetailer[addressIndex];
        String floor = csvRetailer[floorIndex];
        String city = csvRetailer[cityIndex];
        String state = csvRetailer[stateIndex];
        int zipcode = Integer.valueOf(csvRetailer[zipcodeIndex]);
        String block = csvRetailer[blockIndex];
        String description = csvRetailer[descriptionIndex];
        String secondaryDescription = csvRetailer[description2Index];

        //add newRetailer to Retailers array
        Retailers.add(new Retailer(title, address, floor, city, state, zipcode, block, description,
            secondaryDescription));

      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return Retailers;
  }

  public ArrayList<POI> readUserPOIS(String filename) throws FileNotFoundException {
    int nameIndex = 0;

    //Google uses latitude, longitude pairings.
    int latitudeIndex = 1;
    int longitudeIndex = 2;

    int descriptionIndex = 3;

    BufferedReader br = null;
    String line;
    ArrayList<POI> POIS = new ArrayList<POI>();

    try {

      br = new BufferedReader(new FileReader(filename));
      while ((line = br.readLine()) != null) {
        // Separate by comma
        String[] csvPOIS = line.split(",");

        //Get name of POI
        String name = csvPOIS[nameIndex];

        //Get location values
        System.out.print(csvPOIS[latitudeIndex]);
        double longitude = Double.valueOf(csvPOIS[latitudeIndex]);
        double latitude = Double.valueOf(csvPOIS[longitudeIndex]);

        //Get location array
        //double[] location = {longitude, latitude};

        String description = csvPOIS[descriptionIndex];

        POIS.add(new POI(latitude, longitude, name,description));
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return POIS;
  }


//  /**
//   * Reads routes from a csv file
//   *
//   * <p> Needs to be tested to ensure it works. </p>
//   *
//   * @param filename the name of file to open
//   * @return ArrayList<Route> Routes
//   * @throws FileNotFoundException if the file cannot be found
//   */
//  public ArrayList<Route> readRoutes(String filename) throws FileNotFoundException {
//
//    // Column indexes for the appropriate value per row
//    int durationIndex = 0;
//    int startDateTimeIndex = 1;
//    int stopDateTimeIndex = 2;
//
//    // Start Station
//    int startStationIDIndex = 3;
//    int startStationNameIndex = 4;
//    int startStationLatitudeIndex = 5;
//    int startStationLongitudeIndex = 6;
//
//    //Stop Station
//    int stopStationIDIndex = 7;
//    int stopStationNameIndex = 8;
//    int stopStationLatitudeIndex = 9;
//    int stopStationLongitudeIndex = 10;
//
//    int bikeIDIndex = 11;
//    int userTypeIndex = 12;
//    int birthYearIndex = 13;
//    int genderIndex = 14;
//
//
//    // Initialize scanner and Retailers array
//    BufferedReader br = null;
//    String line;
//    ArrayList<Route> Routes = new ArrayList<Route>();
//
//    try {
//
//      br = new BufferedReader(new FileReader(filename));
//      while ((line = br.readLine()) != null) {
//        // Separate by comma
//        String[] csvRoute = line.split(",");
//
//        // Set Route attributes from the buffered row
//        int duration = Integer.valueOf(csvRoute[durationIndex]);
//        Date startDateTime = csvRoute[startDateTimeIndex];
//        Date stopDateTime = csvRoute[stopDateTimeIndex];
//
//        Station startStation = new Station(Integer.valueOf(csvRoute[startStationIDIndex]),
//            String.valueOf(csvRoute[startStationNameIndex]),
//            Double.valueOf(csvRoute[startStationLatitudeIndex]),
//            Double.valueOf(csvRoute[startStationLongitudeIndex]));
//
//        Station stopStation = new Station(Integer.valueOf(csvRoute[stopStationIDIndex]),
//            String.valueOf(csvRoute[stopStationNameIndex]),
//            Double.valueOf(csvRoute[stopStationLatitudeIndex]),
//            Double.valueOf(csvRoute[stopStationLongitudeIndex]));
//
//        int bikeID = Integer.valueOf(csvRoute[bikeIDIndex]);
//        String userType = csvRoute[userTypeIndex];
//        int birthYear = Integer.valueOf(csvRoute[birthYearIndex]);
//        int gender = Integer.valueOf(csvRoute[genderIndex]);
//
//        //add newRetailer to Retailers array
//        Routes.add(new Route(duration, startDateTime, stopDateTime, startStation, stopStation, bikeID, userType, birthYear, gender));
//
//      }
//
//    } catch (FileNotFoundException e) {
//      e.printStackTrace();
//    } catch (IOException e) {
//      e.printStackTrace();
//    } finally {
//      if (br != null) {
//        try {
//          br.close();
//        } catch (IOException e) {
//          e.printStackTrace();
//        }
//      }
//    }
//
//    return Routes;
//  }
}