import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FilterController {
    private GraphicalUserInterface graphicalUserInterface;
    @FXML
    private TextField leftBorder;
    @FXML
    private TextField rightBorder;

    public void init(GraphicalUserInterface graphicalUserInterface) {
        this.graphicalUserInterface = graphicalUserInterface;
    }

    @FXML
    private void processFilter() {
        double leftBorder;
        double rightBorder;
        try {
            leftBorder = Double.parseDouble(this.leftBorder.getText());
            rightBorder = Double.parseDouble(this.rightBorder.getText());
        }
        catch (NumberFormatException e) {
            graphicalUserInterface.setUpErrorStage(e.getMessage());
            return;
        }

        graphicalUserInterface.getInterfaceController().filterCommand(leftBorder, rightBorder);
    }
}
