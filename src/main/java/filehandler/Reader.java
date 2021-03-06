package filehandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import model.Hotspot;
import model.PublicPOI;
import model.Retailer;
import model.Route;
import model.Station;
import model.UserPOI;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The class Reader defines the object type Reader It is used to read data from text files
 */
public class Reader {

  /**
   * Converts from a String to a Date type
   *
   * @param newDate inputDate
   * @param pattern Date pattern that we want to read
   * @return The Date representation of the string passed in as a parameter
   */
  public Date StringToDate(String newDate, String pattern) {
    Date date;
    try {
      date = new SimpleDateFormat(pattern).parse(newDate);
      return date;
    } catch (ParseException e) {
      return null;
    }
  }

  /**
   * Finds commas embedded in quotes and adds the index of such commas to a list
   *
   * @param string The string to find quoted commas within
   * @return An ArrayList of integers - The comma indexes
   */
  private ArrayList<Integer> findQuotedCommas(String string) {
    boolean quoteReached = false;
    ArrayList<Integer> locs = new ArrayList<>();
    for (int i = 0; i < string.length(); i++) {
      if (string.charAt(i) == '"') {
        quoteReached = !quoteReached;
      }
      if (quoteReached && string.charAt(i) == ',') {
        locs.add(i);
      }
    }
    return locs;
  }

