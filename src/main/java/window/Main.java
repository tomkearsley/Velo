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
    //System.out.print(getClass().getResource("../resource/main.fxml"));//prints null
    Parent root = FXMLLoader.load(getClass().getResource("../resource/main.fxml"));
    primaryStage.setTitle("Velo");
    primaryStage.setScene(new Scene(root, 500, 400));
    primaryStage.show();
  }

}

