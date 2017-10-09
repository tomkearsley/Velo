package helper;


import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TextField;
import model.Hotspot;
import model.PublicPOI;
import model.Retailer;
import model.Route;
import model.Station;
import model.UserPOI;

/**
 * static class containing all filters used by the different data tables
 */
public class filters {
  public static int HotspotSelectedIndex;

  /**
   * Determines if string is an integer
   *
   * @param s String  to test
   * @return true if String is an integer
   */
  private static boolean isInteger(String s) {
    try {
      Integer i = Integer.parseInt(s);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return true;
  }

  /**
   * Filters the given filteredList by predetermined fields (borough, type, provider)
   *
   * @return filtered version of fListHotspots
   */
  public static FilteredList<Hotspot> hotspotFilter1(TextField HotspotFilterField,
      FilteredList<Hotspot> fListHotspots) {

    HotspotFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
      fListHotspots.setPredicate(Hotspot -> {
        //if filter is empty, show all
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();
        // Add more Hotspot.get__'s below to include more things in the search
        System.out.println(HotspotSelectedIndex);
        switch (HotspotSelectedIndex) {
          case 0: //NAME
            if(Hotspot.getName().toLowerCase().contains(lowerCaseFilter)) {
              return true;
            }
            break;
          case 1: //BOROUGH
            if(Hotspot.getBorough().toLowerCase().contains(lowerCaseFilter)) {
              return true;
            }
            break;
          case 2: //TYPE
            if(Hotspot.getType().toLowerCase().contains(lowerCaseFilter)) {
              return true;
            }
            break;
          case 3: //PROVIDER
            if(Hotspot.getProvider().toLowerCase().contains(lowerCaseFilter)) {
              return true;
            }
            break;
          default:
            return false;
        }
        return false;
      });
    });

    return fListHotspots;
  }

  /**
   * Filters the given FilteredList by predetermined fields (Address, Name, zipcode)
   *
   * @return filtered version of fListRetailers
   */
  public static FilteredList<Retailer> retailerFilter(TextField RetailerFilterField,
      FilteredList<Retailer> fListRetailers) {
    RetailerFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
      fListRetailers.setPredicate(Retailer -> {
        //if filter is empty, show all
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();
        /*
         * Add more Retailer.get__'s below to include more things in the search
         */
        if (Retailer.getAddress().toLowerCase().contains(lowerCaseFilter) || Retailer.getName()
            .toLowerCase().contains(lowerCaseFilter)) {
          return true;
        }
        //checking for zipcode. entire zipcode must be entered before a match is found
        if (isInteger(lowerCaseFilter)) {
          Integer input = Integer.parseInt(lowerCaseFilter);
          if (input == Retailer.getZipcode()) {
            return true;
          }
        }
        return false;
      });
    });
    return fListRetailers;
  }

  /**
   * Filters the given filteredList by predetermined fields (Name)
   *
   * @return filtered version of fListPublicPOIs
   */
  public static FilteredList<PublicPOI> publicPOIFilter(TextField PublicPOIFilterField,
      FilteredList<PublicPOI> fListPublicPOIs) {
    PublicPOIFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
      fListPublicPOIs.setPredicate(PublicPOI -> {
        //if filter is empty, show all
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();
        // Add more Hotspot.get__'s below to include more things in the search

        if (PublicPOI.getName().toLowerCase().contains(lowerCaseFilter)) {
          return true;
        }
        return false;
      });
    });
    return fListPublicPOIs;
  }

  /**
   * Filters the given filteredList by predetermined fields (Name)
   *
   * @return filtered version of fListUserPOIs
   */
  public static FilteredList<UserPOI> userPOIFilter(TextField UserPOIFilterField,
      FilteredList<UserPOI> fListUserPOIs) {
    UserPOIFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
      fListUserPOIs.setPredicate(UserPOI -> {
        //if filter is empty, show all
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();
        /*
         * Add more UserPOI.get__'s below to include more things in the search
         */
        if (UserPOI.getName().toLowerCase().contains(lowerCaseFilter)) {
          return true;
        }
        return false;
      });
    });
    return fListUserPOIs;
  }

  /**
   * Filters the given filteredList by predetermined fields (Name)
   *
   * @return filtered version of fListStations
   */
  public static FilteredList<Station> stationFilter(TextField StationFilterField,
      FilteredList<Station> fListStations) {
    StationFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
      fListStations.setPredicate(Station -> {
        //if filter is empty, show all
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();
        /*
         * Add more Station.get__'s below to include more things in the search
         */
        if (Station.getName().toLowerCase().contains(lowerCaseFilter)) {
          return true;
        }
        return false;
      });
    });

    return fListStations;
  }

  /**
   * Filters the given filteredList by predetermined fields (Start/stop location names, bike ID,
   * gender of cyclist)
   *
   * @return filtered version of fListRoutes
   */
  public static FilteredList<Route> routeFilter(TextField RouteFilterField,
      FilteredList<Route> fListRoutes) {
    RouteFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
      fListRoutes.setPredicate(Route -> {
        //if filter is empty, show all
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();
        /*
         * Add more Route.get__'s below to include more things in the search
         */
        if (Route.getStartStation().getName().toLowerCase().contains(lowerCaseFilter) ||
            Route.getStopStation().getName().toLowerCase().contains(lowerCaseFilter)) {
          return true;
        }
        //filtering by gender of rider or bike ID. needs to be an exact match before anything is shown
        if (isInteger(lowerCaseFilter)) {
          Integer input = Integer.parseInt(lowerCaseFilter);
          if (Route.getBikeID() == input || Route.getGender() == input) {
            return true;
          }
        }
        return false;
      });
    });
    return fListRoutes;
  }
}
