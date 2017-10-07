package controller;

import filehandler.Reader;
import filehandler.Writer;
import helper.Bridge;
import helper.tableOnClickPopup;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Worker.State;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Hotspot;
import model.PublicPOI;
import model.Retailer;
import model.Route;
import model.Station;
import model.UserPOI;
import netscape.javascript.JSObject;


/**
 * The controller class for the main.fxml file
 */
public class MainController {

  /* Main tabs */
  @FXML
  private TabPane mainPane;
  @FXML
  private Tab mapViewTab;
  @FXML
  private Tab dataViewTab;
  @FXML
  private Tab historyViewTab;
  @FXML
  private Tab userViewTab;


  @FXML
  private AnchorPane mapViewPane;

  @FXML
  private Pane userPane;
  @FXML
  private TabPane dataTabPane;
  @FXML
  private WebView mapWebView;

  // ArrayLists of all data types
  private ArrayList<Hotspot> hotspots = new ArrayList<Hotspot>();
  private ArrayList<Retailer> retailers = new ArrayList<Retailer>();
  private ArrayList<UserPOI> userPOIs = new ArrayList<UserPOI>();
  private ArrayList<PublicPOI> publicPOIs = new ArrayList<PublicPOI>();
  private ArrayList<Route> routes = new ArrayList<Route>();
  private ArrayList<Station> stations = new ArrayList<Station>();
  private ArrayList<ImageView> buttons = new ArrayList<ImageView>(); // TODO specify what this is

  /* Data tab attributes */
  // Toggling detailed view in table view
  private boolean hotspotIsDetailed = false;
  private boolean retailerIsDetailed = false;
  //private boolean userPOIIsDetailed = false;
  //private boolean publicPOIIsDetailed = false;
  private boolean routeIsDetailed = false;
  //private boolean stationIsDetailed = false;

  //Data tables
  @FXML
  private TableView<Hotspot> dataTableHotspot;
  @FXML
  private TableView<Retailer> dataTableRetailer;
  @FXML
  private TableView<PublicPOI> dataTablePublicPOI;
  @FXML
  private TableView<UserPOI> dataTableUserPOI;
  @FXML
  private TableView<Station> dataTableStation;
  @FXML
  private TableView<Route> dataTableRoute;

  // Table filter fields. one for each table view
  @FXML
  private TextField HotspotFilterField;
  @FXML
  private TextField RetailerFilterField;
  @FXML
  private TextField PublicPOIFilterField;
  @FXML
  private TextField UserPOIFilterField;
  @FXML
  private TextField StationFilterField;
  @FXML
  private TextField RouteFilterField;


  /* Map tab attributes */
  private JSObject window;
  private Bridge aBridge = new Bridge();
  @FXML
  private ImageView wifi_icon_primary;
  @FXML
  private ImageView retailer_icon_primary;
  @FXML
  private ImageView poi_icon_primary;
  @FXML
  private ImageView wifi_icon_secondary;
  @FXML
  private ImageView retailer_icon_secondary;
  @FXML
  private ImageView poi_icon_secondary;
  @FXML
  private ImageView station_icon_primary;
  @FXML
  private ImageView station_icon_secondary;
  @FXML
  private ImageView ppoi_icon_primary;
  @FXML
  private ImageView ppoi_icon_secondary;

  @FXML
  private TextField locationFrom;
  @FXML
  private TextField locationTo;
  private boolean hotspotsLoaded = false;
  private boolean stationsLoaded = false;
  private boolean POISLoaded = false;
  private boolean retailersLoaded = false;

  /* Account tab attributes */
  @FXML
  private ChoiceBox importType; // ChoiceBox for the Account import button


  /* METHODS */

