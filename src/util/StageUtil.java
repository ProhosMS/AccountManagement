package util;

import AccountEdit.ErrorController;
import AccountEdit.ErrorView;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Account.Account;
import view.View;

import java.io.IOException;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class StageUtil {

    public static Stage initStage(View view, int x, int y) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(view.getView(), x, y));
        return stage;
    }

    public static void setAccountLabels(Account account, Label accountId, Label accountName, Label accountBalance) {
        accountId.setText(account.getID());
        accountName.setText(account.getName());
        accountBalance.setText(AccountUtil.getStringBalance(account.getBalance()));

        account.getBalanceProperty().addListener((obs, oldBalance, newBalance) -> {
            if (newBalance != null) {
                accountBalance.setText(AccountUtil.getStringBalance(account.getBalance()));
            }
        });
    }

    public static View generateErrorView(String message, int x, int y) throws IOException {
        View errorView = new ErrorView();
        ErrorController errorController = errorView.getController();
        errorController.init(message);

        return errorView;
//        Stage stage = StageUtil.initStage(errorView, x, y);
//        stage.show();
    }
}