  /**
   * Replaces the commas embedded in quotes with a space to keep the string length constant while
   * allowing for proper splitting
   *
   * @param string The string to be adjusted
   * @param locs The list of offending comma indexes
   * @return The string after replacing offending commas
   */
  private String changeQuotedCommas(String string, ArrayList<Integer> locs) {
    String newString = "";
    int locCount = 0;
    for (int i = 0; i < string.length(); i++) {
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
   *
   * @param csv The list of strings from a POI
   * @param locs The list of offending comma indexes
   * @return The list of strings with original commas replaced
   */
  private String[] replaceQuotedCommas(String[] csv, ArrayList<Integer> locs) {
    int charCount = 0;
    int locCount = 0;
    for (int i = 0; i < csv.length; i++) {
      String newString = "";
      for (int o = 0; o < csv[i].length(); o++) {
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
   * Removes double quotes from Strings that are embedded within them, as they were only functional
   * for dealing with non-splitting commas within the csv
   *
   * @param csv The csv to search through
   * @return The csv with border quotes removed
   */
  private String[] removeBorderQuotes(String[] csv) {
    for (int i = 0; i < csv.length; i++) {
      if (csv[i].length() > 0 && csv[i].charAt(0) == '"'
          && csv[i].charAt(csv[i].length() - 1) == '"') {
        csv[i] = csv[i].substring(1, csv[i].length() - 1);
      }
    }
    return csv;
  }

  /**
   * Searches a list of stations and returns a station instance if a station with the same ID is
   * found. else returns null
   *
   * @param stations ArrayList of stations to search
   * @param id ID of the station being searched for
   * @return The matching Station instance (or null)
   */
  private Station getStationFromList(ArrayList<Station> stations, int id) {
    for (Station station : stations) {
      if (station.getID() == id) {
        return station;
      }
    }
    return null;
  }

  /**
   * Reads WiFi hotspots from a csv file Uses BufferedReader
   * @param filename the name of file to open
   * @param isExternalFile To determine format of file and how to read it
   * @return ArrayList Hotspots
   * @throws IOException if there is an error with the file (or it doesn't exist)
   * @throws ArrayIndexOutOfBoundsException if there is an error with the indexing
   */
  public ArrayList<Hotspot> readHotspots(String filename, boolean isExternalFile)
      throws IOException, ArrayIndexOutOfBoundsException {

    // Column indexes for the appropriate value per row
    int idIndex = 0;
    int typeIndex = isExternalFile ? 1 : 3;
    int providerIndex = isExternalFile ? 2 : 4;
    int nameIndex = isExternalFile ? 3 : 5;
    int locationAddressIndex = isExternalFile ? 4 : 6;
    int latitudeIndex = isExternalFile ? 5 : 7;
    int longitudeIndex = isExternalFile ? 6 : 8;
    int remarksIndex = isExternalFile ? 7 : 12;
    int cityIndex = isExternalFile ? 8 : 13;
    int ssidIndex = isExternalFile ? 9 : 14;
    int boroughIndex = isExternalFile ? 10 : 19;
    int postcodeIndex = isExternalFile ? 11 : 22;

    // Initialize scanner and Hotspots array
    BufferedReader br;
    String line;
    ArrayList<Hotspot> Hotspots = new ArrayList<>();

    if (isExternalFile) {
      br = new BufferedReader(new FileReader(new File(filename)));
    } else {
      br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
    }
    while ((line = br.readLine()) != null) {

      // Separate by comma
      ArrayList<Integer> locs = findQuotedCommas(line);
      line = changeQuotedCommas(line, locs);
      String[] csvHotspot = line.split(",", -1);
      csvHotspot = replaceQuotedCommas(csvHotspot, locs);
      csvHotspot = removeBorderQuotes(csvHotspot);

      // Get Hotspot attributes from the buffered row
      int id;
      try {
        id = Integer.parseInt(csvHotspot[idIndex]);
      } catch (NumberFormatException e) {
        continue;
      }
      Double latitude, longitude;
      try {
        latitude = Double.parseDouble(csvHotspot[latitudeIndex]);
        longitude = Double.parseDouble(csvHotspot[longitudeIndex]);
      } catch (NumberFormatException e) {
        continue;
      }
      String location = csvHotspot[locationAddressIndex];
      String borough = csvHotspot[boroughIndex];
      String city = csvHotspot[cityIndex];
      int postcode;
      try {
        postcode = Integer.parseInt(csvHotspot[postcodeIndex]);
      } catch (NumberFormatException e) {
        postcode = -1;
      }
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

    return Hotspots;
  }

  /**
   * Reads retailers from a csv file, and builds a list of retailer instances using the extracted
   * attributes
   *
   * @param filename the name of file to open
   * @param isExternalFile Whether the file is external to the program (for Import/Exports)
   * @return ArrayList Retailers
   * @throws IOException if there is an error with the file (or it doesn't exist)
   * @throws ArrayIndexOutOfBoundsException if there is an error with the indexing
   */
  public ArrayList<Retailer> readRetailers(String filename, boolean isExternalFile)
      throws IOException, ArrayIndexOutOfBoundsException {

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
    int latitudeIndex = 9;
    int longitudeIndex = 10;

    // Initialize scanner and Retailers array
    BufferedReader br;
    String line;
    ArrayList<Retailer> Retailers = new ArrayList<>();

    if (isExternalFile) {
      br = new BufferedReader(new FileReader(new File(filename)));
    } else {
      br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
    }

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
      } catch (NumberFormatException e) {
        zipcode = -1;
      }
      String block = csvRetailer[blockIndex];
      String description = csvRetailer[descriptionIndex];
      String secondaryDescription = csvRetailer[description2Index];

      if (!isExternalFile) {
        Double latitude;
        Double longitude;
        try {
           latitude = Double.parseDouble(csvRetailer[latitudeIndex]);
           longitude = Double.parseDouble(csvRetailer[longitudeIndex]);
        } catch (NumberFormatException e) {
          continue;
        }

        //add newRetailer to Retailers array
        Retailers
            .add(new Retailer(title, address, floor, city, state, zipcode, block, description,
                secondaryDescription, latitude, longitude));
      } else {
        //add newRetailer to Retailers array
        Retailers
            .add(new Retailer(title, address, floor, city, state, zipcode, block, description,
                secondaryDescription));
      }

    }

    return Retailers;
  }

  /**
   * Reads User Points of Interest from a csv file, and builds a list of UserPOI instances using the
   * extracted attributes
   *
   * @param filename the name of file to open
   * @param isExternalFile Whether the file is external to the program (for Import/Exports)
   * @return ArrayList User Points of Interest
   * @throws IOException if there is an error with the file (or it doesn't exist)
   * @throws ArrayIndexOutOfBoundsException if there is an error with the indexing
   */
  public ArrayList<UserPOI> readUserPOIS(String filename, boolean isExternalFile)
      throws IOException, ArrayIndexOutOfBoundsException {

    int nameIndex = 0;
    int latitudeIndex = 1;
    int longitudeIndex = 2;
    int descriptionIndex = 3;

    BufferedReader br;
    String line;
    ArrayList<UserPOI> UserPOIs = new ArrayList<>();

    if (isExternalFile) {
      br = new BufferedReader(new FileReader(new File(filename)));
    } else {
      br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
    }

    while ((line = br.readLine()) != null) {
      // Separate by comma
      ArrayList<Integer> locs = findQuotedCommas(line);
      line = changeQuotedCommas(line, locs);
      String[] csvPOI = line.split(",", -1);
      csvPOI = replaceQuotedCommas(csvPOI, locs);
      csvPOI = removeBorderQuotes(csvPOI);

      //Get name & description of POI
      String name = csvPOI[nameIndex];
      String description = csvPOI[descriptionIndex];

      //Get coordinates
      double latitude;
      double longitude;

      try {
        latitude = Double.parseDouble(csvPOI[latitudeIndex]);
        longitude = Double.parseDouble(csvPOI[longitudeIndex]);
      } catch (NumberFormatException e) {
        continue;
      }

      UserPOIs.add(new UserPOI(latitude, longitude, name, description));
    }

    return UserPOIs;
  }

  /**
   * Reads Public Points of Interest from a csv file, and builds a list of PublicPOI instances using
   * the extracted attributes
   *
   * @param filename the name of file to open
   * @param isExternalFile Whether the file is external to the program (for Import/Exports)
   * @return ArrayList Public Points of Interest
   * @throws IOException if there is an error with the file (or it doesn't exist)
   * @throws ArrayIndexOutOfBoundsException if there is an error with the indexing
   */
  public ArrayList<PublicPOI> readPublicPOIS(String filename, boolean isExternalFile)
      throws IOException, ArrayIndexOutOfBoundsException {

    int nameIndex = 0;
    int latitudeIndex = 1;
    int longitudeIndex = 2;
    int descriptionIndex = 3;

    BufferedReader br;
    String line;
    ArrayList<PublicPOI> PublicPOIs = new ArrayList<>();

    if (isExternalFile) {
      br = new BufferedReader(new FileReader(new File(filename)));
    } else {
      br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
    }

    while ((line = br.readLine()) != null) {
      // Separate by comma
      ArrayList<Integer> locs = findQuotedCommas(line);
      line = changeQuotedCommas(line, locs);
      String[] csvPOI = line.split(",", -1);
      csvPOI = replaceQuotedCommas(csvPOI, locs);
      csvPOI = removeBorderQuotes(csvPOI);

      //Get name & description
      String name = csvPOI[nameIndex];
      String description = csvPOI[descriptionIndex];

      //Get Coordinates
      double latitude, longitude;
      try {
        latitude = Double.parseDouble(csvPOI[latitudeIndex]);
        longitude = Double.parseDouble(csvPOI[longitudeIndex]);
      } catch (NumberFormatException e) {
        continue;
      }

      PublicPOIs.add(new PublicPOI(latitude, longitude, name, description));
    }
    br.close();
    return PublicPOIs;
  }

  /**
   * Reads routes from a csv file
   *
   * <p> Needs to be tested to ensure it works. </p>
   *
   * @param filename the name of file to open
   * @param stations The stations to read the routes from
   * @param isExternalFile Whether the file is external to the program (for Import/Exports)
   * @return ArrayList Routes
   * @throws IOException if there is an error with the file (or it doesn't exist)
   * @throws ArrayIndexOutOfBoundsException if there is an error with the indexing
   */
  public ArrayList<Route> readRoutes(String filename, ArrayList<Station> stations,
      boolean isExternalFile)
      throws IOException, ArrayIndexOutOfBoundsException{

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
    BufferedReader br;
    String line;
    ArrayList<Route> Routes = new ArrayList<>();

    if (isExternalFile) {
      br = new BufferedReader(new FileReader(new File(filename)));
    } else {
      br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
    }
    while ((line = br.readLine()) != null) {
      // Separate by comma
      String[] csvRoute = line.split(",");

      // Set Route attributes from the buffered row
      int duration = 0;
      try {
        duration = Integer.parseInt(csvRoute[durationIndex]);
      } catch (NumberFormatException e) {
        //TODO ALERT WINDOWS
      }

      Station startStation, stopStation;
      try {
        // startStation
        int startStationID = Integer.parseInt(csvRoute[startStationIDIndex]);
        startStation = getStationFromList(stations, startStationID);
        if (startStation == null) {
          startStation = new Station(startStationID,
              String.valueOf(csvRoute[startStationNameIndex]),
              Double.valueOf(csvRoute[startStationLatitudeIndex]),
              Double.valueOf(csvRoute[startStationLongitudeIndex]));
        }

        // stopStation
        int stopStationID = Integer.parseInt(csvRoute[stopStationIDIndex]);
        stopStation = getStationFromList(stations, stopStationID);
        if (stopStation == null) {
          stopStation = new Station(stopStationID,
              String.valueOf(csvRoute[stopStationNameIndex]),
              Double.valueOf(csvRoute[stopStationLatitudeIndex]),
              Double.valueOf(csvRoute[stopStationLongitudeIndex]));
        }
      }catch (NumberFormatException e) {
          continue;
      }

      // Other values
      int bikeID;
      try {
        bikeID = Integer.valueOf(csvRoute[bikeIDIndex]);
      } catch (NumberFormatException e) {
        bikeID = -1;
      }
      String userType = csvRoute[userTypeIndex];

      // Birth year
      int birthYear;
      if (csvRoute[birthYearIndex].equals("\\N") || csvRoute[birthYearIndex].equals("NULL")) {
        birthYear = -1;
      } else {
        try {
          birthYear = Integer.parseInt(csvRoute[birthYearIndex]);
        } catch (NumberFormatException e) {
          birthYear = -1;
        }
      }

      int gender;
      try {
        gender = Integer.parseInt(csvRoute[genderIndex]);
      } catch(NumberFormatException e) {
        gender = 2;
      }

      // Convert to Dates
      Date startDateTime = StringToDate(csvRoute[startDateTimeIndex], "MM/dd/yyyy HH:mm");
      Date stopDateTime = StringToDate(csvRoute[stopDateTimeIndex], "MM/dd/yyyy HH:mm");

      //add newRetailer to Retailers array
      Routes.add(new Route(duration, startDateTime, stopDateTime, startStation, stopStation, bikeID,
          userType, birthYear, gender));
    }
    return Routes;
  }

  /**
   * Reads routes from a JSON file
   *
   * <p> Needs to be tested to ensure it works. </p>
   *
   * @param filename the name of file to open
   * @return ArrayList Stations
   * @throws IOException if there is an error with the file (or it doesn't exist)
   * @throws ArrayIndexOutOfBoundsException if there is an error with the indexing
   */
  public ArrayList<Station> readStations(String filename) throws IOException, ArrayIndexOutOfBoundsException {

    ArrayList<Station> returnArray = new ArrayList<>();

    BufferedReader reader = new BufferedReader(
        new InputStreamReader(getClass().getResourceAsStream(filename)));
    StringBuilder sb = new StringBuilder();
    String inputLine;

    while ((inputLine = reader.readLine()) != null) {
      sb.append(inputLine);
    }
    reader.close();

    JSONObject jsonObj = new JSONObject(sb.toString());

    JSONArray stationList = jsonObj.getJSONArray("stationBeanList");

    int listLength = stationList.length();

    Station bufferStation;

    JSONObject bufferObject;

    int id;
    String stationName;
    int availableDocks;
    int totalDocks;
    double latitude;
    double longitude;
    String statusValue;
    int statusKey;
    int availableBikes;
    String stAddress1;
    String stAddress2;
    String city;
    String postalCode;
    String location;
    String altitude;
    boolean testStation;
    Date lastCommunicationTime;
    String landMark;

    /*
     id, stationName, availableDocs, totalDocks, latitude, longitude, statusValue, statusKey, availableBikes, stAddress1, stAddress2, postalCode,
     location, altitude, testStation, lastCommunicationTime, landMark
     */

    for (int i = 0; i < listLength; i++) {
      bufferObject = stationList.getJSONObject(i);
      id = bufferObject.getInt("id");
      stationName = bufferObject.getString("stationName");
      availableDocks = bufferObject.getInt("availableDocks");
      totalDocks = bufferObject.getInt("totalDocks");
      latitude = bufferObject.getDouble("latitude");
      longitude = bufferObject.getDouble("longitude");
      statusValue = bufferObject.getString("statusValue");
      statusKey = bufferObject.getInt("statusKey");
      availableBikes = bufferObject.getInt("availableBikes");
      stAddress1 = bufferObject.getString("stAddress1");
      stAddress2 = bufferObject.getString("stAddress2");
      city = bufferObject.getString("city");
      postalCode = bufferObject.getString("postalCode");
      location = bufferObject.getString("location");
      altitude = bufferObject.getString("altitude");
      testStation = bufferObject.getBoolean("testStation");
      try {
        lastCommunicationTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a")
            .parse(bufferObject.getString("lastCommunicationTime"));
      } catch (ParseException e) {
        System.out.println("Unexpected date");
        return null;
      }
      landMark = bufferObject.getString("landMark");

      //Converting date from simpledateformat to localdate
      Instant instant = lastCommunicationTime.toInstant();
      ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
      LocalDate date = zdt.toLocalDate();

      bufferStation = new Station(id, stationName, availableDocks, totalDocks, latitude,
          longitude, statusValue, statusKey, availableBikes, stAddress1, stAddress2, city,
          postalCode,
          location, altitude, testStation, date, landMark);

      returnArray.add(bufferStation);
    }
    return returnArray;
  }
}