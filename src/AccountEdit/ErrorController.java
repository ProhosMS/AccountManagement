package AccountEdit;

import controller.AbstractController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class ErrorController extends AbstractController {

    public Label errorLabel;

    public void init(String errorMessage) {
        errorLabel.setText(errorMessage);
    }
}
