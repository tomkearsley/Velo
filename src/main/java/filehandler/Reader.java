package filehandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import model.Hotspot;
import model.POI;
import model.Retailer;
import java.util.ArrayList;
import java.io.FileNotFoundException;

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
    int boroughIndex = 18;
    int postcodeIndex = 22;

    // Initialize scanner and Hotspots array
    BufferedReader br = null;
    String line;
    ArrayList<Hotspot> Hotspots = new ArrayList<Hotspot>();

    try {

      br = new BufferedReader(new FileReader(filename));
      while ((line = br.readLine()) != null) {
        Hotspot newHotspot = new Hotspot();

        // Separate by comma
        String[] csvHotspot = line.split(",");

        // Set Hotspot attributes from the buffered row
        newHotspot.setID(Integer.valueOf(csvHotspot[idIndex]));
        newHotspot.setType(csvHotspot[typeIndex]);
        newHotspot.setProvider(csvHotspot[providerIndex]);
        newHotspot.setName(csvHotspot[nameIndex]);
        newHotspot.setLocationAdress(csvHotspot[locationAddressIndex]);
        newHotspot.setLatitude(Double.parseDouble(csvHotspot[latitudeIndex]));
        newHotspot.setLongitude(Double.parseDouble(csvHotspot[longitudeIndex]));
        newHotspot.setRemarks(csvHotspot[remarksIndex]);
        newHotspot.setCity(csvHotspot[cityIndex]);
        newHotspot.setSSID(csvHotspot[ssidIndex]);
        newHotspot.setBorough(csvHotspot[boroughIndex]);
        newHotspot.setPostcode(Integer.valueOf(csvHotspot[postcodeIndex]));

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
      while ((line = br.readLine()).length() > 0) {
        // Separate by comma
        String[] csvRetailer = line.split("\t");

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

    BufferedReader br = null;
    String line;
    ArrayList<POI> POIS = new ArrayList<POI>();

    try {

      br = new BufferedReader(new FileReader(filename));
      while ((line = br.readLine()).length() > 0) {
        // Separate by comma
        String[] csvPOIS = line.split("\t");

        //Get name of POI
        String name = csvPOIS[nameIndex];

        //Get location values
        double longitude = Double.valueOf(csvPOIS[latitudeIndex]);
        double latitude = Double.valueOf(csvPOIS[longitudeIndex]);

        //add newRetailer to Retailers array
        double[] location = {longitude, latitude};

        POIS.add(new POI(location, name));
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
}
