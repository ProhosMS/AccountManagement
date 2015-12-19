package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Account.AccountModel;
import model.AgentThreadMonitor;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AbstractController implements Controller {

    protected AccountModel accountModel;
    public Button exitButton;

    @Override
    public void initModel(AccountModel model) {
        if (this.accountModel != null) {
            throw new IllegalStateException("Model can only be set once");
        }
        this.accountModel = model;
    }

    public void exitButtonHandler(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void closeSystem() {
        AgentThreadMonitor.getInstance().shutDown();
        Platform.exit();
        System.exit(0);
    }
}
