package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Analyst;
import model.Cyclist;
import window.Join;
import window.Login;
import window.Main;
import window.MainAnalyst;
import window.UpdateAccount;

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
  private Stage primaryStage;


  // Methods
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    instanceGUIManager = this; // set the static object GUIManager
    this.primaryStage = primaryStage;
    primaryStage.setResizable(false); // TODO delete
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

  /** The GUIManager method corresponding to the LoginController method authenticate
   * Lets GUIManager know the user was successfully authenticated
   * @throws Exception possible start exceptions
   */
  void logOut() throws Exception {

    //Close any other window, begin Login window
    loginWindow.start(primaryStage);
  }

}
