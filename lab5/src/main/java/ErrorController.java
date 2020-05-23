import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ErrorController {
    @FXML
    private Text errorLabel;

    void setErrorLabel(String errorLabel) {
        this.errorLabel.setText(errorLabel);
    }
}
