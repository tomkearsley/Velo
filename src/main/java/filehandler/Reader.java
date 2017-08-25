package filehandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import model.Hotspot;
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





}
