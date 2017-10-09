package controller;

import com.sun.org.apache.regexp.internal.RE;
import filehandler.MySQL;
import filehandler.Reader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Analyst;
import model.Cyclist;
import model.Hotspot;
import model.PublicPOI;
import model.Retailer;
import model.Route;
import model.Station;
import model.UserPOI;
import window.Join;
import window.Login;
import window.Main;
import window.MainAnalyst;
import window.UpdateAccount;
import window.UserPOIForm;

public class GUIManager extends Application {

  // Attributes
  private static GUIManager instanceGUIManager;
  private String userType;
  private Cyclist cyclistAccount;
  private Analyst analystAccount;
  private Login loginWindow = new Login();
  private Join joinWindow = new Join();
  private Main mainWindow = new Main();
  private MainAnalyst mainAnalyst = new MainAnalyst();
  private UpdateAccount updateAccount = new UpdateAccount();
  private UserPOIForm userPOIFormWindow = new UserPOIForm();
  private Stage primaryStage;

  private ArrayList<Hotspot> hotspots = new ArrayList<>();
  private ArrayList<Retailer> retailers = new ArrayList<>();
  private ArrayList<UserPOI> userPOIs = new ArrayList<>();
  private ArrayList<PublicPOI> publicPOIs = new ArrayList<>();
  private ArrayList<Route> routes = new ArrayList<>();
  private ArrayList<Station> stations = new ArrayList<>();

  private ArrayList<Route> userRouteHistory = new ArrayList<>(); //EXISTING route history


