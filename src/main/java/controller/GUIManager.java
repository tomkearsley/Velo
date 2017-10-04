package controller;

import javafx.application.Application;
import javafx.scene.control.Menu;
import javafx.stage.Stage;
import window.Join;
import window.Login;
import window.Main;

public class GUIManager extends Application {

  // Attributes
  private static GUIManager instanceGUIManager;
  private Login loginWindow = new Login();
  private Join joinWindow = new Join();
  private Main mainWindow = new Main();
  private Stage primaryStage;


  // Methods
  public static void main(String[] args) {

    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    instanceGUIManager = this; // set the static object GUIManager

    // TODO test this. Doesn't seem like it's working
    // Add macOS menu bar items
    //MenuToolkit tk = MenuToolkit.toolkit();
    //Menu defaultApplicationMenu = tk.createDefaultApplicationMenu("Vélo");

//    MenuBar bar = new MenuBar();
//    bar.getMenus().add(defaultApplicationMenu);
//    tk.setGlobalMenuBar(bar); // Use the menu bar for all stages including new ones

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
  public void cyclistAuthenticated() throws Exception {

    // Close other window, begin Main window
    mainWindow.start(primaryStage);
  }

  public void analystAuthenticated() throws Exception {

    // Close other window, begin mainAnalyst window

  }

  /** The GUIManager method corresponding to the LoginControler method join
   * Lets GUIManager know the user wants to sign up
   * @throws Exception possible start exceptions
   */
  public void cyclistJoin() throws Exception {

    // Close other window, launch Join window
    joinWindow.start(primaryStage);
  }

  /** The GUIManager method corresponding to the LoginControler method join
   * Lets GUIManager know the user wants to sign up
   * @throws Exception possible start exceptions
   */
  public void cyclistJoinCancelled() throws Exception {

    // Close Join window, launch Login window
    loginWindow.start(primaryStage);
  }

  // JoinController methods
  /** The GUIManger method corresponding to the JoinController method createUser
   * Lets GUIManager know a user was created successfully via join window
   * @throws Exception possible start exceptions
   */
  public void cyclistCreated() throws Exception {

    // Close any other window, begin Main window
    mainWindow.start(primaryStage);
  }

}
