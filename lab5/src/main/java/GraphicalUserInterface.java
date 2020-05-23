import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;

public class GraphicalUserInterface {

    InterfaceController interfaceController;
    Stage filterStage;
    Stage primaryStage;

    GraphicalUserInterface(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("interface.fxml"));
        primaryStage = setUpStage(loader);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(WindowEvent -> System.exit(0));
        primaryStage.show();

        this.primaryStage = primaryStage;
        this.interfaceController = loader.getController();
        this.interfaceController.init(this);
    }

    public void setUpErrorStage(String errorLabel) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("error.fxml"));
        Stage errorStage = setUpStage(loader);
        errorStage.setResizable(false);
        errorStage.setAlwaysOnTop(true);
        errorStage.show();

        ErrorController errorController = loader.getController();
        errorController.setErrorLabel(errorLabel);
    }

    public void setUpFilterStage() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("filter.fxml"));
        filterStage = setUpStage(loader);
        filterStage.setOnCloseRequest(WindowEvent -> interfaceController.switchCheckBox(false));
        filterStage.setResizable(false);
        filterStage.show();

        FilterController fIlterController = loader.getController();
        fIlterController.init(this);
    }

    private Stage setUpStage(FXMLLoader loader) {
        AnchorPane root;
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        return stage;
    }

    public InterfaceController getInterfaceController() {
        return interfaceController;
    }

    public void closeFilterStage() {
        if (filterStage != null) {
            filterStage.close();
        }
    }
}
