package controller;

import static com.sun.org.apache.xalan.internal.utils.SecuritySupport.getResourceAsStream;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import window.Join;
import window.Login;
import window.Main;
import window.Warning;

public class GUIManager extends Application {

  // Attributes
  private static GUIManager instanceGUIManager;
  private Login loginWindow = new Login();
  private Join joinWindow = new Join();
  private Main mainWindow = new Main();
  private Warning warningWindow = new Warning();
  private Stage primaryStage;


  // Methods
  public static void main(String[] args) {

    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    instanceGUIManager = this; // set the static object GUIManager

    this.primaryStage = primaryStage;
    // primaryStage.getIcons().add(new Image("velo-icon.png")); // TODO add icon to app
    loginWindow.start(primaryStage);
  }

  public static GUIManager getInstanceGUIManager() {
    return instanceGUIManager;
  }


  // Other methods

  //LoginController methods
  /** The GUIManager method corresponding to the LoginController method authenticate
   * Lets GUIManager know the user was successfully authenticated
   * @throws Exception possible start exceptions
   */
  public void loginAuthenticated() throws Exception {

    // Close other window, begin Main window
    mainWindow.start(primaryStage);
  }

  /** The GUIManager method corresponding to the LoginControler method join
   * Lets GUIManager know the user wants to sign up
   * @throws Exception possible start exceptions
   */
  public void userJoin() throws Exception {

    // Close other window, launch Join window
    joinWindow.start(primaryStage);
  }

  /** The GUIManager method corresponding to the LoginControler method join
   * Lets GUIManager know the user wants to sign up
   * @throws Exception possible start exceptions
   */
  public void userJoinCancelled() throws Exception {

    // Close Join window, launch Login window
    loginWindow.start(primaryStage);
  }

  // JoinController methods
  /** The GUIManger method corresponding to the JoinController method createUser
   * Lets GUIManager know a user was created successfully via join window
   * @throws Exception possible start exceptions
   */
  public void userCreated() throws Exception {

    // Close any other window, begin Main window
    mainWindow.start(primaryStage);
  }

  // WarningController methods
  /** Creates the warning window with an error message
   *
   * @param errorMessage error message to display in the warning window
   * @throws Exception possible Stage and Start exceptions
   */
  public void error(String errorMessage) throws Exception {

    // Make a secondary stage (so it doesn't close the window that's already open)
    Stage secondaryStage = new Stage();

    // Create error window
    warningWindow.start(secondaryStage);

    // TODO set warning description, set visible... use the public method in WarningController?
  }


}
