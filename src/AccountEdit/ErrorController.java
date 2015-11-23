package AccountEdit;

import controller.AbstractController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class ErrorController {

    public Label errorLabel;
    public Button dismissButton;

    public void init(String errorMessage) {
        errorLabel.setText(errorMessage);
    }

    public void dismissButtonHandler(ActionEvent actionEvent) {
        Stage stage = (Stage) dismissButton.getScene().getWindow();
        stage.close();
    }
}
