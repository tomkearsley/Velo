package filehandler;

import java.io.BufferedReader;
import java.io.FileReader;

import java.text.SimpleDateFormat;

import java.security.spec.ECField;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import model.Hotspot;
import model.Retailer;
import model.UserPOI;
import model.Route;
import model.Station;

import java.util.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * The class Reader defines the object type Reader
 * It is used to read data from text files
 *
 * TODO remove OpenCSV once all reader tests verified working with comma issues
 */
public class Reader {

  /**
   * Converts from a String to a Date type
   * @param newDate
   * @return
   * @throws Exception
   */
  public Date StringToDate(String newDate, String pattern) {
    Date date;
    try {
      date = new SimpleDateFormat(pattern).parse(newDate);
      return date;
    }
    catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }

  /**
   * Finds commas embedded in quotes and adds the index of such commas to a list
   * @param string The string to find quoted commas within
   * @return An ArrayList of integers - The comma indexes
   */
  private ArrayList<Integer> findQuotedCommas(String string) {
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
  private String changeQuotedCommas(String string, ArrayList<Integer> locs) {
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
  private String[] replaceQuotedCommas(String[] csv, ArrayList<Integer> locs) {
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
  private String[] removeBorderQuotes(String[] csv) {
    for (int i=0; i<csv.length; i++) {
      if (csv[i].length() > 0 && csv[i].charAt(0) == '"' && csv[i].charAt(csv[i].length() - 1) == '"') {
        csv[i] = csv[i].substring(1, csv[i].length() - 1);
      }
    }
    return csv;
  }

  /**
   * Searches a list of stations and returns a station instance if a station with the same ID is found.
   * else returns null
   * @param stations  ArrayList of stations to search
   * @param id        ID of the station being searched for
   * @return          The matching Station instance (or null)
   */
  private Station getStationFromList(ArrayList<Station> stations, int id) {
    for (Station station : stations) {
      if(station.getID() == id) {
        return station;
      }
    }
    return null;
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
        int id = Integer.parseInt(csvHotspot[idIndex]);
        Double latitude = Double.parseDouble(csvHotspot[latitudeIndex]);
        Double longitude = Double.parseDouble(csvHotspot[longitudeIndex]);
        String location = csvHotspot[locationAddressIndex];
        String borough = csvHotspot[boroughIndex];
        String city = csvHotspot[cityIndex];
        int postcode = Integer.parseInt(csvHotspot[postcodeIndex]);
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
   * Reads retailers from a csv file, and builds a list of retailer instances using the extracted
   * attributes
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
          zipcode = Integer.parseInt(csvRetailer[zipcodeIndex]);
        } catch(NumberFormatException e) {
          zipcode = -1;
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

  /**
   * Reads User Points of Interest from a csv file, and builds a list of UserPOI instances using the extracted
   * attributes
   * @param filename the name of file to open
   * @return ArrayList<UserPOI> User Points of Interest
   * @throws FileNotFoundException if the file cannot be found
   */
  public ArrayList<UserPOI> readUserPOIS(String filename) throws FileNotFoundException {
    int nameIndex = 0;

    //Google uses latitude, longitude pairings.
    int latitudeIndex = 1;
    int longitudeIndex = 2;

    int descriptionIndex = 3;

    BufferedReader br = null;
    String line;
    ArrayList<UserPOI> UserPOIs = new ArrayList<UserPOI>();

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
        double latitude = Double.parseDouble(csvPOI[latitudeIndex]);
        double longitude = Double.parseDouble(csvPOI[longitudeIndex]);

        String description = csvPOI[descriptionIndex];

        UserPOIs.add(new UserPOI(latitude, longitude, name,description));
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
    return UserPOIs;
  }

  /**
   * Reads routes from a csv file, and builds a list of route instances using the extracted
   * attributes
   * @param filename the name of file to open
   * @return ArrayList<Route> The extracted routes
   * @throws FileNotFoundException if the file cannot be found
   */
  public ArrayList<Route> readRoutes(String filename, ArrayList<Station> stations) throws FileNotFoundException {

    // Column indexes for the appropriate value per row
    int durationIndex = 0;
    int startTimeIndex = 1;
    int stopTimeIndex = 2;
    int stStationIdIndex = 3;
    int stStationNameIndex = 4;
    int stStationLatIndex = 5;
    int stStationLongIndex = 6;
    int endStationIdIndex = 7;
    int endStationNameIndex = 8;
    int endStationLatIndex = 9;
    int endStationLongIndex = 10;
    int bikeIdIndex = 11;
    int userTypeIndex = 12;
    int birthYearIndex = 13;
    int genderIndex = 14;

    // Initialize scanner and Retailers array
    BufferedReader br = null;
    String line;
    ArrayList<Route> Routes = new ArrayList<Route>();

    try {

      br = new BufferedReader(new FileReader(filename));
      while ((line = br.readLine()) != null) {
        // Separate by comma
        ArrayList<Integer> locs = findQuotedCommas(line);
        line = changeQuotedCommas(line, locs);
        String[] csvRoute = line.split(",", -1);
        csvRoute = replaceQuotedCommas(csvRoute, locs);
        csvRoute = removeBorderQuotes(csvRoute);

        // Set Retailer attributes from the buffered row
        int duration;
        try {
          duration = Integer.parseInt(csvRoute[durationIndex]);
        } catch(NumberFormatException e) {
          duration = 0;
        }

        LocalDate startDate, stopDate;
        LocalTime startTime, stopTime;

        try {
          String[] startTimeFields = csvRoute[startTimeIndex].split(",", -1);

          int startTimeYear = Integer.parseInt(startTimeFields[0].split("-", -1)[0]);
          int startTimeMonth = Integer.parseInt(startTimeFields[0].split("-", -1)[1]);
          int startTimeDay = Integer.parseInt(startTimeFields[0].split("-", -1)[2]);

          int startTimeHour = Integer.parseInt(startTimeFields[1].split(":", -1)[0]);
          int startTimeMin = Integer.parseInt(startTimeFields[1].split(":", -1)[1]);
          int startTimeSec = Integer.parseInt(startTimeFields[1].split(":", -1)[2]);

          startDate = LocalDate.of(startTimeYear, startTimeMonth, startTimeDay);
          startTime = LocalTime.of(startTimeHour, startTimeMin, startTimeSec);

          String[] stopTimeFields = csvRoute[stopTimeIndex].split(",", -1);

          int stopTimeYear = Integer.parseInt(stopTimeFields[0].split("-", -1)[0]);
          int stopTimeMonth = Integer.parseInt(stopTimeFields[0].split("-", -1)[1]);
          int stopTimeDay = Integer.parseInt(stopTimeFields[0].split("-", -1)[2]);

          int stopTimeHour = Integer.parseInt(stopTimeFields[1].split(":", -1)[0]);
          int stopTimeMin = Integer.parseInt(stopTimeFields[1].split(":", -1)[1]);
          int stopTimeSec = Integer.parseInt(stopTimeFields[1].split(":", -1)[2]);

          stopDate = LocalDate.of(stopTimeYear, stopTimeMonth, stopTimeDay);
          stopTime = LocalTime.of(stopTimeHour, stopTimeMin, stopTimeSec);


        } catch(Exception e) {
          continue;
        }

        int stStationId;
        try {
          stStationId = Integer.parseInt(csvRoute[stStationIdIndex]);
        } catch(NumberFormatException e) {
          continue;
        }
        String stStationName = csvRoute[stStationNameIndex];
        double stStationLat, stStationLong;
        try {
          stStationLat = Double.parseDouble(csvRoute[stStationLatIndex]);
          stStationLong = Double.parseDouble(csvRoute[stStationLongIndex]);
        } catch(NumberFormatException e) {
          continue;
        }

        Station startStation = getStationFromList(stations, stStationId);
        if (startStation == null) {
          startStation = new Station(stStationId, stStationName, stStationLat, stStationLong);
        }

        int endStationId;
        try {
          endStationId = Integer.parseInt(csvRoute[stStationIdIndex]);
        } catch(NumberFormatException e) {
          continue;
        }
        String endStationName = csvRoute[stStationNameIndex];
        double endStationLat, endStationLong;
        try {
          endStationLat = Double.parseDouble(csvRoute[stStationLatIndex]);
          endStationLong = Double.parseDouble(csvRoute[stStationLongIndex]);
        } catch(NumberFormatException e) {
          continue;
        }

        Station stopStation = getStationFromList(stations, endStationId);
        if (stopStation == null) {
          stopStation = new Station(endStationId, endStationName, endStationLat, endStationLong);
        }

        int bikeId;
        try {
          bikeId = Integer.parseInt(csvRoute[bikeIdIndex]);
        } catch(NumberFormatException e) {
          bikeId = 0;
        }
        String userType = csvRoute[userTypeIndex];

        int birthYear;
        try {
          birthYear = Integer.parseInt(csvRoute[birthYearIndex]);
        } catch(NumberFormatException e) {
          birthYear = 0;
        }

        int gender;
        try {
          gender = Integer.parseInt(csvRoute[genderIndex]);
        } catch(NumberFormatException e) {
          gender = 0;
        }

        //add new Route to Route array
        Routes.add(new Route(duration, startDate, startTime, stopDate, stopTime, startStation, stopStation,
            bikeId, userType, birthYear, gender));

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
    return new ArrayList<Route>();
    //return Retailers;
  }


  /**
   * Reads routes from a csv file
   *
   * <p> Needs to be tested to ensure it works. </p>
   *
   * @param filename the name of file to open
   * @return ArrayList<Route> Routes
   * @throws FileNotFoundException if the file cannot be found
   */
  public ArrayList<Route> readRoutes(String filename) throws FileNotFoundException {

    // Column indexes for the appropriate value per row
    int durationIndex = 0;
    int startDateTimeIndex = 1;
    int stopDateTimeIndex = 2;

    // Start Station
    int startStationIDIndex = 3;
    int startStationNameIndex = 4;
    int startStationLatitudeIndex = 5;
    int startStationLongitudeIndex = 6;

    //Stop Station
    int stopStationIDIndex = 7;
    int stopStationNameIndex = 8;
    int stopStationLatitudeIndex = 9;
    int stopStationLongitudeIndex = 10;

    int bikeIDIndex = 11;
    int userTypeIndex = 12;
    int birthYearIndex = 13;
    int genderIndex = 14;


    // Initialize scanner and Retailers array
    BufferedReader br = null;
    String line;
    ArrayList<Route> Routes = new ArrayList<Route>();

    try {

      br = new BufferedReader(new FileReader(filename));
      while ((line = br.readLine()) != null) {
        // Separate by comma
        String[] csvRoute = line.split(",");

        // Set Route attributes from the buffered row
        int duration = Integer.valueOf(csvRoute[durationIndex]);

        // startStation
        Station startStation = new Station(Integer.valueOf(csvRoute[startStationIDIndex]),
            String.valueOf(csvRoute[startStationNameIndex]),
            Double.valueOf(csvRoute[startStationLatitudeIndex]),
            Double.valueOf(csvRoute[startStationLongitudeIndex]));

        // stopStation
        Station stopStation = new Station(Integer.valueOf(csvRoute[stopStationIDIndex]),
            String.valueOf(csvRoute[stopStationNameIndex]),
            Double.valueOf(csvRoute[stopStationLatitudeIndex]),
            Double.valueOf(csvRoute[stopStationLongitudeIndex]));

        // Other values
        int bikeID = Integer.valueOf(csvRoute[bikeIDIndex]);
        String userType = csvRoute[userTypeIndex];

        // Birth year
        int birthYear;
        if (csvRoute[birthYearIndex].equals("\\N")) {
          birthYear = -1;
        } else {
          birthYear = Integer.valueOf(csvRoute[birthYearIndex]);
        }
        int gender = Integer.valueOf(csvRoute[genderIndex]);

        // Convert to Dates
        try {
          Date startDateTime = StringToDate(csvRoute[startDateTimeIndex], "MM/dd/yyyy");
          Date stopDateTime = StringToDate(csvRoute[stopDateTimeIndex], "MM/dd/yyyy");

          //add newRetailer to Retailers array
          Routes.add(new Route(duration, startDateTime, stopDateTime, startStation, stopStation, bikeID, userType, birthYear, gender));
        }
        catch (Exception e) {
          System.out.println(e);
        }
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

    return Routes;
  }
}