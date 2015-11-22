package view;

import controller.AccountListCellController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import model.Account;

import java.io.IOException;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AccountListCell extends ListCell<Account> {

    private View cellView;
    private Parent root;
    private AccountListCellController cellController;

    public AccountListCell() throws IOException {
        cellView = new View("/view/cell.fxml");
        cellController = cellView.getController();
        root = cellView.getView();
    }

    @Override
    public void updateItem(Account account, boolean empty) {
        super.updateItem(account, empty);

        if (empty) {
            setGraphic(null);
        } else if (cellView != null && account != null) {
            cellController.accountId.setText(account.getID());
            cellController.accountName.setText(account.getName());
            cellController.accountBalance.setText(account.getStringBalance());
            setGraphic(root);
        }
    }
}
