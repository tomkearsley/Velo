package controller;

import filehandler.Reader;
import helper.Bridge;
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
import model.Hotspot;
import model.PublicPOI;
import model.Retailer;
import model.Route;
import model.Station;
import model.UserPOI;
import netscape.javascript.JSObject;


/**
 * Initialises all of the ArrayLists used for temporary storage and @FXML items
 */
public class MainController {



  private ArrayList<Hotspot> hotspots = new ArrayList<Hotspot>();
  private ArrayList<Retailer> retailers = new ArrayList<Retailer>();
  private ArrayList<UserPOI> userPOIs = new ArrayList<UserPOI>();
  private ArrayList<PublicPOI> publicPOIs = new ArrayList<PublicPOI>();
  private ArrayList<Route> routes = new ArrayList<Route>();
  private ArrayList<Station> stations = new ArrayList<Station>();
  //local variables for toggling detailed view in table view
  private boolean hotspotIsDetailed = false;
  private boolean retailerIsDetailed = false;
  //private boolean userPOIIsDetailed = false;
  //private boolean publicPOIIsDetailed = false;
  private boolean routeIsDetailed = false;
  //private boolean stationIsDetailed = false;

  private ArrayList<ImageView> buttons = new ArrayList<ImageView>();

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

  //filter fields. one for each tableview
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


  @FXML
  private TabPane dataTabPane;
  @FXML
  private AnchorPane mapViewPane;
  @FXML
  private WebView mapWebView;
  @FXML
  private Pane userPane;
  @FXML
  private Pane fileHandlerPane;

  @FXML private ImageView wifi_icon_primary;
  @FXML private ImageView retailer_icon_primary;
  @FXML private ImageView poi_icon_primary;
  @FXML private ImageView wifi_icon_secondary;
  @FXML private ImageView retailer_icon_secondary;
  @FXML private ImageView poi_icon_secondary;
  @FXML private ImageView station_icon_primary;
  @FXML private ImageView station_icon_secondary;

  //Other attributes
  private JSObject window;
  private Bridge aBridge = new Bridge();

