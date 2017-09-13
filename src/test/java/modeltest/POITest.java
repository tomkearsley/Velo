package modeltest;


import model.POI;
import model.PublicPOI;
import model.UserPOI;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class POITest {

  double[] l1;
  POI p1;
  double[] l2;
  String empireString;
  PublicPOI pp1;
  double[] l3;
  String userHouse;
  UserPOI up1;
  double[] testArray1;
  double[] testArray2;
  double delta;

  @Before
  public void setUpPOI() {
    double longitude1 = 40.719011;
    double latitude1 = -73.908305;
    double longitude2 = 40.748652;
    double latitude2 = -73.985773;
    double longitude3;
    double latitude3;
    l1 = new double[]{40.719011, -73.908305};
    p1 = new POI(longitude1,latitude1, "New York Center", "");
    l2 = new double[]{40.748652, -73.985773};
    empireString = "Iconic, art deco office tower from 1931 with exhibits "
        + "& observatories on the 86th & 102nd floors.";
    pp1 = new PublicPOI(longitude2,latitude2, "Empire State Building", empireString);
    l3 = new double[]{};
    userHouse = "My House";
    //up1 = new UserPOI(l3, "My House", userHouse);
    testArray1 = new double[]{40.719011, -73.908305};
    testArray2 = new double[]{40.748652, -73.985773};
    delta = 1.0;
  }


  /**
   * Checks locations for POI, User POI & Public POI are set correctly
   */
  @Test
  public void LocationTest() {
    double[] p1Location = {p1.getLongitude(),p1.getLatidude()};
    double[] pp1Location = {pp1.getLongitude(),pp1.getLatidude()};
    assertArrayEquals(testArray1,p1Location , delta);
    assertArrayEquals(testArray2, pp1Location, delta);
  }

  /**
   * Checks names for POI, User POI & Public POI are correctly set.
   */
  @Test
  public void NameTest() {
    assertEquals("New York Center", p1.getName());
    assertEquals("Empire State Building", pp1.getName());
    assertEquals("My House", up1.getName());
  }

  /**
   * Checks descriptions for User POI and Public POI are set correctly
   */
  @Test
  public void DescriptionTestUser() {
    assertEquals(userHouse, up1.getDescription());
  }

  @Test
  public void DescriptionTestPublic() {
    assertEquals(empireString, pp1.getDescription());
  }
}
