package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import window.Login;
import window.Main;

public class GUIManager extends Application {

  private static GUIManager instanceGUIManager;
  private Login loginWindow = new Login();
  private Main mainWindow = new Main();
  private Stage primaryStage;

  // TODO make this run the login window

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    instanceGUIManager = this; // set the static object GUIManager

    this.primaryStage = primaryStage;
    loginWindow.start(primaryStage);
  }

  public static GUIManager getInstanceGUIManager() {
    return instanceGUIManager;
  }


  /** OTHER METHODS */

  public void loginAuthenticated() throws Exception {

    // Close other window, begin Main window
    mainWindow.start(primaryStage);
  }


  public void error(String errorMessage) {

    // Make a secondary stage (so it doesn't close the window that's already open

    // Create error window

  }



}