  // Methods
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    instanceGUIManager = this; // set the static object GUIManager
    this.primaryStage = primaryStage;
    //primaryStage.setResizable(false); // TODO delete
    loginWindow.start(primaryStage);
  }

  static GUIManager getInstanceGUIManager() {
    return instanceGUIManager;
  }

  // Other methods

  /** Sets the int userType for the GUIManager
   * "cyclist"
   * "analyst"
   * @param newUserType the int that represents what type of user is logged in
   */
  private void setUserType(String newUserType) {
    userType = newUserType;
  }

  /** Gets the int userType from the GUImanager
   * "cyclist"
   * "analyst"
   * @return int userType is the int that represent what type of user is logged in
   */
  public String getUserType() {
    return userType;
  }

  /** Sets the GUIManager cyclist attribute
   * This allows the GUIManager to share the logged in cyclistAccount with other windows
   * @param newCyclistAccount the new cyclist account that's logged in for the app
   */
  public void setCyclistAccount(Cyclist newCyclistAccount) {
    setUserType("cyclist");
    analystAccount = null;
    cyclistAccount = newCyclistAccount;
  }

  /** Gets the GUIManager cyclist attribute
   * This allows other GUIs to retrieve the logged in cyclist account.
   * @return cyclistAccount - the cyclist that's logged in
   */
  public Cyclist getCyclistAccount() {
    return cyclistAccount;
  }

  /** Sets the GUIManager analyst attribute
   * This allows the GUIManager to share the logged in analystAccount with other windows
   * @param newAnalystAccount the new analyst account that's logged in for the app
   */
  public void setAnalystAccount(Analyst newAnalystAccount) {
    setUserType("analyst");
    cyclistAccount = null;
    analystAccount = newAnalystAccount;
  }

  /** Gets the GUIManager analyst attribute
   * This allows other GUIs to retrieve the logged in analyst account.
   * @return analystAccount - the analyst that's logged in
   */
  public Analyst getAnalystAccount() {
    return analystAccount;
  }

  //LoginController methods
  /** The GUIManager method corresponding to the LoginController method authenticate
   * Lets GUIManager know a cyclist user was successfully authenticated
   * @throws Exception possible start exceptions
   */
  void cyclistAuthenticated() throws Exception {

    // Close other window, begin Main window
    mainWindow.start(primaryStage);
  }

  /** The GUIManager method corresponding to the LoginController method authenticate
   * Lets GUIManager know a analyst user was successfully authenticated
   * @throws Exception possible start exceptions
   */
  void analystAuthenticated() throws Exception {

    // Close other window, begin mainAnalyst window
    mainAnalyst.start(primaryStage);
  }

  /** The GUIManager method corresponding to the LoginControler method join
   * Lets GUIManager know the user wants to sign up
   * @throws Exception possible start exceptions
   */
  void cyclistJoin() throws Exception {

    // Close other window, launch Join window
    joinWindow.start(primaryStage);
  }

  /** The GUIManager method corresponding to the LoginControler method join
   * Lets GUIManager know the user wants to sign up
   * @throws Exception possible start exceptions
   */
  void cyclistJoinCancelled() throws Exception {

    // Close Join window, launch Login window
    loginWindow.start(primaryStage);
  }

  // JoinController methods
  /** The GUIManger method corresponding to the JoinController method createUser
   * Lets GUIManager know a user was created successfully via join window
   * @throws Exception possible start exceptions
   */
  void cyclistCreated() throws Exception {

    // Close any other window, begin Main window
    mainWindow.start(primaryStage);
  }

  // AccountUpdate methods
  /** The GUIManger method corresponding to the UpdateAccountController
   * Lets GUIManager know a user wants to update their account
   * @throws Exception possible start exceptions
   */
  void updateAccount() throws Exception {

    // Close any other window, begin Main window
    updateAccount.start(primaryStage);
  }

  /** The GUIManger method corresponding to the UpdateAccountController
   * Lets GUIManager know a user was updated successfully via updateAccount window
   * @throws Exception possible start exceptions
   */
  void accountUpdated() throws Exception {

    // Close any other window, begin Main window
    mainWindow.start(primaryStage);
  }

  /** The GUIManger method corresponding to the UpdateAccountController
   * Lets GUIManager know a user cancelled updating via updateAccount window
   * @throws Exception possible start exceptions
   */
  void accountUpdateCancelled() throws Exception {

    // Close any other window, begin Main window
    mainWindow.start(primaryStage);
  }

  /** The GUIManger method corresponding to the UserPOIFormController method AddPlace
   * Lets GUIManager know a user wants to add a place via UserPOIForm window
   * @throws Exception possible start exceptions
   */
  void addPlace() throws Exception {

    // Close any other window, begin UserPOIForm
    userPOIFormWindow.start(primaryStage);
  }

  /** The GUIManger method corresponding to the UserPOIFormController method cancelAddPlace
   * Lets GUIManager know a user cancelled adding a place via UserPOIForm window
   * @throws Exception possible start exceptions
   */
  void cancelAddPlace() throws Exception {

    // Close any other window, being Main window
    mainWindow.start(primaryStage);
  }

  /** The GUIManager method corresponding to the LoginController method authenticate
   * Lets GUIManager know the user was successfully authenticated
   * @throws Exception possible start exceptions
   */
  void logOut() throws Exception {

    //Clear user logged data
    cyclistAccount = null;
    analystAccount = null;

    //Close any other window, begin Login window
    loginWindow.start(primaryStage);
  }

  /**
   * populates arrayLists used for temporary local storage
   *
   * @return true if populating was successful, otherwise false
   */
  public boolean populateArrayLists() {
    Reader rdr = new Reader();
    Alert alert = null;
    try {
      /* OLD FILE READING
      //hotspots = rdr.readHotspots("/file/InitialHotspots.csv", 0);
      //retailers = rdr.readRetailers("/file/InitialRetailers.csv");
      //stations = rdr.readStations("/file/stations.json");
      */
      MySQL mysql = new MySQL();
      Connection conn = mysql.getConnection();
      hotspots = mysql.getHotspots(conn);
      retailers = mysql.getRetailers(conn);
      stations = mysql.getStations(conn);

      userPOIs = rdr.readUserPOIS("/file/UserPOIdata_smallsample.csv", false);
      publicPOIs = rdr.readPublicPOIS("/file/PublicPOIdata_smallsample.csv", false);
      routes = rdr.readRoutes("/file/tripdata_smallsample.csv", stations, false);
      //TODO populate userRouteHistory
    } catch (IOException e) {
      alert = new Alert(AlertType.ERROR, "There was an error loading an inital data file", ButtonType.OK);
    } catch (ArrayIndexOutOfBoundsException e) {
      alert = new Alert(AlertType.ERROR, "There was an error with the format of an initial data file",
          ButtonType.OK);
    } catch(Exception e) {
      System.out.println(e);
    }
    if (alert != null) {
      alert.showAndWait();
    }
    return true;
  }

  public ArrayList<Retailer> getRetailers() {
    return retailers;
  }

  public ArrayList<Hotspot> getHotspots() {
    return hotspots;
  }

  public ArrayList<PublicPOI> getPublicPOIs() {
    return publicPOIs;
  }

  public ArrayList<Station> getStations() {
    return stations;
  }

  public ArrayList<UserPOI> getUserPOIs() {
    return userPOIs;
  }

  public ArrayList<Route> getRoutes() {
    return routes;
  }

  public ArrayList<Route> getUserRouteHistory() {
    ArrayList<Route> routeHistory = new ArrayList<>();
    for (Route route : getRoutes()) {
      if (route.travelledByContains(cyclistAccount.getUsername())) {
        routeHistory.add(route);
      }
    }
    return routeHistory;
  }

  public void addRetailers(ArrayList<Retailer> newRetailers) {
    retailers.addAll(newRetailers);
  }

  public void addHotspots(ArrayList<Hotspot> newHotspots) {
    hotspots.addAll(newHotspots);
  }

  public void addPublicPOIs(ArrayList<PublicPOI> newPublicPOIs) {
    publicPOIs.addAll(newPublicPOIs);
  }

  public void addUserPOIs(ArrayList<UserPOI> newUserPOIs) {
    userPOIs.addAll(newUserPOIs);
  }

  public void addRoutes(ArrayList<Route> newRoutes) {
    routes.addAll(newRoutes);
  }

  public void addUserRouteHistory(Route route) {
    route.addTravelledBy(GUIManager.getInstanceGUIManager().cyclistAccount.getUsername());
  }

  public void removeUserRouteHistory(Route route) {
    route.removeTravelledBy(GUIManager.getInstanceGUIManager().cyclistAccount.getUsername());
  }


}
