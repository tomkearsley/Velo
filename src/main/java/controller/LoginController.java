package controller;

import filehandler.MySQL;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
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

    // TODO and check credentials

    MySQL mysql = new MySQL();
    ArrayList<Boolean> LoginResult = new ArrayList<Boolean>();
    try {
        LoginResult = mysql.login(enteredUsername, enteredPassword);
    }
    catch (Exception e){
      System.out.println(e);
    }


    // TODO If CYCLIST authenticated, tell GUIManager
    Boolean isCyclist = LoginResult.get(0);
    Boolean sucessfulLogin = LoginResult.get(1);

    if (sucessfulLogin && isCyclist) {
      try {
        System.out.println("User authenticated");
        GUIManager.getInstanceGUIManager().cyclistAuthenticated();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    // TODO else if ANALYST user, tell GUIManager
    else if (isCyclist == false && sucessfulLogin == true) {
      try {
        System.out.println("Analyst authenticated");
        GUIManager.getInstanceGUIManager().analystAuthenticated();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }


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

}