  /**
   * populates arrayLists used for temporary local storage
   * @return true if populating was successful, otherwise false
   */
  public boolean populateArrayLists() {
    Reader rdr = new Reader();
    try {
      hotspots = rdr.readHotspots("/file/InitialHotspots.csv", 0);
      retailers = rdr.readRetailers("/file/InitialRetailers.csv");
      stations = rdr.readStations("/file/stations.json");
      userPOIs = rdr.readUserPOIS("/file/UserPOIdata_smallsample.csv");
      publicPOIs = rdr.readPublicPOIS("/file/PublicPOIdata_smallsample.csv");
      routes = rdr.readRoutes("/file/tripdata_smallsample.csv", stations);
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public void setImages() throws URISyntaxException{
    wifi_icon_primary.setImage(new Image(getClass().getResource("/image/hotspot-icon.png").toURI().toString()));
    retailer_icon_primary.setImage(new Image(getClass().getResource("/image/retailer-icon.png").toURI().toString()));
    poi_icon_primary.setImage(new Image(getClass().getResource("/image/marker-icon.png").toURI().toString()));
    station_icon_primary.setImage(new Image(getClass().getResource("/image/station-icon.png").toURI().toString()));

    wifi_icon_secondary.setImage(new Image(getClass().getResource("/image/hotspot-pressed-icon.png").toURI().toString()));
    retailer_icon_secondary.setImage(new Image(getClass().getResource("/image/retailer-pressed-icon.png").toURI().toString()));
    poi_icon_secondary.setImage(new Image(getClass().getResource("/image/marker-pressed-icon.png").toURI().toString()));
    station_icon_secondary.setImage(new Image(getClass().getResource("/image/station-pressed-icon.png").toURI().toString()));

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

  public void toggleButton(int buttonNo) {
    System.out.println("Toggling button" + (buttonNo + 1));
    buttons.get(buttonNo).setVisible(false);
    int halfButtonSize = buttons.size()/2;
    //Check if button is primary or on-click
    if(buttonNo >= halfButtonSize) {
      buttons.get(buttonNo - halfButtonSize).setVisible(true);
    }
    else {
      buttons.get(buttonNo + halfButtonSize).setVisible(true);
    }
  }

  public void toggleButton1() {
    toggleButton(0);
  }

  public void toggleButton2() {
    try {
      displayHotspots();
    }
    catch (IOException e) {
      System.out.println("Error occurred while reading hotspots");
    }
    toggleButton(1);
  }

  public void toggleButton3() {
    toggleButton(2);
  }

  public void toggleButton4() {
    toggleButton(3);
  }

  public void toggleButton5() {
    toggleButton(4);
  }

  public void toggleButton6() {
    try {
      hideMarkers();
    }
    catch (Exception e) {
      System.out.println("Internal error, please report to app devs");
    }
    toggleButton(5);
  }

  public void toggleButton7() {
    toggleButton(6);
  }

  public void toggleButton8() {
    toggleButton(7);
  }

  /**
   * Runs at startup Populates the model structure with data from .csv files using
   * populateArrayLists() TODO adapt to using database primarily with csv as fallback
   */
  public void initialize() throws URISyntaxException{
    boolean arraylists_populated = populateArrayLists();

    if (!arraylists_populated) {
      //TODO bring up warning window when that is implemented
    }

    //create tables
    initRetailerTable();
    initHotspotTable();
    initPublicPOITable();
    initUserPOITable();
    initStationTable();
    initRouteTable();
    //Ostrich - Is this meant to be me? - Imas




    URL url = getClass().getResource("/googleMaps.html");

    WebEngine mapEngine = mapWebView.getEngine();
    mapEngine.setJavaScriptEnabled(true);

    setImages();

    mapEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
        if (newState == State.SUCCEEDED) {
          window = (JSObject) mapEngine.executeScript("window");
          window.setMember("aBridge", aBridge);
          System.out.println("Initialisation complete"); // Maybe don't let them switch to map view until this is initialised or just default to table view to give time for this to load.
        }
      });

    //double latitude = 40.785091;double longitude = -73.968285;String title = "Test Marker";String markerType = "default";

    mapEngine.load(url.toExternalForm());
    mapEngine.setJavaScriptEnabled(true);

    //testABC();
    //mapEngine.executeScript("test()");
  }

  /**
   * helper function for filter boxes. If the input string s is an integer,
   * returns true. Otherwise, false
   * @param s
   * @return
   */
  private boolean isInteger(String s) {
    try {
      Integer i = Integer.parseInt(s);
    }
    catch(NumberFormatException nfe) {
      return false;
    }
    return true;
  }

  public void displayHotspots() throws IOException{
    Reader rdr = new Reader();
    //Run both lines of code
    window.setMember("aBridge",aBridge);
    window.call("loadHotspots",rdr.readHotspots("/file/InitialHotspots.csv", 0));
    testPretty();
  }

  public void hideMarkers() {
    window.setMember("aBridge",aBridge);
    window.call("hideMarkers");
  }

  public void showMarkers() {
    window.setMember("aBridge",aBridge);
    window.call("showMarkers");
  }

  public void deleteMarkers() {
    window.setMember("aBridge",aBridge);
    window.call("deleteMarkers");
  }

  public void prettyMarker(double lat,double lng,String info,String markerType) {
    window.setMember("aBridge",aBridge);
    window.call("prettyMarker",lat,lng,info,markerType);
  }

  public void testPretty() {
    prettyMarker(40.714728,-73.998672,"Test string","wifi");
  }
  /*
  Action handlers
   */
  public void viewData() {
    dataTabPane.toFront();
  }

  public void viewMap() {
    mapViewPane.toFront();
  }

  public void viewUser() {
    userPane.toFront();
  }

  public void viewFileHandler() {
    fileHandlerPane.toFront();
  }

  public void toggleDetailsHotspot() {
    //simple view: id, locAddress, borough, city, type, provider, remarks
    //advanced view: include above and lat, long, postcode, SSID
    if(hotspotIsDetailed) {
      //it is detailed, so remove columns
      dataTableHotspot.getColumns().remove(7, 11);
    } else {
      TableColumn<Hotspot, Double> latCol = new TableColumn<Hotspot, Double>("Latitude");
      latCol.setCellValueFactory(new PropertyValueFactory<Hotspot, Double>("latitude"));
      TableColumn<Hotspot, Double> longCol = new TableColumn<Hotspot, Double>("Longitude");
      longCol.setCellValueFactory(new PropertyValueFactory<Hotspot, Double>("longitude"));
      TableColumn<Hotspot, String> postCodeCol = new TableColumn<Hotspot, String>("postcode");
      postCodeCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("postcode"));
      TableColumn<Hotspot, String> SSIDCol = new TableColumn<Hotspot, String>("SSID");
      SSIDCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("SSID"));
      dataTableHotspot.getColumns().addAll(latCol, longCol, postCodeCol, SSIDCol);
    }
    hotspotIsDetailed = !hotspotIsDetailed;
  }

  public void toggleDetailsRetailer() {
    //simple:
    //detailed:
    if(retailerIsDetailed) {
      dataTableRetailer.getColumns().remove(3, 8);
    } else {
      TableColumn<Retailer, String> floorCol = new TableColumn<Retailer, String>("Floor");
      floorCol.setCellValueFactory(new PropertyValueFactory<Retailer, String>("floor"));
      TableColumn<Retailer, String> cityCol = new TableColumn<Retailer, String>("City");
      cityCol.setCellValueFactory(new PropertyValueFactory<Retailer, String>("city"));
      TableColumn<Retailer, String> zipcodeCol = new TableColumn<Retailer, String>("Zipcode");
      zipcodeCol.setCellValueFactory(new PropertyValueFactory<Retailer, String>("zipcode"));
      TableColumn<Retailer, String> stateCol = new TableColumn<Retailer, String>("State");
      stateCol.setCellValueFactory(new PropertyValueFactory<Retailer, String>("state"));
      TableColumn<Retailer, String> blockCol = new TableColumn<Retailer, String>("Block");
      blockCol.setCellValueFactory(new PropertyValueFactory<Retailer, String>("block"));
      dataTableRetailer.getColumns().addAll(floorCol, cityCol, zipcodeCol, stateCol, blockCol);
    }

    retailerIsDetailed = !retailerIsDetailed;
  }

  public void toggleDetailsRoute() {
    if(routeIsDetailed) {
      dataTableRoute.getColumns().remove(4, 8);
    }
    else {
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
   * converts the arrayList of retailers to an observableList creates columns and sets these columns
   * and values to be displayed in rawDataTable
   */
  public void initRetailerTable() {
    //converting the arraylist to an observable list
    ObservableList<Retailer> oListRetailers = FXCollections.observableArrayList(retailers);
    //each 2 line section creates one table heading and set of values
    //TODO lat and long from address?
    TableColumn<Retailer, String> nameCol = new TableColumn<Retailer, String>(
        "Name");//title to be written above column
    nameCol.setCellValueFactory(
        new PropertyValueFactory<Retailer, String>("name"));//looks for retailer.getName()
    TableColumn<Retailer, String> addressCol = new TableColumn<Retailer, String>("Address");
    addressCol.setCellValueFactory(new PropertyValueFactory<Retailer, String>("address"));

    TableColumn<Retailer, String> secondaryDescCol = new TableColumn<Retailer, String>(
        "Secondary Description");
    secondaryDescCol
        .setCellValueFactory(new PropertyValueFactory<Retailer, String>("secondaryDescription"));


    FilteredList<Retailer> fListRetailers = new FilteredList<Retailer>(oListRetailers);
    /**
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
        /**
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
    /**
     * Sorting:
     * wrapping the filtered list in a sorted list allows the user to click on the title
     * of a column and sort the entries in alphanumeric order
     */
    SortedList<Retailer> sListRetailers = new SortedList<Retailer>(fListRetailers);
    sListRetailers.comparatorProperty().bind(dataTableRetailer.comparatorProperty());
//simple: name, address, description
    //detailed: above and floor, city, zipcode, state, block
    //sets up simple view
    dataTableRetailer.getColumns()
        .setAll(nameCol, addressCol, secondaryDescCol);
    dataTableRetailer.setItems(sListRetailers);

    /**
     * on click behaviour
     */
    dataTableRetailer.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if(event.isPrimaryButtonDown() && event.getClickCount() == 2) {
          helper.tableOnClickPopup.create("Retailer", "you've clicked a thing!", dataTableRetailer.getSelectionModel().getSelectedItem());
        }

      }
    });
  }

  /**
   * converts the arrayList of hotspots to an ObservableList and creates TableColumns sets these as
   * viewable in rawDataTable
   */
  public void initHotspotTable() {
    ObservableList<Hotspot> oListHotspots = FXCollections.observableArrayList(hotspots);

    TableColumn<Hotspot, String> idCol = new TableColumn<Hotspot, String>("Name");
    idCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("name"));


    TableColumn<Hotspot, String> locAddressCol = new TableColumn<Hotspot, String>("Address");
    locAddressCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("locationAddress"));
    TableColumn<Hotspot, String> boroughCol = new TableColumn<Hotspot, String>("Borough");
    boroughCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("borough"));
    TableColumn<Hotspot, String> cityCol = new TableColumn<Hotspot, String>("City");
    cityCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("city"));

    TableColumn<Hotspot, String> typeCol = new TableColumn<Hotspot, String>("Type");
    typeCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("type"));

    TableColumn<Hotspot, String> nameCol = new TableColumn<Hotspot, String>("Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("name"));
    TableColumn<Hotspot, String> providerCol = new TableColumn<Hotspot, String>("Provider");
    providerCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("provider"));
    TableColumn<Hotspot, String> remarksCol = new TableColumn<Hotspot, String>("Remarks");
    remarksCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("description"));

    FilteredList<Hotspot> fListHotspots = new FilteredList<Hotspot>(oListHotspots);
    HotspotFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
      fListHotspots.setPredicate(Hotspot -> {
        //if filter is empty, show all
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();
        // Add more Hotspot.get__'s below to include more things in the search

        if (Hotspot.getBorough().toLowerCase().contains(lowerCaseFilter) || Hotspot.getType()
            .toLowerCase().contains(lowerCaseFilter)|| Hotspot.getProvider()
            .toLowerCase().contains(lowerCaseFilter)) {
          return true;
        }
        return false;
      });
    });

    SortedList<Hotspot> sListHotspots = new SortedList<Hotspot>(fListHotspots);
    sListHotspots.comparatorProperty().bind(dataTableHotspot.comparatorProperty());

    //sets up simple view.
    dataTableHotspot.getColumns()
        .setAll(idCol, locAddressCol, boroughCol, cityCol, typeCol, providerCol, remarksCol);

    dataTableHotspot.setItems(sListHotspots);
  }

  public void initPublicPOITable() {
    //lat, long, name, description
    ObservableList<PublicPOI> oListPublicPOIs = FXCollections.observableArrayList(publicPOIs);
    TableColumn<PublicPOI, Double> latCol = new TableColumn<PublicPOI, Double>("Latitude");
    latCol.setCellValueFactory(new PropertyValueFactory<PublicPOI, Double>("latitude"));
    TableColumn<PublicPOI, Double> longCol = new TableColumn<PublicPOI, Double>("Longitude");
    longCol.setCellValueFactory(new PropertyValueFactory<PublicPOI, Double>("longitude"));
    TableColumn<PublicPOI, String> nameCol = new TableColumn<PublicPOI, String>("Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<PublicPOI, String>("name"));
    TableColumn<PublicPOI, String> descriptionCol = new TableColumn<PublicPOI, String>(
        "Description");
    descriptionCol.setCellValueFactory(new PropertyValueFactory<PublicPOI, String>("description"));

    FilteredList<PublicPOI> fListPublicPOIs = new FilteredList<PublicPOI>(oListPublicPOIs);
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

    SortedList<PublicPOI> sListPublicPOIs = new SortedList<PublicPOI>(fListPublicPOIs);
    sListPublicPOIs.comparatorProperty().bind(dataTablePublicPOI.comparatorProperty());

    dataTablePublicPOI.getColumns().setAll(nameCol, latCol, longCol, descriptionCol);
    dataTablePublicPOI.setItems(sListPublicPOIs);
  }

  public void initUserPOITable() {
    //lat, long, name, description
    ObservableList<UserPOI> oListUserPOIs = FXCollections.observableArrayList(userPOIs);

    TableColumn<UserPOI, Double> latCol = new TableColumn<UserPOI, Double>("Latitude");
    latCol.setCellValueFactory(new PropertyValueFactory<UserPOI, Double>("latitude"));
    TableColumn<UserPOI, Double> longCol = new TableColumn<UserPOI, Double>("Longitude");
    longCol.setCellValueFactory(new PropertyValueFactory<UserPOI, Double>("longitude"));
    TableColumn<UserPOI, String> nameCol = new TableColumn<UserPOI, String>("Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<UserPOI, String>("name"));
    TableColumn<UserPOI, String> descriptionCol = new TableColumn<UserPOI, String>("Description");
    descriptionCol.setCellValueFactory(new PropertyValueFactory<UserPOI, String>("description"));

    FilteredList<UserPOI> fListUserPOIs = new FilteredList<UserPOI>(oListUserPOIs);
    UserPOIFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
      fListUserPOIs.setPredicate(UserPOI -> {
        //if filter is empty, show all
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();
        /**
         * Add more UserPOI.get__'s below to include more things in the search
         */
        if (UserPOI.getName().toLowerCase().contains(lowerCaseFilter)) {
          return true;
        }
        return false;
      });
    });

    SortedList<UserPOI> sListUserPOIs = new SortedList<UserPOI>(fListUserPOIs);
    sListUserPOIs.comparatorProperty().bind(dataTableUserPOI.comparatorProperty());

    dataTableUserPOI.getColumns().setAll(nameCol, latCol, longCol, descriptionCol);
    dataTableUserPOI.setItems(sListUserPOIs);
  }

  public void initStationTable() {
    //latitude, longitude, name, ID
    ObservableList<Station> oListStations = FXCollections.observableArrayList(stations);

    TableColumn<Station, Double> latCol = new TableColumn<Station, Double>("Latitude");
    latCol.setCellValueFactory(new PropertyValueFactory<Station, Double>("latitude"));
    TableColumn<Station, Double> longCol = new TableColumn<Station, Double>("Longitude");
    longCol.setCellValueFactory(new PropertyValueFactory<Station, Double>("longitude"));
    TableColumn<Station, String> nameCol = new TableColumn<Station, String>("Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<Station, String>("name"));
    TableColumn<Station, Integer> idCol = new TableColumn<Station, Integer>("ID");
    idCol.setCellValueFactory(new PropertyValueFactory<Station, Integer>("ID"));

    FilteredList<Station> fListStations = new FilteredList<Station>(oListStations);
    StationFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
      fListStations.setPredicate(Station -> {
        //if filter is empty, show all
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();
        /**
         * Add more Station.get__'s below to include more things in the search
         */
        if (Station.getName().toLowerCase().contains(lowerCaseFilter)) {
          return true;
        }
        return false;
      });
    });

    SortedList<Station> sListStations = new SortedList<Station>(fListStations);
    sListStations.comparatorProperty().bind(dataTableStation.comparatorProperty());

    dataTableStation.getColumns().setAll(nameCol, latCol, longCol, idCol);
    dataTableStation.setItems(sListStations);
  }

  public void initRouteTable() {
    //startStation, stopStation, startDateTime, endDateTime, bikeID, userType, birthYear, gender
    ObservableList<Route> oListRoutes = FXCollections.observableArrayList(routes);

    TableColumn<Route, Station> startStationCol = new TableColumn<Route, Station>("Start Station");
    startStationCol
        .setCellValueFactory(new PropertyValueFactory<Route, Station>("startStationName"));
    TableColumn<Route, Station> stopStationCol = new TableColumn<Route, Station>("Stop Station");
    stopStationCol.setCellValueFactory(new PropertyValueFactory<Route, Station>("stopStationName"));
    TableColumn<Route, Date> startDateTimeCol = new TableColumn<Route, Date>("Start Time");
    startDateTimeCol.setCellValueFactory(new PropertyValueFactory<Route, Date>("startDate"));
    TableColumn<Route, Date> endDateTimeCol = new TableColumn<Route, Date>("Stop Time");
    endDateTimeCol.setCellValueFactory(new PropertyValueFactory<Route, Date>("stopDate"));

    FilteredList<Route> fListRoutes = new FilteredList<Route>(oListRoutes);

    RouteFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
      fListRoutes.setPredicate(Route -> {
        //if filter is empty, show all
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();
        /**
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

    SortedList<Route> sListRoutes = new SortedList<Route>(fListRoutes);
    sListRoutes.comparatorProperty().bind(dataTableRoute.comparatorProperty());
    //simple: start and end stations, start and end times
    //TODO finish simple/advanced views for routes
    //TODO calculate duration of a trip and put it in a column..?
    //sets up simple view
    dataTableRoute.getColumns()
        .setAll(startStationCol, stopStationCol, startDateTimeCol, endDateTimeCol);
    dataTableRoute.setItems(sListRoutes);
  }
}