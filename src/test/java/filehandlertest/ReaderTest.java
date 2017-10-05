package filehandlertest;

//TODO Replace assertTrue (blah.equals(blah2)) with assertEquals (overriding issue)
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import model.Hotspot;
import model.PublicPOI;
import model.Retailer;
import model.Route;
import model.Station;
import model.UserPOI;
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

  /**
   * Test the Reader to ensure it extracts Hotspot csv data correctly
   * @throws FileNotFoundException In case the file can' be found
   */
  @Test
  public void readHotspots() throws FileNotFoundException {
    ArrayList<Hotspot> hotspots = reader.readHotspots("/test/TestInitialHotspots.csv", false);

    Hotspot hotspot998 = new Hotspot(998, 40.745968, -73.994039,
        "179 WEST 26 STREET", "MN17", "New York", 10001,
        "Free", "LinkNYC Free Wi-Fi", "mn-05-123662",
        "LinkNYC - Citybridge", "Tablet Internet -phone , Free 1 GB Wi-FI Service");

    Hotspot hotspot26 = new Hotspot(26, 40.657452, -73.963991,
        "Skate Rental Area", "BK99", "Brooklyn", 11215,
        "Limited Free", "GuestWiFi", "Prospect Park",
        "ALTICEUSA", "3 free 10 min sessions");

    ArrayList<Hotspot> expectedHotspots = new ArrayList<Hotspot>();
    expectedHotspots.add(hotspot998);
    expectedHotspots.add(hotspot26);

    for(int i=0; i<expectedHotspots.size(); i++) {
      assertTrue(expectedHotspots.get(i).equals(hotspots.get(i)));
    }
  }

  /**
   * Tests the Reader to verify that it collects the right Retailer information from a csv
   * and does not miss any entries
   * @throws FileNotFoundException Throws exception if file is not found
   */
  @Test
  public void readRetailers() throws FileNotFoundException{
    List<Retailer> Retailers = reader.readRetailers("/test/retailers.csv", false);
    Retailer r1, r2, r3;
    r1 = new Retailer("Starbucks Coffee", "3 New York Plaza", "", "New York",
        "NY", 10004, "8-32", "Casual Eating & Takeout",
        "F-Coffeehouse",40.7375659,-74.04536639999999);
    r2 = new Retailer("New York Health & Racquet Club", "39 Whitehall Street", "",
        "New York", "NY", 10004, "8-32",
        "Personal and Professional Services", "P-Athletic Clubs/Fitness",
        40.7029437,-74.0126761);
    r3 = new Retailer("A.J. Kelly's", "6 Stone Street", "", "New York",
        "NY", 10004, "10-32", "Full Service Dining",
        "F-Irish Pub",40.0514416,-95.60269609999999);

    List<Retailer> actualRetailers = Arrays.asList(r1, r2, r3);
    for (int i = 0; i < Retailers.size(); i++) {
      assertTrue(Retailers.get(i).equals(actualRetailers.get(i)));
    }

  }

  @Test
  public void readExternalRetailerWithEmptyEndingCells() throws FileNotFoundException{
    List<Retailer> Retailers = reader.readRetailers("src/main/resources/test/retailerEmptyEnds.csv", true);
    Retailer r1, r2;
    r1 = new Retailer("Starbucks Coffee", "3 New York Plaza", "", "New York",
        "NY", 10004, "", "",
        "");
    r2 = new Retailer("A.J. Kelly's", "6 Stone Street", "", "New York",
        "NY", 10004, "10-32", "Full Service Dining",
        "");
    List<Retailer> actualRetailers = Arrays.asList(r1, r2);
    for (int i=0; i < Retailers.size(); i++) {
      assertTrue(Retailers.get(i).equals(actualRetailers.get(i)));
    }
  }

  //TODO ADD JAVADOC
  @Test
  public void readUserPOIS() throws FileNotFoundException {
    ArrayList<UserPOI> POIs = reader.readUserPOIS("/test/POIS.csv", false);
    ArrayList<UserPOI> expectedPOIs = new ArrayList<UserPOI>();

    UserPOI p1, p2, p3;
    //Location,Name,Description
    p1 = new UserPOI(40.7484,-73.9857,"Empire State Building","The Empire State Building is a 102-story skyscraper");
    p2 = new UserPOI(40.7829,-73.9654,"Central Park","Central Park is an urban park in Manhattan");
    p3 = new UserPOI(40.6892,-74.0445,"Statue Of Liberty","The Statue of Liberty is a colossal neoclassical sculpture on Liberty Island in New York Harbor");

    expectedPOIs.add(p1);
    expectedPOIs.add(p2);
    expectedPOIs.add(p3);

    for(int i = 0; i < expectedPOIs.size(); i++) {
      assertTrue(expectedPOIs.get(i).equals(POIs.get(i)));
    }
  }

  //TODO ADD JAVADOC
  @Test
  public void readPublicPOIS() throws FileNotFoundException {
    ArrayList<PublicPOI> POIs = reader.readPublicPOIS("/test/PublicPOIS.csv", false);
    ArrayList<PublicPOI> expectedPOIs = new ArrayList<PublicPOI>();

    PublicPOI p1, p2, p3;
    //Location,Name,Description
    p1 = new PublicPOI(40.689209,-74.044457,"Statue of Liberty","Iconic National Monument opened in 1886, offering guided tours, a museum & city views.");
    p2 = new PublicPOI(40.758936,-73.985119,"Times Square","Bustling destination in the heart of the Theater District known for bright lights, shopping & shows.");
    p3 = new PublicPOI(40.782857,-73.965355,"Central Park","Sprawling park with pedestrian paths & ballfields, plus a zoo, carousel, boat rentals & a reservoir.");

    expectedPOIs.add(p1);
    expectedPOIs.add(p2);
    expectedPOIs.add(p3);

    for(int i = 0; i < expectedPOIs.size(); i++) {
      assertTrue(expectedPOIs.get(i).equals(POIs.get(i)));
    }
  }

  /**
   * Test the Reader to ensure it extracts Route csv data correctly
   * @throws FileNotFoundException Throws exception if file can't be found
   */
  @Test
  public void readRoutes() throws FileNotFoundException {
    ArrayList<Station> stations = new ArrayList<Station>();

    Station startStation1, stopStation1, startStation2, stopStation2, startStation3, stopStation3;
    startStation1 = new Station(164,"E 47 St & 2 Ave",40.75323098,-73.97032517);
    stopStation1 = new Station(504,"1 Ave & E 15 St",40.73221853,-73.98165557);
    startStation2 = new Station(531,"Forsyth St & Broome St",40.71893904,-73.99266288);
    stopStation2 = new Station(499,"Broadway & W 60 St",40.76915505,-73.98191841);
    startStation3 = new Station(345,"W 13 St & 6 Ave",40.73649403,-73.99704374);
    stopStation3 = new Station(455,"1 Ave & E 44 St",40.75001986,-73.96905301);

    stations.add(startStation1);
    stations.add(stopStation1);
    stations.add(startStation2);
    stations.add(stopStation2);
    stations.add(startStation3);
    stations.add(stopStation3);

    ArrayList<Route> routes = reader.readRoutes("/test/TestInitialRoutes.csv", stations, false);

    // Expected routes
    Date startDateTime1 = new filehandler.Reader().StringToDate("7/1/13 0:00", "MM/dd/yyyy HH:mm");
    Date stopDateTime1 = new filehandler.Reader().StringToDate("7/1/13 0:10", "MM/dd/yyyy HH:mm");
    Route route1 = new Route(634, startDateTime1, stopDateTime1, startStation1, stopStation1,16950,"Customer",-1,0);

    Date startDateTime2 = new filehandler.Reader().StringToDate("7/1/13 0:01", "MM/dd/yyyy HH:mm");
    Date stopDateTime2 = new filehandler.Reader().StringToDate("7/1/13 0:27", "MM/dd/yyyy HH:mm");
    Route route2 = new Route(1580, startDateTime2, stopDateTime2, startStation2, stopStation2,16063,"Customer",-1,0);

    Date startDateTime3 = new filehandler.Reader().StringToDate("7/1/13 0:04", "MM/dd/yyyy HH:mm");
    Date stopDateTime3 = new filehandler.Reader().StringToDate("7/1/13 0:25", "MM/dd/yyyy HH:mm");
    Route route3 = new Route(1275, startDateTime3, stopDateTime3, startStation3, stopStation3,16236,"Subscriber",1983,1);

    ArrayList<Route> expectedRoutes = new ArrayList<Route>();
    expectedRoutes.add(route1);
    expectedRoutes.add(route2);
    expectedRoutes.add(route3);

    for(int i=0; i<expectedRoutes.size(); i++) {
      assertTrue(expectedRoutes.get(i).equals(routes.get(i)));
    }
  }

  @Test
  public void readStations() throws FileNotFoundException, ParseException{
    ArrayList<Station> stations = reader.readStations("/file/stations.json");

    ArrayList<Station> compareStations = new ArrayList<Station>();

    Date date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").parse("2017-09-20 10:14:27 PM");
    Date date2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").parse("2017-09-20 10:13:52 PM");
    Date date3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").parse("2017-09-20 10:14:02 PM");

    Station station1 = new Station(72,"W 52 St & 11 Ave",28,39,40.76727216,-73.99392888,"In Service",1,10,"W 52 St & 11 Ave","","","","","",false,date1,"");
    Station station2 = new Station(79,"Franklin St & W Broadway",18,33,40.71911552,-74.00666661,"In Service",1,14,"Franklin St & W Broadway","","","","","",false,date2,"");
    Station station3 = new Station(82,"St James Pl & Pearl St",3,27,40.71117416,-74.00016545,"In Service",1,24,"St James Pl & Pearl St","","","","","",false,date3,"");

    compareStations.add(station1);
    compareStations.add(station2);
    compareStations.add(station3);

    for(int i = 0;i < compareStations.size()-1;i++) {
      //System.out.println(stations.get(i).toString());
      //System.out.println(compareStations.get(i).toString());
      assertTrue(stations.get(i).toString().equals(compareStations.get(i).toString()));
    }
  }
}