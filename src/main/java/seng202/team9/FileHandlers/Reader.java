package seng202.team9.FileHandlers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import seng202.team9.Models.Hotspot;
import seng202.team9.Models.Retailer;
import java.util.ArrayList;
import java.io.FileNotFoundException;

/**
 * The class Reader is used to load files and data
 */

public class Reader {

  /**
   * Reads WiFi hotspots from a csv file
   *
   * <p>
   *   Needs to be tested to ensure it works.
   * </p>
   * @param filename  the name of file to open
   * @throws FileNotFoundException  if the file cannot be found
   * @return ArrayList<Hotspot> Hotspots
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
   * <p>
   *   Needs to be tested to ensure it works.
   * </p>
   * @param filename  the name of file to open
   * @throws FileNotFoundException  if the file cannot be found
   * @return ArrayList<Retailer> Retailers
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
        Retailer newRetailer = new Retailer();

        // Separate by comma
        String[] csvRetailer = line.split(",");

        // Set Retailer attributes from the buffered row
        newRetailer.setTitle(csvRetailer[titleIndex]);
        newRetailer.setAddress(csvRetailer[addressIndex]);
        newRetailer.setFloor(csvRetailer[floorIndex]);
        newRetailer.setCity(csvRetailer[cityIndex]);
        newRetailer.setState(csvRetailer[stateIndex]);
        newRetailer.setZipcode(Integer.valueOf(csvRetailer[zipcodeIndex]));
        newRetailer.setBlock(csvRetailer[blockIndex]);
        newRetailer.setDescription(csvRetailer[descriptionIndex]);
        newRetailer.setSecondaryDescription(csvRetailer[description2Index]);

        //add newRetailer to Retailers array
        Retailers.add(newRetailer);

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




}
