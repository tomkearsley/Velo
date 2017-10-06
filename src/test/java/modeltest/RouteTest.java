package modeltest;

import static org.junit.Assert.*;

import filehandler.Reader;
import java.util.ArrayList;
import model.Hotspot;
import model.POI;
import model.PublicPOI;
import model.Retailer;
import model.Route;
import model.Station;
import model.UserPOI;
import org.junit.Before;
import org.junit.Test;

public class RouteTest {

  private Station station1;
  private Station station2;
  private Station station3;
  private PublicPOI publicPOI1;
  private UserPOI userPOI1;
  private Retailer retailer1;
  private Hotspot hotspot1;

  Route two_points;
  Route three_points; //start, retailer, stop

  @Before
  public void setUp() throws Exception {
    Reader rdr = new Reader();
    /*
    test points:
     */
    //station1 uses full constructor, other two use shortened but are actual points
    station1 = new Station(123, "First Station", 30, 31, 40, -70, "q", 2, 2, "Here", "There",
        "New York", "10004", "NY", "123", true,
        rdr.StringToDate("01/25/2017 12:34", "MM/dd/yyyy HH:mm"), "Thing");
    station2 = new Station(164, "E 47 St & 2 Ave", 40.75323098, -73.97032517);
    station3 = new Station(504, "1 Ave & E 15 St", 40.73221853, -73.98165557);
    hotspot1 = new Hotspot(998, 40.745968, -73.994039,
        "179 WEST 26 STREET", "MN17", "New York", 10001,
        "Free", "LinkNYC Free Wi-Fi", "mn-05-123662",
        "LinkNYC - Citybridge", "Tablet Internet -phone , Free 1 GB Wi-FI Service");
    retailer1 = new Retailer("Starbucks Coffee", "3 New York Plaza", "", "New York",
        "NY", 10004, "8-32", "Casual Eating & Takeout",
        "F-Coffeehouse", 40.7375659, -74.04536639999999);
    publicPOI1 = new PublicPOI(40.689209, -74.044457, "Statue of Liberty",
        "Iconic National Monument opened in 1886, offering guided tours, a museum & city views.");
    userPOI1 = new UserPOI(40.7484, -73.9857, "Empire State Building",
        "The Empire State Building is a 102-story skyscraper");

    /*
    test routes:
     */
    /* just contains station1 and station2 */
    two_points = new Route(12, rdr.StringToDate("01/25/2017 12:34", "MM/dd/yyyy HH:mm"),
        rdr.StringToDate("01/25/2017 12:34", "MM/dd/yyyy HH:mm"),
        station1, station2, 23, "userType", 1999, 0);

    /* copy + paste of two_points but overriding the mapPoints */
    three_points = new Route(12, rdr.StringToDate("01/25/2017 12:34", "MM/dd/yyyy HH:mm"),
        rdr.StringToDate("01/25/2017 12:34", "MM/dd/yyyy HH:mm"),
        station1, station2, 23, "userType", 1999, 0);
    ArrayList<POI> newPoints = new ArrayList<>();
    newPoints.add(station2);
    newPoints.add(publicPOI1);
    newPoints.add(station3);
    three_points.setMapPoints(newPoints);
  }

  @Test
  public void Start_NotNull() {
    Station start = two_points.getStartStation();
    assertNotNull(start);
  }

  @Test

  public void Stop_NotNull() {
    Station stop = two_points.getStartStation();
    assertNotNull(stop);
  }

  @Test
  /*
   * testing that an attribute unique to station exists
   */
  public void Start_is_Station() {
    Station start = two_points.getStartStation();
    assertTrue(start.getAltitude().equals("123"));
  }

  @Test
  public void TestInsertPointFirst() {
    two_points.insertPointFirst(retailer1);
    ArrayList<POI> actual;
    actual = two_points.getMapPoints();
    ArrayList<POI> expected = new ArrayList<>();
    expected.add(station1);
    expected.add(retailer1);
    expected.add(station2);
    assertTrue(expected.get(0) == actual.get(0)
        && expected.get(1) == actual.get(1)
        && expected.get(2) == actual.get(2));
    //because the line below doesn't work
    //assertTrue(expected.equals(actual));

    //test that points are in correct order
  }

  @Test
  public void TestInsertPointLast() {
    //System.out.println("before:" + three_points.getMapPoints().toString());
    three_points.insertPointLast(hotspot1);
    System.out.println(three_points.getMapPoints().size());
    assertTrue(three_points.getMapPoints().get(1) == publicPOI1
        && three_points.getMapPoints().get(2) == hotspot1);
  }
}