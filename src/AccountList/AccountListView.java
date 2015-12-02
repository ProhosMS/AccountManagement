package AccountList;

import view.View;

import java.io.IOException;

/**
 * @author sangm (sang.mercado@gmail.com)
 */

public class AccountListView extends View {
    private static final String ACCOUNT_LIST_VIEW_FILE = "/AccountList/resources/index.fxml";

    public AccountListView() throws IOException {
        super(ACCOUNT_LIST_VIEW_FILE);
    }
}
