package window;

import static javafx.application.Application.launch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The class Warning runs the warning.fxml file to build the Warning GUI window
 * Designed to display a custom warning message
 */
public class Warning extends Application{

  public static void main(String[] args) {
    launch(args);
  }
  @Override
  public void start(Stage primaryStage) throws Exception {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/gui/warning.fxml"));//shows as an error in intelliJ but it works
      primaryStage.setTitle("Vélo");
      primaryStage.setScene(new Scene(root, 500, 400));
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
