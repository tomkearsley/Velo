package controller;

import filehandler.Reader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import model.Hotspot;
import model.PublicPOI;
import model.Retailer;
import model.Route;
import model.Station;
import model.UserPOI;

/**
 * Initialises all of the ArrayLists used for temporary storage and @FXML items
 */
public class MainController {

  ArrayList<Hotspot> hotspots = new ArrayList<Hotspot>();
  ArrayList<Retailer> retailers = new ArrayList<Retailer>();
  ArrayList<UserPOI> userPOIs = new ArrayList<UserPOI>();
  ArrayList<PublicPOI> publicPOIs = new ArrayList<PublicPOI>();
  ArrayList<Route> routes = new ArrayList<Route>();
  ArrayList<Station> stations = new ArrayList<Station>();
  //TODO use reader to populate these ArrayLists

  //Data table
  @FXML
  private TableView rawDataTable;
  @FXML
  private SplitPane dataSplitPane;
  @FXML
  private Pane mapViewPane;
  @FXML
  private ChoiceBox<DataType> dataTypeChoiceBox;

  @FXML
  private TextField rawDataFilterField;

  @FXML
  private WebView mapWebView;



  public boolean populateArrayLists() {
    Reader rdr = new Reader();
    try{
      hotspots = rdr.readHotspots("src/main/resources/file/InitialHotspots");
      retailers = rdr.readRetailers("src/main/resources/file/InitialRetailers");

    }
    catch(FileNotFoundException e){
      System.out.println("File not found");
      e.printStackTrace();
      //TODO bring up warning window when that is implemented
    }
    return true;
  }


  /**
   * Runs at startup
   * Populates the model structure with data from .csv files using populateArrayLists()
   * TODO adapt to using database primarily with csv as fallback
   */
  public void initialize(){
    populateArrayLists();
    //dataViewRetailers(); /* some initial data so the table isn't empty on startup */
    dataTypeChoiceBox.getItems().setAll(DataType.values());

    File f = new File("src/main/resources/googleMaps.html");
    mapWebView.getEngine().load(f.toURI().toString());
  }

  /*
  Action handlers
   */
  public void viewData() {
    dataSplitPane.toFront();
  }
  public void viewMap() {
    mapViewPane.toFront();
  }

