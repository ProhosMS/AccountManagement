package AccountList;

import AccountAgent.AgentController;
import AccountEdit.EditController;
import AccountEdit.EditView;
import AccountEdit.ErrorController;
import AccountEdit.ErrorView;
import controller.AbstractController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Account.Account;
import model.Account.AccountModel;
import model.Agent.Agent;
import model.AgentThreadMonitor;
import util.AccountUtil;
import util.Currency;
import util.StageUtil;
import AccountAgent.AgentView;
import view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AccountListController extends AbstractController {

    private File file;
    private Stage primaryStage;

    public Button selectFileButton;
    public Button euroButton;
    public Button usButton;
    public Button yenButton;
    public Button saveButton;
    public Label fileLabel;
    public ListView<Account> accountList;
    public Button depositAgentButton;
    public Button withdrawAgentButton;

    @Override
    public void exitButtonHandler(ActionEvent actionEvent) {
        AgentThreadMonitor.getInstance().shutDown();
        Platform.exit();
        System.exit(0);
        this.primaryStage.close();
    }

    @FXML
    private void editHandler(final ActionEvent actionEvent) throws IOException {
        Stage stage = null;
        View editView = null;

        if (accountList != null) {
            if (actionEvent.getSource() == euroButton) {
                editView = editView(Currency.EURO, accountModel);
            } else if (actionEvent.getSource() == usButton) {
                editView = editView(Currency.US, accountModel);
            } else if (actionEvent.getSource() == yenButton) {
                editView = editView(Currency.YEN, accountModel);
            }

            stage = StageUtil.initStage(editView, 400, 300);
            if (stage != null) {
                stage.show();
            }
        }
    }

    @FXML
    private void agentHandler(ActionEvent actionEvent) throws IOException {
        View agentView = new AgentView();
        Stage stage = StageUtil.initStage(agentView, 400, 400);
        AgentController controller = agentView.getController();

        if (actionEvent.getSource() == depositAgentButton) {
            /* Deposit Agent specific */
            controller.init(accountModel.getCurrentAccount(), Agent.Type.Deposit);
        } else {
            /* Withdraw Agent specific */
            controller.init(accountModel.getCurrentAccount(), Agent.Type.Withdraw);
        }

        stage.show();
    }

    public void prepareFields() {
        fileLabel.setVisible(false);

        if (accountList != null) {
            accountList.getSelectionModel().selectedItemProperty().addListener((obs, oldAccount, newAccount) -> {
                accountModel.setCurrentAccount(newAccount);
            });

            updateAccountList(accountModel.getAccountList());
            /* Just select the first account in the list */
            accountList.getSelectionModel().select(0);

            accountModel.getCurrentAccountProperty().addListener((obs, oldAccount, newAccount) -> {
                if (newAccount != null) {
                    accountList.getSelectionModel().select(newAccount);
                }
            });

            accountList.setCellFactory(a -> {
                try {
                    return new AccountListCell();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            });
        }
    }

    public void init(Stage stage, AccountModel model) {
        setParentStage(stage);
        initModel(model);

        fileLabel.setVisible(false);
    }

    public void setParentStage(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setOnCloseRequest(e -> exitButtonHandler(null));
    }

    private View editView(Currency currency, AccountModel model) throws IOException {
        View editView = new EditView();
        EditController controller = editView.getController();

        controller.init(this, model, currency);

        return editView;
    }

    private void updateAccountList(ObservableList<Account> accountList) {
        this.accountList.setItems(accountList);
    }

    public void selectFileButtonHandler(ActionEvent actionEvent) throws IOException {
        FileChooser chooser = new FileChooser();
        file = chooser.showOpenDialog(saveButton.getScene().getWindow());
        if (file != null) {
            try {
                accountModel.loadFromFile(file);
                prepareFields();

                fileLabel.setText(file.getName());
                fileLabel.setVisible(true);
            } catch (Exception e) {
                View errorView = StageUtil.generateErrorView(e.getMessage(),450, 200);
                StageUtil.initStage(errorView, 450, 200).show();
            }
        }
    }

    public void saveButtonHandler(ActionEvent actionEvent) throws IOException {
        try {
            accountModel.saveToFile(file);
        } catch (Exception e) {
            View errorView = StageUtil.generateErrorView(e.getMessage(),450, 200);
            StageUtil.initStage(errorView, 450, 200).show();
        }
    }
}

