import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Data Base");
        new GraphicalUserInterface(primaryStage);
    }
}
