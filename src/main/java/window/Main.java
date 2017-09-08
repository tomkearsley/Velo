package window;

/**
 * Created by tyler kennedy on 25/8/17
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));//shows as an error in intelliJ but it works
      primaryStage.setTitle("Velo");
      primaryStage.setScene(new Scene(root, 500, 400));
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
