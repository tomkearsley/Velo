package window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The class UserPOIForm runs the userPOIForm.fxml file to build the UserPOIForm GUI window
 */
public class UserPOIForm extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/gui/userPOIForm.fxml"));//shows as an error in intelliJ but it works
      primaryStage.setTitle("Vélo");
      primaryStage.setScene(new Scene(root));
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
