package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class LoginController {

  // FXML Attributes
  @FXML private ImageView icon;
  @FXML private TextField username;
  @FXML private TextField password;
  @FXML private Button logIn;
  @FXML private Hyperlink join;


  /** Checks credentials against database to authenticate user */
  public void authenticate() {

    String enteredUsername = username.getText();
    String enteredPassword = password.getText();

    // TODO get text, and check credentials

    // If authenticated, tell GUIManager
    try {
      System.out.println("User authenticated");
      GUIManager.getInstanceGUIManager().loginAuthenticated();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }


  /** Tells GUIManager to launch the Join window */
  public void join() {

    // If wanting to join, tell GUIManager
    try {
      System.out.println("User joining...");
      GUIManager.getInstanceGUIManager().userJoin();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
