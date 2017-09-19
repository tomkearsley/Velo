package filehandler;

import java.io.BufferedReader;
import java.io.FileReader;

import model.Hotspot;
import model.Retailer;
import model.POI;
import model.Route;
import model.Station;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * The class Reader defines the object type Reader
 * It is used to read data from text files
 */
public class Reader {

  //TODO remove OpenCSV once all reader tests verified working with comma issues

  /**
   * Finds commas embedded in quotes and adds the index of such commas to a list
   * @param string The string to find quoted commas within
   * @return An ArrayList of integers - The comma indexes
   */
  public ArrayList<Integer> findQuotedCommas(String string) {
    boolean quoteReached = false;
    ArrayList<Integer> locs = new ArrayList<Integer>();
    for (int i=0; i<string.length(); i++) {
      if (string.charAt(i) == '"') {
        quoteReached = !quoteReached;
      }
      if (quoteReached && string.charAt(i) == ',') {
        locs.add(new Integer(i));
      }
    }
    return locs;
  }

  /**
   * Replaces the commas embedded in quotes with a space to keep the string length constant while allowing
   * for proper splitting
   * @param string The string to be adjusted
   * @param locs The list of offending comma indexes
   * @return The string after replacing offending commas
   */
  public String changeQuotedCommas(String string, ArrayList<Integer> locs) {
    String newString = "";
    int locCount = 0;
    for (int i=0; i<string.length(); i++) {
      if (locCount < locs.size() && i == locs.get(locCount)) {
        locCount++;
        newString += " ";
      } else {
        newString += string.charAt(i);
      }
    }
    return newString;
  }

  /**
   * Replaces the temporary space character with the original commas after splitting by valid commas
   * Thus allowing splitting of a string by valid commas while allowing commas in the final fields
   * @param csv The list of strings from a POI
   * @param locs The list of offending comma indexes
   * @return The list of strings with original commas replaced
   */
  public String[] replaceQuotedCommas(String[] csv, ArrayList<Integer> locs) {
    int charCount = 0;
    int locCount = 0;
    for (int i=0; i<csv.length; i++) {
      String newString = "";
      for (int o=0; o<csv[i].length(); o++) {
        if (locCount < locs.size() && charCount == locs.get(locCount)) {
          newString += ',';
          locCount++;
        } else {
          newString += csv[i].charAt(o);
        }
        charCount++;
      }
      csv[i] = newString;
      charCount++;
    }
    return csv;
  }

  /**
   * Removes double quotes from Strings that are embedded within them, as they were only functional for dealing
   * with non-splitting commas within the csv
   * @param csv The csv to search through
   * @return The csv with border quotes removed
   */
  public String[] removeBorderQuotes(String[] csv) {
    for (int i=0; i<csv.length; i++) {
      if (csv[i].length() > 0 && csv[i].charAt(0) == '"' && csv[i].charAt(csv[i].length() - 1) == '"') {
        csv[i] = csv[i].substring(1, csv[i].length() - 1);
      }
    }
    return csv;
  }


  /**
   * Reads WiFi hotspots from a csv file
   * Uses BufferedReader
   *
   * TODO Needs to be tested to ensure it works.
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
        ArrayList<Integer> locs = findQuotedCommas(line);
        line = changeQuotedCommas(line, locs);
        String[] csvHotspot = line.split(",", -1);
        csvHotspot = replaceQuotedCommas(csvHotspot, locs);
        csvHotspot = removeBorderQuotes(csvHotspot);

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
        ArrayList<Integer> locs = findQuotedCommas(line);
        line = changeQuotedCommas(line, locs);
        String[] csvRetailer = line.split(",", -1);
        csvRetailer = replaceQuotedCommas(csvRetailer, locs);
        csvRetailer = removeBorderQuotes(csvRetailer);

        // Set Retailer attributes from the buffered row
        String title = csvRetailer[titleIndex];
        String address = csvRetailer[addressIndex];
        String floor = csvRetailer[floorIndex];
        String city = csvRetailer[cityIndex];
        String state = csvRetailer[stateIndex];
        int zipcode;
        try {
          zipcode = Integer.valueOf(csvRetailer[zipcodeIndex]);
        } catch(Exception e) {
          zipcode = -1;
        }
        if(csvRetailer.length < 7) {
          for (int i=0; i < csvRetailer.length; i++){
            System.out.print(i + ": " + csvRetailer[i]);
          }
          System.out.println();
        }
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
    ArrayList<POI> POIs = new ArrayList<POI>();

    try {

      br = new BufferedReader(new FileReader(filename));
      while ((line = br.readLine()) != null) {
        // Separate by comma
        ArrayList<Integer> locs = findQuotedCommas(line);
        line = changeQuotedCommas(line, locs);
        String[] csvPOI = line.split(",", -1);
        csvPOI = replaceQuotedCommas(csvPOI, locs);
        csvPOI = removeBorderQuotes(csvPOI);

        //Get name of POI
        String name = csvPOI[nameIndex];

        //Get location values
        double latitude = Double.valueOf(csvPOI[latitudeIndex]);
        double longitude = Double.valueOf(csvPOI[longitudeIndex]);

        String description = csvPOI[descriptionIndex];

        POIs.add(new POI(latitude, longitude, name,description));
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
    return POIs;
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