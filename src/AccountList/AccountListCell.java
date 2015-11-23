package AccountList;

import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import model.Account;
import view.View;

import java.io.IOException;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AccountListCell extends ListCell<Account> {

    private final static String CELL_VIEW_FILE = "/AccountList/resources/cell.fxml";

    private View cellView;
    private Parent root;
    private AccountListCellController cellController;

    public AccountListCell() throws IOException {
        cellView = new View(CELL_VIEW_FILE);
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
