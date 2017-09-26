package controller;

public class LoginController {

  public void authenticate() {

    // TODO add @fxml for textfields, get text, and check credentials
    System.out.println("Authenticated");

    // If authenticated, tell GUIManager
    try {
      GUIManager.getInstanceGUIManager().loginAuthenticated();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
