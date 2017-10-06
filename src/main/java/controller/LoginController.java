package controller;

import filehandler.MySQL;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
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
import sun.rmi.runtime.Log;

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
  // Methods
  /** Initialize the window */
  public void initialize() {
    EventHandler<KeyEvent> listener = (keyEvent) -> {
      if (keyEvent.getCode() == KeyCode.ENTER)  {
        authenticate();
      }
    };
    username.setOnKeyPressed(listener);
    password.setOnKeyPressed(listener);
    logIn.setOnKeyPressed(listener);
  }

  /** Checks credentials against database to authenticate user */
  public void authenticate() {

    userLoggingIn();

    Platform.runLater(()-> {

      String enteredUsername = username.getText();
      String enteredPassword = password.getText();

      // Check credentials
      MySQL mysql = new MySQL();
      ArrayList<Boolean> LoginResult = new ArrayList<Boolean>();
      try {
          LoginResult = mysql.login(enteredUsername, enteredPassword);
      }
      catch (Exception e){
        System.out.println(e);
      }
      Boolean isCyclist = LoginResult.get(0);
      Boolean successfulLogin = LoginResult.get(1);

      // If CYCLIST authenticated, tell GUIManager
      if (successfulLogin && isCyclist) {
        System.out.println("Cyclist authenticated");
        userLoggedIn(); // formats GUI
        Platform.runLater(()-> {
          try {
            GUIManager.getInstanceGUIManager().cyclistAuthenticated();
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
      }
      // else if ANALYST user, tell GUIManager
      else if (successfulLogin /*&& !isCyclist*/) {
        System.out.println("Analyst authenticated");
        userLoggedIn(); // formats GUI
        Platform.runLater(()-> {
          try {
            GUIManager.getInstanceGUIManager().analystAuthenticated();
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
      } else {
        // else credentials incorrect, call invalid credentials to display a error message
        invalidCredentials();
      }
    }
  );
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
    progressBar.setVisible(false);
    username.setDisable(false);
    password.setDisable(false);
    logIn.setDisable(false);
    join.setDisable(false);
    feedback.setText("Incorrect username or password");
    feedback.setStyle("-fx-text-fill: red");
    feedback.setVisible(true);
  }

  /** Hides the red warning label message */
  @FXML private void hideFeedbackLabel() {
    feedback.setVisible(false);
  }

  /** Prepares the login GUI for loading next screen by disabling gui elements */
  private void userLoggedIn() {
    // progressBar.setVisible(true);
    username.setDisable(true);
    password.setDisable(true);
    logIn.setDisable(true);
    join.setDisable(true);
    feedback.setText("Logged in, loading...");
    feedback.setStyle("-fx-text-fill: green");
    feedback.setVisible(true);
  }

  /** Disables the login GUI while checking credentials */
  private void userLoggingIn() {
    // progressBar.setVisible(true);
    username.setDisable(true);
    password.setDisable(true);
    logIn.setDisable(true);
    join.setDisable(true);
    feedback.setText("Loading...");
    feedback.setStyle("-fx-text-fill: green");
    feedback.setVisible(true);
  }

}
