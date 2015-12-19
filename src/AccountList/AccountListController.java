package AccountList;

import AccountAgent.AgentController;
import AccountEdit.EditController;
import AccountEdit.EditView;
import AccountList.view.AccountListCell;
import AccountList.view.AgentCell;
import controller.AbstractController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
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
import util.Currency;
import util.StageUtil;
import AccountAgent.AgentView;
import view.View;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AccountListController extends AbstractController {

    private ObservableMap<Agent, Thread> runningAgents;

    private File file;
    private Stage primaryStage;

    public ListView<Map.Entry<Agent, Thread>> agentList;
    public ListView<Account> accountList;
    public Button selectFileButton;
    public Button euroButton;
    public Button usButton;
    public Button yenButton;
    public Button saveButton;
    public Label fileLabel;
    public Button depositAgentButton;
    public Button withdrawAgentButton;

    @Override
    public void exitButtonHandler(ActionEvent actionEvent) {
        super.closeSystem();
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

            agentList.setCellFactory(a -> {
                try {
                    return new AgentCell();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            });
        }

        if (agentList != null) {
            runningAgents
                    .addListener((MapChangeListener<? super Agent, ? super Thread>) item -> {
                        Set<Map.Entry<Agent, Thread>> agents = runningAgents.entrySet();
                        Platform.runLater(() -> agentList.setItems(FXCollections.observableArrayList(agents)));
                        System.out.println(agents);
            });
        }
    }

    public void init(Stage stage, AccountModel model) {
        setParentStage(stage);
        initModel(model);
        runningAgents = AgentThreadMonitor.getInstance().observableRunningAgents();

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