  /**
   * converts the arrayList of retailers to an observableList
   * creates columns and sets these columns and values to be displayed in rawDataTable
   */
  public void dataViewRetailers() {
    //converting the arraylist to an observable list
    ObservableList<Retailer>  oListRetailers = FXCollections.observableArrayList(retailers);
    //TODO add filteredLists to other dataView__ methods, probably abstract these up a layer
    //each 2 line section creates one table heading and set of values
    //TODO lat and long from address?
    TableColumn<Retailer, String> nameCol = new TableColumn<Retailer, String>("Name");//title to be written above column
    nameCol.setCellValueFactory(new PropertyValueFactory<Retailer, String>("name"));//looks for retailer.getName()
    TableColumn<Retailer, String> addressCol = new TableColumn<Retailer, String>("Address");
    addressCol.setCellValueFactory(new PropertyValueFactory<Retailer, String>("address"));
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
    TableColumn<Retailer, String> secondaryDescCol = new TableColumn<Retailer, String>("Secondary Description");
    secondaryDescCol.setCellValueFactory(new PropertyValueFactory<Retailer, String>("secondaryDescription"));

    FilteredList<Retailer> fListRetailers = new FilteredList<Retailer>(oListRetailers);
    /**
     * Filtering:
     * if this returns true, the object is shown. If the filter field is empty,
     * or the attributes below match, then the object is shown
     */
    rawDataFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
      fListRetailers.setPredicate(Retailer -> {
        //if filter is empty, show all
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();
        /**
         * Add more Retailer.get__'s below to include more things in the search
         */
        if (Retailer.getAddress().toLowerCase().contains(lowerCaseFilter) || Retailer.getName().toLowerCase().contains(lowerCaseFilter)) {
          return true;
        }
        return false;
        });
      });

    rawDataTable.getColumns().setAll(nameCol, addressCol, floorCol, cityCol, zipcodeCol, stateCol, blockCol, secondaryDescCol);
    rawDataTable.setItems(fListRetailers);
  }

  /**
   * converts the arrayList of hotspots to an ObservableList and creates TableColumns
   * sets these as viewable in rawDataTable
   */
  public void dataViewHotspots() {
    ObservableList<Hotspot> oListHotspots = FXCollections.observableArrayList(hotspots);

    TableColumn<Hotspot, String> idCol = new TableColumn<Hotspot, String>("Name");
    idCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("name"));
    TableColumn<Hotspot, Double> latCol = new TableColumn<Hotspot, Double>("Latitude");
    latCol.setCellValueFactory(new PropertyValueFactory<Hotspot, Double>("latitude"));
    TableColumn<Hotspot, Double> longCol = new TableColumn<Hotspot, Double>("Longitude");
    longCol.setCellValueFactory(new PropertyValueFactory<Hotspot, Double>("longitude"));
    TableColumn<Hotspot, String> locAddressCol = new TableColumn<Hotspot, String>("Address");
    locAddressCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("locationAddress"));
    TableColumn<Hotspot, String> boroughCol = new TableColumn<Hotspot, String>("Borough");
    boroughCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("borough"));
    TableColumn<Hotspot, String> cityCol = new TableColumn<Hotspot, String>("City");
    cityCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("city"));
    TableColumn<Hotspot, String> postCodeCol = new TableColumn<Hotspot, String>("postcode");
    postCodeCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("postcode"));
    TableColumn<Hotspot, String> typeCol = new TableColumn<Hotspot, String>("Type");
    typeCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("type"));
    TableColumn<Hotspot, String> SSIDCol = new TableColumn<Hotspot, String>("SSID");
    SSIDCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("SSID"));
    TableColumn<Hotspot, String> nameCol = new TableColumn<Hotspot, String>("Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("name"));
    TableColumn<Hotspot, String> providerCol = new TableColumn<Hotspot, String>("Provider");
    providerCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("provider"));
    TableColumn<Hotspot, String> remarksCol = new TableColumn<Hotspot, String>("Remarks");
    remarksCol.setCellValueFactory(new PropertyValueFactory<Hotspot, String>("description"));

    FilteredList<Hotspot> fListHotspots = new FilteredList<Hotspot>(oListHotspots);
    //TODO ask tutor why Hotspot.get__ is not usable
    rawDataFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
      fListHotspots.setPredicate(Hotspot -> {
        //if filter is empty, show all
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();
         // Add more Hotspot.get__'s below to include more things in the search

        if (Hotspot.getName().toLowerCase().contains(lowerCaseFilter)) {
          return true;
        }
        return false;
      });
    });
    rawDataTable.getColumns().setAll(idCol, latCol, longCol, locAddressCol, boroughCol, cityCol, postCodeCol, typeCol, SSIDCol, providerCol, remarksCol); //something something
    rawDataTable.setItems(fListHotspots);
  }
  public void dataViewPublicPOIs() {
    //lat, long, name, description
    ObservableList<PublicPOI> oListPublicPOIs = FXCollections.observableArrayList(publicPOIs);
    TableColumn<PublicPOI, Double> latCol = new TableColumn<PublicPOI, Double>("Latitude");
    latCol.setCellValueFactory(new PropertyValueFactory<PublicPOI, Double>("latitude"));
    TableColumn<PublicPOI, Double> longCol = new TableColumn<PublicPOI, Double>("Longitude");
    longCol.setCellValueFactory(new PropertyValueFactory<PublicPOI, Double>("longitude"));
    TableColumn<PublicPOI, String>  nameCol = new TableColumn<PublicPOI, String>("Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<PublicPOI, String>("name"));
    TableColumn<PublicPOI, String> descriptionCol = new TableColumn<PublicPOI, String>("Description");
    descriptionCol.setCellValueFactory(new PropertyValueFactory<PublicPOI, String>("description"));

    FilteredList<PublicPOI> fListPublicPOIs = new FilteredList<PublicPOI>(oListPublicPOIs);
    rawDataFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
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

    rawDataTable.getColumns().setAll(nameCol, latCol, longCol, descriptionCol);
    rawDataTable.setItems(fListPublicPOIs);
  }
  //TODO fill out stubs
  public void dataViewUserPOIs() {
    //lat, long, name, description
    ObservableList<UserPOI> oListUserPOIs = FXCollections.observableArrayList(userPOIs);

    TableColumn<UserPOI, Double> latCol = new TableColumn<UserPOI, Double>("Latitude");
    latCol.setCellValueFactory(new PropertyValueFactory<UserPOI, Double>("latitude"));
    TableColumn<UserPOI, Double> longCol = new TableColumn<UserPOI, Double>("Longitude");
    longCol.setCellValueFactory(new PropertyValueFactory<UserPOI, Double>("longitude"));
    TableColumn<UserPOI, String>  nameCol = new TableColumn<UserPOI, String>("Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<UserPOI, String>("name"));
    TableColumn<UserPOI, String> descriptionCol = new TableColumn<UserPOI, String>("Description");
    descriptionCol.setCellValueFactory(new PropertyValueFactory<UserPOI, String>("description"));

    FilteredList<UserPOI> fListUserPOIs = new FilteredList<UserPOI>(oListUserPOIs);
    rawDataFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
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
    rawDataTable.getColumns().setAll(nameCol, latCol, longCol, descriptionCol);
    rawDataTable.setItems(oListUserPOIs);
  }

  public void dataViewStations() {
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
    rawDataFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
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
    rawDataTable.getColumns().setAll(nameCol, latCol, longCol, idCol);
    rawDataTable.setItems(oListStations);
  }

  public void dataViewRoutes() {
    //startStation, stopStation, startDateTime, endDateTime, bikeID, userType, birthYear, gender
    //in-between points??
    ObservableList<Route> oListRoutes = FXCollections.observableArrayList(routes);

    TableColumn<Route, Station> startStationCol = new TableColumn<Route, Station>("Start Station");
    startStationCol.setCellValueFactory(new PropertyValueFactory<Route, Station>("startStation"));
    TableColumn<Route, Station> stopStationCol = new TableColumn<Route, Station>("Stop Station");
    stopStationCol.setCellValueFactory(new PropertyValueFactory<Route, Station>("stopStation"));
    TableColumn<Route, Date> startDateTimeCol = new TableColumn<Route, Date>("Start Time");
    startDateTimeCol.setCellValueFactory(new PropertyValueFactory<Route, Date>("startDateTime"));
    TableColumn<Route, Date> endDateTimeCol = new TableColumn<Route, Date>("Stop Time");
    endDateTimeCol.setCellValueFactory(new PropertyValueFactory<Route, Date>("endDateTime"));
    TableColumn<Route, Integer> bikeIDCol= new TableColumn<Route, Integer>("Bike ID");
    bikeIDCol.setCellValueFactory(new PropertyValueFactory<Route, Integer>("bikeID"));
    TableColumn<Route, String> userTypeCol = new TableColumn<Route, String>("User Type");
    userTypeCol.setCellValueFactory(new PropertyValueFactory<Route, String>("userType"));
    TableColumn<Route, Integer> birthYearCol = new TableColumn<Route, Integer>("Birth Year");
    birthYearCol.setCellValueFactory(new PropertyValueFactory<Route, Integer>("birthYear"));
    TableColumn<Route, Integer> genderCol = new TableColumn<Route, Integer>("Gender");
    genderCol.setCellValueFactory(new PropertyValueFactory<Route, Integer>("gender"));


    FilteredList<Route> fListRoutes = new FilteredList<Route>(oListRoutes);

    rawDataFilterField.textProperty().addListener((observable, oldValue, newValue) -> {
      fListRoutes.setPredicate(Route -> {
        //if filter is empty, show all
        if (newValue == null || newValue.isEmpty()) {
          return true;
        }

        String lowerCaseFilter = newValue.toLowerCase();
        /**
         * Add more Route.get__'s below to include more things in the search
         */
        if (Route.getStartStation().getName().toLowerCase().contains(lowerCaseFilter)) {
          return true;
        }
        return false;
      });
    });
    rawDataTable.getColumns().setAll(startStationCol, stopStationCol, startDateTimeCol, endDateTimeCol, bikeIDCol, userTypeCol, birthYearCol, genderCol);
    rawDataTable.setItems(oListRoutes);
  }
  public void dataViewSelected() {
    DataType selected = dataTypeChoiceBox.getValue();
    switch (selected) {
      case HOTSPOT:
        dataViewHotspots();
        break;
      case RETAILER:
        dataViewRetailers();
        break;
      case PUBLICPOI:
        dataViewPublicPOIs();
        break;
      case USERPOI:
        dataViewUserPOIs();
        break;
      case STATION:
        dataViewStations();
        break;
      case ROUTE:
        dataViewRoutes();
        break;
      }
    }

  /**
   * Fetches a specified attribute of the retailer instance. Used for filtering of retailers
   * using one function that can control the filter field through this parameter
   * @param retailer The retailer instance to fetch attributes from
   * @param field The field char to be filtered ('n' for name, 'a' for address, etc...)
   * @return The string value of that attribute
   */
  public String getRetailerField(Retailer retailer, char field) {
    switch(field) {
      case 'n':
        return retailer.getName();
      case 'a':
        return retailer.getAddress();
      case 'f':
        return retailer.getFloor();
      case 'c':
        return retailer.getCity();
      case 's':
        return retailer.getState();
      case 'z':
        return Integer.toString(retailer.getZipcode());
      case 'b':
        return retailer.getBlock();
      default:
        return "Invalid field name";
    }
  }

  /**
   * Filters the list of retailers to match a certain term in a given field
   * @param matchList The list of currently matched retailers (empty for filtering original list,
   *                  populated for adding additional filtering (ie filtering by name, then state)
   * @param field The field to be filtered on, standard is first character of field name ('n' for name)
   * @param term  The field to search against. If the attribute is a string, searches for attribute containing
   *              the term. If integer, searches for direct matches
   * @return  The filtered list of retailers
   */
  public ArrayList<Retailer> filterRetailers(ArrayList<Retailer> matchList, char field, String term) {
    boolean firstRun = matchList.size() == 0;
    if (firstRun) {
      for (Retailer retailer : retailers) {
        if (field == 'z') {
          if (getRetailerField(retailer, field).equals(term)) {
            matchList.add(retailer);
          }
        } else if (getRetailerField(retailer, field).contains(term)) {
          matchList.add(retailer);
        }
      }
    } else {
      for (Retailer retailer : matchList) {
        if (field == 'z') {
          if (!getRetailerField(retailer, field).equals(term)) {
            matchList.remove(retailer);
          }
        } else if (!getRetailerField(retailer, field).contains(term)) {
          matchList.remove(retailer);
        }
      }
    }
    return matchList;
  }

}