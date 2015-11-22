package controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Account;
import model.AccountModel;
import util.Currency;
import view.AccountListCell;
import view.View;

import java.io.IOException;
import java.util.List;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class IndexController extends AbstractController {

    private final static String EDIT_VIEW_FILE = "/view/editView.fxml";
    private Stage primaryStage;

    @FXML
    private Button exitButton;
    @FXML
    private Button euroButton;
    @FXML
    private Button usButton;
    @FXML
    private Button yenButton;
    @FXML
    private Button saveButton;
    @FXML
    private Label fileLabel;
    @FXML
    private ListView<Account> accountList;

    @FXML
    private void saveButtonClickEvent() {
        accountModel.saveToFile("/tmp");
    }

    @FXML
    private void exitButton() {
        this.primaryStage.close();
        this.primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    @FXML
    private void editHandler(final ActionEvent actionEvent) throws IOException {
        Stage stage = null;
        View editView = null;

        if (this.accountList != null) {
            Account account = accountModel.getCurrentAccount();

            if (actionEvent.getSource() == euroButton) {
                editView = editView(Currency.EURO, accountModel);
                stage = initStage(editView);
            } else if (actionEvent.getSource() == usButton) {
                editView = editView(Currency.US, accountModel);
                stage = initStage(editView);
            } else if (actionEvent.getSource() == yenButton) {
                editView = editView(Currency.YEN, accountModel);
                stage = initStage(editView);
            }

            if (stage != null) {
                stage.show();
            }
        }
    }

    public void init(Stage stage, AccountModel model) {
        primaryStage = primaryStage;
        initModel(model);

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

    public void setFileLabelName(String fileName) {
        fileLabel.setText(fileName);
    }

    public void updateAccount(Account updatedAccount) {
//        int index = findAccount(updatedAccount);
//        if (index >= 0) {
//            this.accounts.set(index, updatedAccount);
//            int listIndex = this.accountList.getSelectionModel().getSelectedIndex();
//            updateAccountList(this.accounts, listIndex);
//        }
    }

    public void setParentStage(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setOnCloseRequest(e -> exitButton());
    }

    public List<Account> getAccounts() {
        return accountModel.getAccountList();
    }

    private View editView(Currency currency, AccountModel model) throws IOException {
        View editView = new View(EDIT_VIEW_FILE);
        EditController controller = editView.getController();

        controller.init(this, model, currency);

        return editView;
    }

    private Stage initStage(View view) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(view.getView(), 300, 300));
        return stage;
    }

    private void updateAccountList(ObservableList<Account> accountList) {
        this.accountList.setItems(accountList);
    }

//    private int findAccount(Account account) {
//        return Collections.binarySearch(accounts, account);
//    }

}

