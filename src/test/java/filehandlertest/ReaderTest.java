package filehandlertest;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Hotspot;
import model.POI;
import model.Retailer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import filehandler.Reader;


public class ReaderTest {
  private Reader reader;

  /**
   * This function is called before running any of the following functions.
   */
  @Before
  public void setUp() {
    reader = new Reader();
  }

  /**
   * This function is called at the end of every test that is run.
   */
  @After
  public void tearDown() {
    reader = null;
  }

  @Test
  public void readHotspots() throws FileNotFoundException {

    ArrayList<Hotspot> hotspots = reader.readHotspots("src/test/testResources/TestInitialHotspots.csv");


    Hotspot hotspot998 = new Hotspot();
    hotspot998.setID(998);
    hotspot998.setLatitude(40.745968);
    hotspot998.setLongitude(-73.994039);
    hotspot998.setLocationAdress("179 WEST 26 STREET");
    hotspot998.setBorough("Manhattan");
    hotspot998.setCity("New York");
    hotspot998.setPostcode(10001);
    hotspot998.setType("Free");
    hotspot998.setSSID("LinkNYC Free Wi-Fi");
    hotspot998.setName("LinkNYC - Citybridge");
    hotspot998.setProvider("LinkNYC - Citybridge");
    hotspot998.setRemarks("Tablet Internet -phone , Free 1 GB Wi-FI Service");

    Hotspot hotspot26 = new Hotspot();
    hotspot26.setID(26);
    hotspot26.setLatitude(40.657452);
    hotspot26.setLongitude(-73.963991);
    hotspot26.setLocationAdress("Skate Rental Area");
    hotspot26.setBorough("Brooklyn");
    hotspot26.setCity("Brooklyn");
    hotspot26.setPostcode(11215);
    hotspot26.setType("Limited Free");
    hotspot26.setSSID("GuestWiFi");
    hotspot26.setName("Prospect Park");
    hotspot26.setProvider("ALTICEUSA");
    hotspot26.setRemarks("3 free 10 min sessions");

    ArrayList<Hotspot> expectedHotspots = new ArrayList<Hotspot>();
    expectedHotspots.add(hotspot998);
    expectedHotspots.add(hotspot26);


    assertEquals(expectedHotspots, hotspots);
  }


  /**
   * Tests the Reader to verify that it collects the right information from a csv
   * and does not miss any entries
   */
  @Test
  public void readRetailers() throws FileNotFoundException{
    List<Retailer> Retailers = reader.readRetailers("src/test/testResources/retailers.csv");
    Retailer r1, r2, r3;
    r1 = new Retailer("Starbucks Coffee", "3 New York Plaza", "", "New York",
        "NY", 10004, "8-32", "Casual Eating & Takeout",
        "F-Coffeehouse");
    r2 = new Retailer("New York Health & Racquet Club", "39 Whitehall Street", "",
        "New York", "NY", 10004, "8-32",
        "Personal and Professional Services", "P-Athletic Clubs/Fitness");
    r3 = new Retailer("A.J. Kelly's", "6 Stone Street", "", "New York",
        "NY", 10004, "10-32", "Full Service Dining",
        "F-Irish Pub");

    List<Retailer> actualRetailers = Arrays.asList(r1, r2, r3);
    for (int i = 0; i < Retailers.size(); i++) {
      assertTrue(Retailers.get(i).equals(actualRetailers.get(i)));
    }

  }

  @Test
  public void readPOIS() throws FileNotFoundException {
    ArrayList<POI> POIS = reader.readUserPOIS("src/test/testResources/POIS.csv");
    ArrayList<POI> expectedPOIS = new ArrayList<POI>();

    double location1[] = {40.7484,-73.9857};
    double location2[] = {40.7829,-73.9654};
    double location3[] = {40.6892,-74.0445};

    String name1 = "Empire State Building";
    String name2 = "Central Park";
    String name3 = "Statue Of Liberty";

    String desc1 = "The Empire State Building is a 102-story skyscraper";
    String desc2 = "Central Park is an urban park in Manhattan";
    String desc3 = "The Statue of Liberty is a colossal neoclassical sculpture on Liberty Island in New York Harbor";

    //Location,Name,Description
    POI POI1 = new POI(location1,name1,desc1);
    POI POI2 = new POI(location2,name2,desc2);
    POI POI3 = new POI(location3,name3,desc3);

    expectedPOIS.add(POI1);
    expectedPOIS.add(POI2);
    expectedPOIS.add(POI3);

    for(int i = 0; i < expectedPOIS.size(); i++) {
      assertTrue(expectedPOIS.get(i).equals(POIS.get(i)));
    }
  }

}