  /**
   * Initializes the window Populates the model structure with data from .csv files Sets GUI element
   * features (i.e. images for the tabs) populateArrayLists() TODO adapt to using database primarily
   * with csv as fallback
   */
  public void initialize() throws URISyntaxException {

    boolean ArrayListsIsPopulated = populateArrayLists();
    if (!ArrayListsIsPopulated) {
      //TODO bring up warning window when that is implemented
      Alert loadingError = new Alert(AlertType.ERROR, "Couldn't load initial data", ButtonType.OK);
      loadingError.showAndWait();
    }

    // TABS INITIALIZATION / SET IMAGES
    // TODO set tabs to images from resources/images

    // MAPS TAB INITIALIZATION
    URL url = getClass().getResource("/googleMaps.html");
    WebEngine mapEngine = mapWebView.getEngine();
    mapEngine.setJavaScriptEnabled(true);
    String[] dataTypeStrings = new String[]{"Hotspot", "Retailer", "Public POI", "User POI",
        "Route"};
    ObservableList<String> dataTypes = FXCollections.observableArrayList(dataTypeStrings);
    importType.setItems(dataTypes);
    importType.setValue("Hotspot");
    setImages(); // Set map images
    mapEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
      if (newState == State.SUCCEEDED) {
        window = (JSObject) mapEngine.executeScript("window");
        window.setMember("aBridge", aBridge);
        System.out.println(
            "Initialisation complete"); // Maybe don't let them switch to map view until this is initialised or just default to table view to give time for this to load.
      }
    });
    mapEngine.load(url.toExternalForm());
    mapEngine.setJavaScriptEnabled(true);

    // DATA TAB INITIALIZATION
    initRetailerTable(); // Create tables
    initHotspotTable();
    initPublicPOITable();
    initUserPOITable();
    initStationTable();
    initRouteTable();
  }

  /* Tab action handlers */
  public void viewData() {
    mainPane.getSelectionModel().select(dataViewTab);
    //dataTabPane.toFront();
  }
  public void viewMap() {
    // mapViewPane.();
    mainPane.getSelectionModel().select(mapViewTab);
  }
  public void viewUser() {
    mainPane.getSelectionModel().select(userViewTab);
    // userPane.toFront();
  }
  public void viewHistory() {
    mainPane.getSelectionModel().select(historyViewTab);
  }

  private void loadHotspots() throws IOException {
    Reader rdr = new Reader();
    //Run both lines of code
    window.setMember("aBridge", aBridge);
    window.call("loadHotspots", rdr.readHotspots("/file/InitialHotspots.csv", false));
    hotspotsLoaded = true;
    //testPretty();
  }

  private void loadStations() throws IOException {
    Reader rdr = new Reader();
    window.setMember("aBridge", aBridge);
    window.call("loadStations", rdr.readStations("/file/stations.json"));
    stationsLoaded = true;
  }

  public void loadPOIS() throws IOException {
    Reader rdr = new Reader();
    window.setMember("aBridge", aBridge);
    window.call("loadPOIS", rdr.readUserPOIS("/file/POIS.csv", false));
    POISLoaded = true;
  } // TODO implement Imas

  private void loadRetailers() throws IOException {
    Reader rdr = new Reader();
    window.setMember("aBridge", aBridge);
    window.call("loadRetailers", rdr.readRetailers("/file/InitialRetailers.csv", false));
    retailersLoaded = true;
  }

  public void loadPPOIS() throws IOException {
    Reader rdr = new Reader();
    window.setMember("aBridge", aBridge);
    window.call("loadPPOIS", rdr.readPublicPOIS("/file/PPOIS.csv", false));
  }

  /**
   * populates arrayLists used for temporary local storage
   *
   * @return true if populating was successful, otherwise false
   */
  private boolean populateArrayLists() {
    Reader rdr = new Reader();
    try {
      //hotspots = rdr.readHotspots("/file/InitialHotspots.csv", 0);
      //MySQL mysql = new MySQL();
      //hotspots = mysql.getHotspots();

      //retailers = rdr.readRetailers("/file/InitialRetailers.csv");
      hotspots = rdr.readHotspots("/file/InitialHotspots.csv", false);
      retailers = rdr.readRetailers("/file/InitialRetailers.csv", false);
      stations = rdr.readStations("/file/stations.json");
      userPOIs = rdr.readUserPOIS("/file/UserPOIdata_smallsample.csv", false);
      publicPOIs = rdr.readPublicPOIS("/file/PublicPOIdata_smallsample.csv", false);
      routes = rdr.readRoutes("/file/tripdata_smallsample.csv", stations, false);
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  private void setImages() throws URISyntaxException {
    wifi_icon_primary
        .setImage(new Image(getClass().getResource("/image/hotspot-icon.png").toURI().toString()));
    retailer_icon_primary
        .setImage(new Image(getClass().getResource("/image/retailer-icon.png").toURI().toString()));
    poi_icon_primary
        .setImage(new Image(getClass().getResource("/image/marker-icon.png").toURI().toString()));
    station_icon_primary
        .setImage(new Image(getClass().getResource("/image/station-icon.png").toURI().toString()));
    ppoi_icon_primary
        .setImage(new Image(getClass().getResource("/image/ppoi-icon.png").toURI().toString()));

    wifi_icon_secondary.setImage(
        new Image(getClass().getResource("/image/hotspot-pressed-icon.png").toURI().toString()));
    retailer_icon_secondary.setImage(
        new Image(getClass().getResource("/image/retailer-pressed-icon.png").toURI().toString()));
    poi_icon_secondary.setImage(
        new Image(getClass().getResource("/image/marker-pressed-icon.png").toURI().toString()));
    station_icon_secondary.setImage(
        new Image(getClass().getResource("/image/station-pressed-icon.png").toURI().toString()));
    ppoi_icon_secondary
        .setImage(new Image(getClass().getResource("/image/ppoi-icon.png").toURI().toString()));

    buttons.add(retailer_icon_primary);
    buttons.add(wifi_icon_primary);
    buttons.add(poi_icon_primary);
    buttons.add(station_icon_primary);

    buttons.add(retailer_icon_secondary);
    buttons.add(wifi_icon_secondary);
    buttons.add(poi_icon_secondary);
    buttons.add(station_icon_secondary);

    /*
    wifi_icon_primary.setVisible(false);
    retailer_icon_primary.setVisible(false);
    poi_icon_primary.setVisible(false);
    station_icon_primary.setVisible(false);
    */

    wifi_icon_secondary.setVisible(false);
    retailer_icon_secondary.setVisible(false);
    poi_icon_secondary.setVisible(false);
    station_icon_secondary.setVisible(false);
  }

  private void toggleButton(int buttonNo) {
    System.out.println("Toggling button" + (buttonNo + 1));
    buttons.get(buttonNo).setVisible(false);
    int halfButtonSize = buttons.size() / 2;
    //Check if button is primary or on-click
    if (buttonNo >= halfButtonSize) {
      buttons.get(buttonNo - halfButtonSize).setVisible(true);
    } else {
      buttons.get(buttonNo + halfButtonSize).setVisible(true);
    }
  } // TODO IMAS RENAME AND JAVADOC

  public void toggleButton1() {
    try {
      if (retailersLoaded) {
        showRetailers();
      } else {
        loadRetailers();
      }
    } catch (IOException e) {
      System.out.println("Error occurred while reading retailers");
      System.out.println(e);
    }
    toggleButton(0);
  } // TODO IMAS RENAME AND JAVADOC

  public void toggleButton2() {
    try {
      if (hotspotsLoaded) {
        showHotspots();
      } else {
        loadHotspots();
      }
    } catch (IOException e) {
      System.out.println("Error occurred while reading hotspots");
      System.out.println(e);
    }
    toggleButton(1);
  } // TODO IMAS RENAME AND JAVADOC

  public void toggleButton3() {
    toggleButton(2);
  } // TODO IMAS RENAME AND JAVADOC

  public void toggleButton4() {
    try {
      if (stationsLoaded) {
        showStations();
      } else {
        loadStations();
      }
    } catch (IOException e) {
      System.out.println("Error occurred while reading stations");
      System.out.println(e);
    }
    toggleButton(3);
  } // TODO IMAS RENAME AND JAVADOC

  public void toggleButton5() {
    try {
      hideRetailers();
    } catch (netscape.javascript.JSException e) {
      //null reference error. Should occur
      System.out.println(e);
    }
    toggleButton(4);

  } // TODO IMAS RENAME AND JAVADOC

  public void toggleButton6() {
    try {
      hideHotspots();
    } catch (netscape.javascript.JSException e) {
      //null reference error. Should occur
      //System.out.println("Internal error, please report to app devs");
    }
    toggleButton(5);
  } // TODO IMAS RENAME AND JAVADOC

  public void toggleButton7() {
    toggleButton(6);
  } // TODO IMAS RENAME AND JAVADOC

  public void toggleButton8() {
    try {
      hideStations();
    } catch (netscape.javascript.JSException e) {
      //null error. should be occuring
      //System.out.println("Internal error, please report to app devs");
      System.out.println(e);
    }
    toggleButton(7);
  } // TODO IMAS RENAME AND JAVADOC

  private void showHotspots() {
    window.setMember("aBridge", aBridge);
    window.call("showHotspots");
  }

  private void hideHotspots() {
    window.setMember("aBridge", aBridge);
    window.call("hideHotspots");
  }

  private void showStations() {
    window.setMember("aBridge", aBridge);
    window.call("showStations");
  }

  private void hideStations() {
    window.setMember("aBridge", aBridge);
    window.call("hideStations");
  }

  private void showRetailers() {
    window.setMember("aBridge", aBridge);
    window.call("showRetailers");
  }

  private void hideRetailers() {
    window.setMember("aBridge", aBridge);
    window.call("hideRetailers");
  }

  private void showPOIS() {
    window.setMember("aBridge", aBridge);
    window.call("showPOIS");
  } // TODO implement Imas

  private void hidePOIS() {
    window.setMember("aBridge", aBridge);
    window.call("hidePOIS");
  } // TODO implement Imas

  public void showPPOIS() {
    window.setMember("aBridge", aBridge);
    window.call("showPPOIS");
  }

  public void hidePPOIS() {
    window.setMember("aBridge", aBridge);
    window.call("hidePPOIS");
  }

  public void hideAllMarkers() {
    window.setMember("aBridge", aBridge);
    window.call("hideAllMarkers");
  }


  private void prettyMarker(double lat, double lng, String info, String markerType) {
    window.setMember("aBridge", aBridge);
    window.call("prettyMarker", lat, lng, info, markerType);
  }

  private void displayRoute(double startLat, double startLng, double endLat, double endLng) {
    window.setMember("aBridge", aBridge);
    window.call("displayRoute", startLat, startLng, endLat, endLng);
  }

  public void displayRouteClick() {
    window.setMember("aBridge", aBridge);
    window.call("displayRouteClick", locationFrom.getText(), locationTo.getText());
  }


  /* DATA TAB METHODS */

  /**
   * Method to create and remove columns containing additional details within the Hotspot table Uses
   * a global boolean hotspotIsDetailed to determine state
   */
  public void toggleDetailsHotspot() {
    //simple view: id, locAddress, borough, city, type, provider, remarks
    //advanced view: include above and lat, long, postcode, SSID
    if (hotspotIsDetailed) {
      //it is detailed, so remove columns
      dataTableHotspot.getColumns().remove(7, 11);
    } else {
      TableColumn<Hotspot, Double> latCol = new TableColumn<>("Latitude");
      latCol.setCellValueFactory(new PropertyValueFactory<>("latitude"));
      TableColumn<Hotspot, Double> longCol = new TableColumn<>("Longitude");
      longCol.setCellValueFactory(new PropertyValueFactory<>("longitude"));
      TableColumn<Hotspot, String> postCodeCol = new TableColumn<>("postcode");
      postCodeCol.setCellValueFactory(new PropertyValueFactory<>("postcode"));
      TableColumn<Hotspot, String> SSIDCol = new TableColumn<>("SSID");
      SSIDCol.setCellValueFactory(new PropertyValueFactory<>("SSID"));
      dataTableHotspot.getColumns().addAll(latCol, longCol, postCodeCol, SSIDCol);
    }
    hotspotIsDetailed = !hotspotIsDetailed;
  }


  /**
   * Method to create and remove columns containing additional details within the Retailer table
   * Uses a global boolean retailerIsDetailed to determine state
   */
  public void toggleDetailsRetailer() {
    if (retailerIsDetailed) {
      dataTableRetailer.getColumns().remove(3, 8);
    } else {
      TableColumn<Retailer, String> floorCol = new TableColumn<>("Floor");
      floorCol.setCellValueFactory(new PropertyValueFactory<>("floor"));
      TableColumn<Retailer, String> cityCol = new TableColumn<>("City");
      cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
      TableColumn<Retailer, String> zipcodeCol = new TableColumn<>("Zipcode");
      zipcodeCol.setCellValueFactory(new PropertyValueFactory<>("zipcode"));
      TableColumn<Retailer, String> stateCol = new TableColumn<>("State");
      stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
      TableColumn<Retailer, String> blockCol = new TableColumn<>("Block");
      blockCol.setCellValueFactory(new PropertyValueFactory<>("block"));
      dataTableRetailer.getColumns().addAll(floorCol, cityCol, zipcodeCol, stateCol, blockCol);
    }

    retailerIsDetailed = !retailerIsDetailed;
  }

  /**
   * Method to create and remove columns containing additional details within the Route table Uses a
   * global boolean routeIsDetailed to determine state
   */
  public void toggleDetailsRoute() {
    if (routeIsDetailed) {
      dataTableRoute.getColumns().remove(4, 8);
    } else {
      TableColumn<Route, Integer> bikeIDCol = new TableColumn<Route, Integer>("Bike ID");
      bikeIDCol.setCellValueFactory(new PropertyValueFactory<Route, Integer>("bikeID"));
      TableColumn<Route, String> userTypeCol = new TableColumn<Route, String>("User Type");
      userTypeCol.setCellValueFactory(new PropertyValueFactory<Route, String>("userType"));
      TableColumn<Route, Integer> birthYearCol = new TableColumn<Route, Integer>("Birth Year");
      birthYearCol.setCellValueFactory(new PropertyValueFactory<Route, Integer>("birthYear"));
      TableColumn<Route, Integer> genderCol = new TableColumn<Route, Integer>("Gender");
      genderCol.setCellValueFactory(new PropertyValueFactory<Route, Integer>("gender"));
      dataTableRoute.getColumns().addAll(bikeIDCol, userTypeCol, birthYearCol, genderCol);
    }
    routeIsDetailed = !routeIsDetailed;
  }

  /**
   * Determines if string is an integer
   *
   * @param s String  to test
   * @return true if String is an integer
   */
  private boolean isInteger(String s) {
    try {
      Integer i = Integer.parseInt(s);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return true;
  }

  /**
   * takes the arrayList retailers, creates table columns and sets these columns along with details
   * from retailers to be shown in the table. also enables filtering and sorting
   */
  private void initRetailerTable() {
    //converting the arraylist to an observable list
    ObservableList<Retailer> oListRetailers = FXCollections.observableArrayList(retailers);
    //each 2 line section creates one table heading and set of values
    //TODO lat and long from address?
    TableColumn<Retailer, String> nameCol = new TableColumn<>(
        "Name");//title to be written above column
    nameCol.setCellValueFactory(
        new PropertyValueFactory<>("name"));//looks for retailer.getName()
    TableColumn<Retailer, String> addressCol = new TableColumn<>("Address");
    addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

    TableColumn<Retailer, String> secondaryDescCol = new TableColumn<>(
        "Secondary Description");
    secondaryDescCol
        .setCellValueFactory(new PropertyValueFactory<>("secondaryDescription"));

    FilteredList<Retailer> fListRetailers = new FilteredList<Retailer>(oListRetailers);
    /*
     * Filtering:
     * if this returns true, the object is shown. If the filter field is empty,
     * or the attributes below match, then the object is shown
     */
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
    /*
     * Sorting:
     * wrapping the filtered list in a sorted list allows the user to click on the title
     * of a column and sort the entries in alphanumeric order
     */
    SortedList<Retailer> sListRetailers = new SortedList<>(fListRetailers);
    sListRetailers.comparatorProperty().bind(dataTableRetailer.comparatorProperty());
//simple: name, address, description
    //detailed: above and floor, city, zipcode, state, block
    //sets up simple view
    dataTableRetailer.getColumns()
        .setAll(nameCol, addressCol, secondaryDescCol);
    dataTableRetailer.setItems(sListRetailers);

    /*
     * on click behaviour
     */
    dataTableRetailer.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
          Retailer selected_item = dataTableRetailer.getSelectionModel().getSelectedItem();
          helper.tableOnClickPopup.create("Retailer", selected_item);
          if (tableOnClickPopup.return_value) {
            double[] loc = filehandler.Google.stringToLocation(selected_item.getAddress());
            //TODO change this when retailers have lat/long fields
            try {
              prettyMarker(loc[0], loc[1], "<b>Test</b>", "retailer");
              viewMap();
            } catch (NullPointerException e) {
              System.out.println("Map not yet loaded");
            }
          }
        }
      }
    });
  }

  /**
   * takes the arrayList hotspots, creates table columns and sets these columns along with details
   * from hotspots to be shown in the table. also enables filtering and sorting
   */
  private void initHotspotTable() {
    ObservableList<Hotspot> oListHotspots = FXCollections.observableArrayList(hotspots);

    TableColumn<Hotspot, String> idCol = new TableColumn<>("Name");
    idCol.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<Hotspot, String> locAddressCol = new TableColumn<Hotspot, String>("Address");
    locAddressCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("locationAddress"));
    TableColumn<Hotspot, String> boroughCol = new TableColumn<Hotspot, String>("Borough");
    boroughCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("borough"));
    TableColumn<Hotspot, String> cityCol = new TableColumn<Hotspot, String>("City");
    cityCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("city"));

    TableColumn<Hotspot, String> typeCol = new TableColumn<>("Type");
    typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

    TableColumn<Hotspot, String> nameCol = new TableColumn<>("Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    TableColumn<Hotspot, String> providerCol = new TableColumn<>("Provider");
    providerCol.setCellValueFactory(new PropertyValueFactory<>("provider"));
    TableColumn<Hotspot, String> remarksCol = new TableColumn<>("Remarks");
    remarksCol.setCellValueFactory(new PropertyValueFactory<>("description"));

    FilteredList<Hotspot> fListHotspots = new FilteredList<>(oListHotspots);
    HotspotFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
      fListHotspots.setPredicate(Hotspot -> {
        //if filter is empty, show all
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();
        // Add more Hotspot.get__'s below to include more things in the search

        if (Hotspot.getBorough().toLowerCase().contains(lowerCaseFilter) || Hotspot.getType()
            .toLowerCase().contains(lowerCaseFilter) || Hotspot.getProvider()
            .toLowerCase().contains(lowerCaseFilter)) {
          return true;
        }
        return false;
      });
    });

    SortedList<Hotspot> sListHotspots = new SortedList<>(fListHotspots);
    sListHotspots.comparatorProperty().bind(dataTableHotspot.comparatorProperty());

    //sets up simple view.
    dataTableHotspot.getColumns()
        .setAll(idCol, locAddressCol, boroughCol, cityCol, typeCol, providerCol, remarksCol);

    dataTableHotspot.setItems(sListHotspots);

    /*
     * on click behaviour
     */
    dataTableHotspot.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
          Hotspot selected_item = dataTableHotspot.getSelectionModel().getSelectedItem();
          helper.tableOnClickPopup.create("Hotspot",  selected_item);
          if (tableOnClickPopup.return_value) {
            try {
              prettyMarker(selected_item.getLatitude(), selected_item.getLongitude(),
                  selected_item.getLocationAddress(), "hotspot");
              viewMap();
            } catch (NullPointerException e) {
              System.out.println("Map not yet loaded");
            }
          }
        }
      }
    });
  }

  /**
   * takes the arrayList publicPOIs, creates table columns and sets these columns along with details
   * from publicPOIs to be shown in the table. also enables filtering and sorting
   */
  private void initPublicPOITable() {

    //lat, long, name, description
    ObservableList<PublicPOI> oListPublicPOIs = FXCollections.observableArrayList(publicPOIs);
    TableColumn<PublicPOI, Double> latCol = new TableColumn<>("Latitude");
    latCol.setCellValueFactory(new PropertyValueFactory<>("latitude"));
    TableColumn<PublicPOI, Double> longCol = new TableColumn<>("Longitude");
    longCol.setCellValueFactory(new PropertyValueFactory<>("longitude"));
    TableColumn<PublicPOI, String> nameCol = new TableColumn<>("Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    TableColumn<PublicPOI, String> descriptionCol = new TableColumn<>(
        "Description");
    descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

    FilteredList<PublicPOI> fListPublicPOIs = new FilteredList<>(oListPublicPOIs);
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

    SortedList<PublicPOI> sListPublicPOIs = new SortedList<>(fListPublicPOIs);
    sListPublicPOIs.comparatorProperty().bind(dataTablePublicPOI.comparatorProperty());

    dataTablePublicPOI.getColumns().setAll(nameCol, latCol, longCol, descriptionCol);
    dataTablePublicPOI.setItems(sListPublicPOIs);

    /*
     * on click behaviour
     */
    dataTablePublicPOI.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
          PublicPOI selected_item = dataTablePublicPOI.getSelectionModel().getSelectedItem();
          tableOnClickPopup.create("Public POI",  selected_item);
          if (tableOnClickPopup.return_value) {
            try {
              prettyMarker(selected_item.getLatitude(), selected_item.getLongitude(),
                  selected_item.getName(), "public-poi");
              viewMap();
            } catch (NullPointerException e) {
              System.out.println("Map not yet loaded");
            }
          }
        }
      }
    });
  }

  /**
   * takes the arrayList userPOIs, creates table columns and sets these columns along with details
   * from userPOIs to be shown in the table. also enables filtering and sorting
   */
  public void initUserPOITable() {
    //lat, long, name, description
    ObservableList<UserPOI> oListUserPOIs = FXCollections.observableArrayList(userPOIs);

    TableColumn<UserPOI, Double> latCol = new TableColumn<>("Latitude");
    latCol.setCellValueFactory(new PropertyValueFactory<>("latitude"));
    TableColumn<UserPOI, Double> longCol = new TableColumn<>("Longitude");
    longCol.setCellValueFactory(new PropertyValueFactory<>("longitude"));
    TableColumn<UserPOI, String> nameCol = new TableColumn<>("Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    TableColumn<UserPOI, String> descriptionCol = new TableColumn<>("Description");
    descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

    FilteredList<UserPOI> fListUserPOIs = new FilteredList<>(oListUserPOIs);
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

    SortedList<UserPOI> sListUserPOIs = new SortedList<>(fListUserPOIs);
    sListUserPOIs.comparatorProperty().bind(dataTableUserPOI.comparatorProperty());

    dataTableUserPOI.getColumns().setAll(nameCol, latCol, longCol, descriptionCol);
    dataTableUserPOI.setItems(sListUserPOIs);

    /*
     * on-click behaviour
     */
    dataTableUserPOI.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
          UserPOI selected_item = dataTableUserPOI.getSelectionModel().getSelectedItem();
          tableOnClickPopup.create("User POI",  selected_item);
          if (tableOnClickPopup.return_value) {
            try {
              prettyMarker(selected_item.getLatitude(), selected_item.getLongitude(),
                  selected_item.getName(), "user-poi");
              viewMap();
            } catch (NullPointerException e) {
              System.out.println("Map not yet loaded");
            }
          }
        }
      }
    });
  }

  /**
   * takes the arrayList stations, creates table columns and sets these columns along with details
   * from stations to be shown in the table. also enables filtering and sorting
   */
  public void initStationTable() {
    //latitude, longitude, name, ID
    ObservableList<Station> oListStations = FXCollections.observableArrayList(stations);

    TableColumn<Station, Double> latCol = new TableColumn<>("Latitude");
    latCol.setCellValueFactory(new PropertyValueFactory<>("latitude"));
    TableColumn<Station, Double> longCol = new TableColumn<>("Longitude");
    longCol.setCellValueFactory(new PropertyValueFactory<>("longitude"));
    TableColumn<Station, String> nameCol = new TableColumn<>("Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    TableColumn<Station, Integer> idCol = new TableColumn<>("ID");
    idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

    FilteredList<Station> fListStations = new FilteredList<>(oListStations);
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

    SortedList<Station> sListStations = new SortedList<>(fListStations);
    sListStations.comparatorProperty().bind(dataTableStation.comparatorProperty());

    dataTableStation.getColumns().setAll(nameCol, latCol, longCol, idCol);
    dataTableStation.setItems(sListStations);

    /*
     * on-click behaviour
     */
    dataTableStation.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
          Station selected_item = dataTableStation.getSelectionModel().getSelectedItem();
          tableOnClickPopup.create("Public POI",  selected_item);
          if (tableOnClickPopup.return_value) {
            try {
              prettyMarker(selected_item.getLatitude(), selected_item.getLongitude(),
                  selected_item.getName(), "station");
              viewMap();
            } catch (NullPointerException e) {
              System.out.println("Map not yet loaded");
            }
          }
        }
      }
    });
  }

  /**
   * takes the arrayList routes, creates table columns and sets these columns along with details
   * from routes to be shown in the table. also enables filtering and sorting
   */
  public void initRouteTable() {
    //startStation, stopStation, startDateTime, endDateTime, bikeID, userType, birthYear, gender
    ObservableList<Route> oListRoutes = FXCollections.observableArrayList(routes);

    TableColumn<Route, Station> startStationCol = new TableColumn<>("Start Station");
    startStationCol
        .setCellValueFactory(new PropertyValueFactory<>("startStationName"));
    TableColumn<Route, Station> stopStationCol = new TableColumn<>("Stop Station");
    stopStationCol.setCellValueFactory(new PropertyValueFactory<>("stopStationName"));
    TableColumn<Route, Date> startDateTimeCol = new TableColumn<>("Start Time");
    startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
    TableColumn<Route, Date> endDateTimeCol = new TableColumn<>("Stop Time");
    endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("stopDate"));

    FilteredList<Route> fListRoutes = new FilteredList<>(oListRoutes);

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

    SortedList<Route> sListRoutes = new SortedList<>(fListRoutes);
    sListRoutes.comparatorProperty().bind(dataTableRoute.comparatorProperty());
    //simple: start and end stations, start and end times
    //TODO calculate duration of a trip and put it in a column..?
    //sets up simple view
    dataTableRoute.getColumns()
        .setAll(startStationCol, stopStationCol, startDateTimeCol, endDateTimeCol);
    dataTableRoute.setItems(sListRoutes);

    dataTableRoute.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
          Route selected_item = dataTableRoute.getSelectionModel().getSelectedItem();
          tableOnClickPopup.create("Public POI",  selected_item);
          if (tableOnClickPopup.return_value) {
            try {

              displayRoute(selected_item.getStartStation().getLatitude(),
                  selected_item.getStartStation().getLongitude(),
                  selected_item.getStopStation().getLatitude(),
                  selected_item.getStopStation().getLongitude());
              viewMap();
            } catch (NullPointerException e) {
              System.out.println("Map not yet loaded");
            }
          }
        }
      }
    });
  }


  /* ACCOUNT TAB METHODS */

  /**
   * Launches the updateAccount window Tells the GUIManager to change to updateAccount window
   */
  public void updateAccount() throws Exception {
    GUIManager.getInstanceGUIManager().updateAccount();
  }

  /**
   * Tells GUIManager the user wants to log out
   */
  @FXML
  void logOut() throws Exception {
    GUIManager.getInstanceGUIManager().logOut();
  }

  /**
   * Allows the user to select a file to import data from, at which case the Import Function is
   * called.
   */
  public void selectImportFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open CSV File");
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"));
    //fileChooser.setInitialDirectory(new File("~$USER")); //TODO Default directory
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
      importData(selectedFile.getPath());
    }
  }

  /**
   * Allows the user to export their routes to a custom csv file. Shows alert based on result
   */
  public void exportUserRoutes() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Export CSV File");
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV FILES", "*.csv"));
    File saveFile = fileChooser.showSaveDialog(null);
    Alert alert = null;
    if (saveFile != null) {
      try {
        Writer writer = new Writer();
        if (saveFile.getPath().endsWith(".csv")) {
          writer.writeRoutesToFile(routes, saveFile.getPath());
        } else {
          writer.writeRoutesToFile(routes, saveFile.getPath() + ".csv");
        }
        alert = new Alert(AlertType.NONE, "Routes successfully exported", ButtonType.OK);

      } catch (IOException e) {
        alert = new Alert(AlertType.ERROR, "Error exporting routes", ButtonType.OK);
      } finally {
        alert.showAndWait();
      }
    }
  }

  /**
   * Imports additional items from a csv file to the appropriate ArrayList based on the selected
   * datatype from the ChoiceBox
   */
  public void importData(
      String importFilePath) { //TODO expand for rest of data types, file path differences
    Reader reader = new Reader();
    int prevSize;
    Alert alert = null;
    if (importType.getValue().equals("Hotspot")) {
      try {
        ArrayList<Hotspot> hotspotsToAdd = reader
            .readHotspots(importFilePath, true); //NOTE Will not work when importing
        // initial hotspots as external file due to index handling changes between internal & external files
        prevSize = hotspots.size();
        for (Hotspot hotspot : hotspotsToAdd) {
          hotspots.add(hotspot);
        }
        alert = new Alert(AlertType.NONE,
            hotspots.size() - prevSize + " Hotspots succesfully imported", ButtonType.OK);
        initHotspotTable();
      } catch (IOException e) {
        System.out.println("Error loading hotspots");
      }
    } else if (importType.getValue().equals("Retailer")) {
      try {
        ArrayList<Retailer> retailersToAdd = reader.readRetailers(importFilePath, true);
        prevSize = retailers.size();
        for (Retailer retailer : retailersToAdd) {
          retailers.add(retailer);
        }
        initRetailerTable();
        alert = new Alert(AlertType.NONE,
            retailers.size() - prevSize + " Retailers succesfully imported", ButtonType.OK);
      } catch (IOException e) {
        System.out.println("Error loading retailers");
      }
    } else if (importType.getValue().equals("Public POI")) {
      try {
        ArrayList<PublicPOI> publicPOIsToAdd = reader
            .readPublicPOIS(importFilePath, true);
        prevSize = publicPOIs.size();
        for (PublicPOI publicPOI : publicPOIsToAdd) {
          publicPOIs.add(publicPOI);
        }
        alert = new Alert(AlertType.NONE,
            publicPOIs.size() - prevSize + " Public POIs succesfully imported", ButtonType.OK);
        initPublicPOITable();
      } catch (IOException e) {
        System.out.println("Error loading public POIs");
      }
    } else if (importType.getValue().equals("User POI")) {
      try {
        ArrayList<UserPOI> userPOIsToAdd = reader
            .readUserPOIS(importFilePath, true);
        prevSize = userPOIs.size();
        for (UserPOI userPOI : userPOIsToAdd) {
          userPOIs.add(userPOI);
        }
        alert = new Alert(AlertType.NONE,
            userPOIs.size() - prevSize + " User POIs succesfully imported", ButtonType.OK);
        initUserPOITable();
      } catch (IOException e) {
        System.out.println("Error loading user POIs");
      }
    } else if (importType.getValue().equals("Route")) {
      try {
        ArrayList<Route> routesToAdd = reader
            .readRoutes(importFilePath, stations, true);
        prevSize = routes.size();
        for (Route route : routesToAdd) {
          routes.add(route);
        }
        alert = new Alert(AlertType.NONE, routes.size() - prevSize + " Routes succesfully imported",
            ButtonType.OK);
        initRouteTable();
      } catch (IOException e) {
        System.out.println("Error loading routes");
      }
    }
    if (alert != null) {
      alert.showAndWait();
    }
  }

}