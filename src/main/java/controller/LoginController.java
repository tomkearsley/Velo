package controller;

import java.util.concurrent.TimeUnit;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController {

  // FXML Attributes
  @FXML private ImageView icon;
  @FXML private TextField username;
  @FXML private TextField password;
  @FXML private Button logIn;
  @FXML private Hyperlink join;
  @FXML private Label feedback;
  @FXML private ProgressBar progressBar;


  // TODO make enter press the log in button
//  // Methods
//  /** Initialize the window */
//  public void initialize() {
//    gridPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
//      @Override
//      public void handle(KeyEvent keyEvent) {
//        if (keyEvent.getCode() == KeyCode.ENTER)  {
//          authenticate();
//        }
//      }
//    });
//  }

  /** Checks credentials against database to authenticate user */
  public void authenticate() {

    String enteredUsername = username.getText();
    String enteredPassword = password.getText();

    // TODO get text, and check credentials

    // TODO If CYCLIST authenticated, tell GUIManager
    try {
      System.out.println("Cyclist authenticated");
      userLoggedIn(); // formats GUI
      GUIManager.getInstanceGUIManager().cyclistAuthenticated();
    } catch (Exception e) {
      e.printStackTrace();
    }
    // TODO if ANALYST user, tell GUIManager
//    try {
//      System.out.println("Analyst authenticated");
//      userLoggedIn(); // formats GUI
//      GUIManager.getInstanceGUIManager().analystAuthenticated();
//    } catch (Exception e) {
//      e.printStackTrace();
//    }

    // TODO else if credentials incorrect, call invalid credentials to display a error message
    // invalidCredentials();

  }

  /** Tells GUIManager to launch the Join window */
  public void join() {

    // If wanting to join, tell GUIManager
    try {
      System.out.println("User joining...");
      GUIManager.getInstanceGUIManager().cyclistJoin();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /** Displays a red warning message */
  private void invalidCredentials() {
    feedback.setText("Incorrect username or password");
    feedback.setVisible(true);
  }

  /** Hides the red warning label message */
  @FXML private void hideFeedbackLabel() {
    feedback.setVisible(false);
  }

  /** Prepares the login GUI for loading next screen by disabling gui elements */
  private void userLoggedIn() {
    progressBar.setVisible(true);
    username.setDisable(true);
    password.setDisable(true);
    logIn.setDisable(true);
    join.setDisable(true);
    feedback.setText("Logged in, loading...");
    feedback.setStyle("-fx-text-fill: green");
    feedback.setVisible(true);
  }

}
