package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Account;
import util.AccountUtil;
import util.Currency;
import view.AccountListCell;
import view.View;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class IndexController {

    private final static String EDIT_VIEW_FILE = "/view/editView.fxml";
    private List<Account> accounts;
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
        saveButton.setText("save button clicked");
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
            Account account = this.accountList.getSelectionModel().getSelectedItem();

            if (actionEvent.getSource() == euroButton) {
                editView = editView(Currency.EURO, account);
                stage = initStage(editView);
            } else if (actionEvent.getSource() == usButton) {
                editView = editView(Currency.US, account);
                stage = initStage(editView);
            } else if (actionEvent.getSource() == yenButton) {
                editView = editView(Currency.YEN, account);
                stage = initStage(editView);
            }

            if (stage != null) {
                stage.show();
            }
        }
    }

    public void init(Stage stage, List<Account> accounts) {
        this.primaryStage = primaryStage;
        this.accounts = AccountUtil.sortAccountById(accounts);
        if (this.accountList != null) {
            this.updateAccountList(this.accounts, 0);
            this.accountList.setCellFactory(a -> {
                try {
                    return new AccountListCell();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            });
        }
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setFileLabelName(String fileName) {
        fileLabel.setText(fileName);
    }

    public void updateAccount(Account updatedAccount) {
        int index = findAccount(updatedAccount);
        if (index >= 0) {
            this.accounts.set(index, updatedAccount);
            int listIndex = this.accountList.getSelectionModel().getSelectedIndex();
            updateAccountList(this.accounts, listIndex);
        }
    }

    public void setParentStage(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setOnCloseRequest(e -> exitButton());
    }

    private View editView(Currency currency, Account account) throws IOException {
        View editView = new View(EDIT_VIEW_FILE);
        EditController controller = editView.getController();

        controller.init(this, account, currency);

        return editView;
    }

    private Stage initStage(View view) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(view.getView(), 300, 300));
        return stage;
    }

    private void updateAccountList(List<Account> accounts, int index) {
        this.accountList.setItems(null);
        this.accountList.setItems(FXCollections.observableList(this.accounts));
        this.accountList.getSelectionModel().select(index);
    }

    private int findAccount(Account account) {
        return Collections.binarySearch(accounts, account);
    }

}

