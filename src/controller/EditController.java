package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Account;
import util.Currency;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class EditController implements Initializable {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^(?:0|[1-9\\.]\\d{0,2}(?:,?\\d{3})*)(?:\\.\\d+)?$");
    private static final Pattern FIX_NUMBER_PATTERN = Pattern.compile("[^\\d+\\.]+");


    private IndexController parentController;
    private Account account;
    private Currency currency;

    /* FXML Properties */
    public TextField amountField;
    public Label accountId;
    public Label accountName;
    public Label accountCurrency;
    public Label accountBalance;
    public Button depositButton;
    public Button withdrawButton;
    public Button dismissButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        amountField.setText("0.0");
        amountField.textProperty().addListener((observer, oldValue, newValue) -> {
            ObservableList<String> styles = amountField.getStyleClass();
            if (amountField.getText().length() == 0 || NUMBER_PATTERN.matcher(amountField.getText()).find()) {
                if (styles.contains("btn-danger")) {
                    styles.remove("btn-danger");
                }
            } else {
                if (!styles.contains("btn-danger")) {
                    styles.add("btn-danger");
                }
            }
        });
    }

    public void init(IndexController indexController, Account account, Currency currency) {
        setAccount(account);
        setCurrency(currency);
        setParentController(indexController);
    }

    private void setAccount(Account account) {
        this.account = account;
        this.accountId.setText(account.getID());
        this.accountName.setText(account.getName());
        this.accountBalance.setText("$" + account.getBalance().toString());
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
        if (currency == Currency.US) {
           this.accountCurrency.setText("US");
        } else if (currency == Currency.EURO) {
            this.accountCurrency.setText("EURO");
        } else if (currency == Currency.YEN) {
            this.accountCurrency.setText("YEN");
        }
    }

    public void setParentController(IndexController parentController) {
        this.parentController = parentController;
    }

//
//    @FXML
//    private void deposit(ActionEvent actionEvent) {
//        String depositString = FIX_NUMBER_PATTERN.matcher(amountField.getText()).replaceAll("");
//        Double unconvertedDepositAmount = Double.parseDouble(depositString);
//        Double depositAmount = Currency.convert(unconvertedDepositAmount, this.curreny);
//
//        this.account.deposit(depositAmount);
//
//        parentController.updateAccount(this.account);
//        amountField.setText("0.0");
//    }
//

//
//
//
//    public void giveAccount(Account selectedItem) {
//        selectedItem.deposit(200.0);
//    }
//

//

}
