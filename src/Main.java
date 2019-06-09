import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    public FXMLLoader loader = new FXMLLoader();
    public StackPane pane = new StackPane();
    public Scene scena = new Scene(pane);



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/Scena.fxml"));
        pane = loader.load();

        Controller controller = loader.getController();
        scena = new Scene(pane);
        primaryStage.setScene(scena);
        primaryStage.setTitle("Most");
        primaryStage.show();
    }
}
