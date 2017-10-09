package filehandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Hotspot;
import model.POI;
import model.PublicPOI;
import model.Retailer;
import model.Route;
import model.Station;
import model.UserPOI;

/**
 * The class Writer defines the object type Writer
 * It is used to write data to files
 */
public class Writer {

  /**
   * Formats a list of attributes to wrap them in surrounding quotemarks if an attribute contains a comma
   * Used for proper specification of commas that aren't splitters for CSV writing
   * @param attributes  The list of String attributes to check (and format)
   * @return The list after formatting necessary attribute Strings
   */
  private String[] getAttributeFormat (String[] attributes) {
    for (int i = 0; i < attributes.length; i++) {
      if (attributes[i].contains(",")) {
        attributes[i] = "\"" + attributes[i] + "\"";
      }
    }
    return attributes;
  }

  /**
   * Fetches the appropriate list of String attributes from a generic POI based on what child class of POI it
   * is an instance of.
   * @param poi The instance of the POI child class
   * @return The list of String attributes for that POI-type
   */
  private String[] getAttributesFromPOI (POI poi) {
    if (poi instanceof Retailer) {
      Retailer ret = (Retailer) poi;
      String zipcode = ret.getZipcode() == -1 ? "":String.valueOf(ret.getZipcode());
      String[] rAttributes = {ret.getName(), ret.getAddress(), ret.getFloor(),
          ret.getCity(), ret.getState(), zipcode,
          ret.getBlock(), ret.getDescription(), ret.getSecondaryDescription()};
      return getAttributeFormat(rAttributes);

    } else if (poi instanceof Hotspot) {
      Hotspot hot = (Hotspot) poi;
      String[] hAttributes = {String.valueOf(hot.getID()), hot.getType(), hot.getProvider(),
          hot.getName(), hot.getLocationAddress(), String.valueOf(hot.getLatitude()),
          String.valueOf(hot.getLongitude()), hot.getDescription(), hot.getCity(), hot.getSSID(),
          hot.getBorough(), String.valueOf(hot.getPostcode())};
      return getAttributeFormat(hAttributes);

    } else if (poi instanceof UserPOI) {
      UserPOI usr = (UserPOI) poi;
      String[] uAttributes = {usr.getName(), String.valueOf(usr.getLatitude()),
          String.valueOf(usr.getLongitude()), usr.getDescription()};
      return getAttributeFormat(uAttributes);

    } else if (poi instanceof PublicPOI) {
      PublicPOI pblc = (PublicPOI) poi;
      String[] pAttributes = {pblc.getName(), String.valueOf(pblc.getLatitude()),
          String.valueOf(pblc.getLongitude()), pblc.getDescription()};
      return getAttributeFormat(pAttributes);
    } else if (poi instanceof Station) {
      Station stn = (Station) poi;
      String[] sAttributes = {String.valueOf(stn.getID()), stn.getName(), String.valueOf(stn.getAvailableDocs()),
          String.valueOf(stn.getTotalDocks()), String.valueOf(stn.getLatitude()), String.valueOf(stn.getLongitude()),
        stn.getStatusValue(), String.valueOf(stn.getAvailableBikes()), stn.getStreetAddress1(),
        stn.getCity(), stn.getPostalCode(), String.valueOf(stn.isTestStation())};
      return getAttributeFormat(sAttributes);
    }
    return null;
  }

  /**
   * Returns a list of String attributes for a route
   * @param rte An instance of a route to grab attributes from
   * @return The list of String attributes
   */
  private String[] getAttributesFromRoute(Route rte) {
    return new String[]{String.valueOf(rte.getDuration()), rte.getStartDate().toString(),
        rte.getStopDate().toString(), String.valueOf(rte.getStartStation().getID()),
        rte.getStartStation().getName(), String.valueOf(rte.getStartStation().getLatitude()),
        String.valueOf(rte.getStartStation().getLongitude()), String.valueOf(rte.getStopStation().getID()),
        rte.getStopStation().getName(), String.valueOf(rte.getStopStation().getLatitude()),
        String.valueOf(rte.getStopStation().getLongitude()), String.valueOf(rte.getBikeID()),
        rte.getUserType(), String.valueOf(rte.getBirthYear()), String.valueOf(rte.getGender())
    };
  }

  /**
   * Writes attributes of an object split by commas to an already specified file using the created
   * Buffered Writer
   * @param writer  The instance of the BufferedWriter
   * @param attributes  The list of String attributes
   * @throws IOException If there was an error writing to the file
   */
  private void writeLinesToFile(BufferedWriter writer, String[] attributes) throws IOException {
    for (int i = 0; i < attributes.length; i++) {
      if (attributes[i] != null) {
        writer.write(attributes[i]);
      }
      if (i < attributes.length - 1) {
        writer.write(',');
      } else {
        writer.newLine();
      }
    }
  }

  /**
   * Writes a list of retailer objects to a given CSV file
   * @param POIs The list of retailer objects
   * @param filename  The file to write to
   * @throws IOException  If there is an error writing to the file
   */
  public void writePOIsToFile (List<? extends POI> POIs, String filename) throws IOException {
    FileWriter outFile = new FileWriter(filename, true);
    BufferedWriter writer = new BufferedWriter(outFile);

    for (POI poi : POIs) {
      String[] attributes = getAttributesFromPOI(poi);
      writeLinesToFile(writer, attributes);

    }
    writer.close();
  }

  /**
   * Writes routes to a csv file given a filename.
   * @param routes The list of routes to write to the file
   * @param filename  The filename to write to
   * @throws IOException  If there is an error writing to the file
   */
  public void writeRoutesToFile (List<Route> routes, String filename) throws IOException {
    FileWriter outFile = new FileWriter(filename, true);
    BufferedWriter writer = new BufferedWriter(outFile);

    for (Route route : routes) {
      String[] attributes = getAttributesFromRoute(route);

      writeLinesToFile(writer, attributes);
    }

    writer.close();
  }

  public static void main(String[] args) {
    Writer testW = new Writer();
    Reader testR = new Reader();

    ArrayList<Retailer> retailers;
    ArrayList<Hotspot> hotspots;
    ArrayList<Station> stations;
    ArrayList<UserPOI> userPOIS;
    ArrayList<PublicPOI> publicPOIS;

    try {
      retailers = testR.readRetailers("/file/InitialRetailers.csv", false);
      hotspots = testR.readHotspots("/file/InitialHotspots.csv", false);
      stations = testR.readStations("/file/stations.json");
      userPOIS = testR.readUserPOIS("/file/UserPOIdata_smallsample.csv", false);
      publicPOIS = testR.readPublicPOIS("/file/PublicPOIdata_smallsample.csv", false);

      testW.writePOIsToFile(retailers, "src/main/java/filehandler/aRet.csv");
      testW.writePOIsToFile(hotspots, "src/main/java/filehandler/aHot.csv");
      testW.writePOIsToFile(userPOIS, "src/main/java/filehandler/aUser.csv");
      testW.writePOIsToFile(publicPOIS, "src/main/java/filehandler/aPub.csv");

    } catch (IOException e) {
      System.out.println("oops");
    }
  }

}
