package modeltest;

import static org.junit.Assert.*;

import filehandler.Reader;
import model.Route;
import model.Station;
import org.junit.Before;
import org.junit.Test;

public class RouteTest {
  Station station1;
  Station station2;
  Route two_points;

  @Before
  public void setUp() throws Exception {
    Reader rdr = new Reader();
    station1 = new Station(123, "First Station", 30, 31, 40, -70, "q", 2, 2, "Here", "There", "New York", "10004", "NY", "123", true, rdr.StringToDate("01/25/2017 12:34", "MM/dd/yyyy HH:mm"), "Thing");
    station2 = new Station( 321, "Second Station", 41,-70.1);
    two_points = new Route(12, rdr.StringToDate("01/25/2017 12:34", "MM/dd/yyyy HH:mm"), rdr.StringToDate("01/25/2017 12:34", "MM/dd/yyyy HH:mm"),
        station1, station2, 23, "userType", 1999, 0);
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
  /**
   * testing an attribute unique to station exists
   */
  public void Start_is_Station() {
    Station start = two_points.getStartStation();
    assertTrue(start.getAltitude() == "123");
  }

  //TODO add tests for the two insert method
}