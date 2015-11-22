package AccountEdit;

import AccountList.IndexController;
import controller.AbstractController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import model.Account;
import model.AccountModel;
import util.Currency;
import view.View;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class EditController extends AbstractController implements Initializable {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^(?:0|[1-9\\.]\\d{0,2}(?:,?\\d{3})*)(?:\\.\\d+)?$");
    private static final Pattern FIX_NUMBER_PATTERN = Pattern.compile("[^\\d+\\.]+");

    private IndexController parentController;
    private Account account;
    private Currency currency;

    /* FXML */
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
            if (verifyAmount(amountField.getText())) {
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

    public void init(IndexController indexController, AccountModel model, Currency currency) {
        initModel(model);
        setAccount(model.getCurrentAccount());
        setCurrency(currency);
        setParentController(indexController);

        account.getBalanceProperty().addListener((obs, oldBalance, newBalance) -> {
            if (newBalance != null) {
                accountBalance.setText(account.getStringBalance());
            }
        });
    }

    private void setAccount(Account account) {
        this.account = account;

        accountId.setText(account.getID());
        accountName.setText(account.getName());
        accountBalance.setText(account.getStringBalance());
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

    public void exitButtonHandler(ActionEvent actionEvent) {
        Stage stage = (Stage) dismissButton.getScene().getWindow();
        stage.close();
    }

    public void depositButtonHandler(ActionEvent actionEvent) {
        String unverifiedText = amountField.getText();

        if (verifyAmount(unverifiedText)) {
            String depositString = FIX_NUMBER_PATTERN.matcher(amountField.getText()).replaceAll("");
            Double unconvertedDepositAmount = Double.parseDouble(depositString);
            Double depositAmount = Currency.convert(unconvertedDepositAmount, this.currency);

            account.deposit(depositAmount);
            amountField.setText("0.0");
            amountField.requestFocus();
        }
    }

    public void withdrawButtonHandler(ActionEvent actionEvent) {
        String fieldText = amountField.getText();

        if (verifyAmount(fieldText)) {
            String withdrawString = FIX_NUMBER_PATTERN.matcher(fieldText).replaceAll("");
            Double rawWithdrawAmount = Double.parseDouble(withdrawString);
            Double withdrawAmount = Currency.convert(rawWithdrawAmount, this.currency);
            try {
                account.withdraw(withdrawAmount);
            } catch (IllegalArgumentException e) {
                /** What requirements asks me to do
                 * controller must request the view to open a pop-up window that contains message
                 * “Insufficient funds: amount to withdraw is x, it is greater than available funds: y”.
                 * The pop-up window contains button “Dismiss” on pressing which the pop-up window should close.
                 */

//                View errorView = new ErrorView();
//                ErrorController errorController = errorView.getController();
//                errorController.init(e.getMessage());
//                Stage stage = initStage(errorView);
//                stage.show();

            } finally {
                amountField.setText("0.0");
                amountField.requestFocus();
            }
        }
    }

    private boolean verifyAmount(String amountText) {
        return (amountText.length() == 0 ||
                NUMBER_PATTERN.matcher(amountText).find());
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
