package window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The class MainAnalyst runs the mainAnalyst.fxml file to build the MainAnalyst GUI window
 */
public class MainAnalyst extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/gui/mainAnalyst.fxml"));//shows as an error in intelliJ but it works
      primaryStage.setTitle("Vélo");
      primaryStage.setScene(new Scene(root));
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
