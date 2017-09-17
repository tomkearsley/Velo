package modeltest;


import model.POI;
import model.PublicPOI;
import model.UserPOI;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class POITest {

  private double[] testLocation1;
  private POI testPOI;
  private double[] testLocation2;
  private String empireString;
  private PublicPOI testPublicPOI;
  private double[] l3;
  private String userHouse;
  private UserPOI up1;
  private double[] testArray1;
  private double[] testArray2;
  private double delta;

  @Before
  public void setUpPOI() {
    double longitude1 = 40.719011;
    double latitude1 = -73.908305;
    double longitude2 = 40.748652;
    double latitude2 = -73.985773;
    testLocation1 = new double[]{40.719011, -73.908305};
    testPOI = new POI(longitude1,latitude1, "New York Center", "");
    testLocation2 = new double[]{40.748652, -73.985773};
    empireString = "Iconic, art deco office tower from 1931 with exhibits "
        + "& observatories on the 86th & 102nd floors.";
    testPublicPOI = new PublicPOI(longitude2,latitude2, "Empire State Building", empireString);
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
    double[] p1Location = {testPOI.getLongitude(),testPOI.getLatidude()};
    double[] testPublicPOILocation = {testPublicPOI.getLongitude(),testPublicPOI.getLatidude()};
    assertArrayEquals(testArray1,p1Location , delta);
    assertArrayEquals(testArray2, testPublicPOILocation, delta);
  }

  /**
   * Checks names for POI, User POI & Public POI are correctly set.
   */
  @Test
  public void NameTest() {
    assertEquals("New York Center", testPOI.getName());
    assertEquals("Empire State Building", testPublicPOI.getName());
    assertEquals("My House", up1.getName());
  }

  /**
   * Checks descriptions for User POI and Public POI are set correctly
   */
  @Test
  public void DescriptionTestUser() {
    assertEquals(userHouse, up1.getDescription());
  }

  /**
   * Checks descriptions for public POI
   */
  @Test
  public void DescriptionTestPublic() {
    assertEquals(empireString, testPublicPOI.getDescription());
  }
}
