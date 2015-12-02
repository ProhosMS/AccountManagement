import AccountList.AccountListController;
import AccountList.AccountListView;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Account.AccountModel;
import view.View;

import java.util.stream.Collectors;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        View accountListView = new AccountListView();
        Parent root = accountListView.getView();
        AccountListController controller = accountListView.getController();

        AccountModel accountModel = new AccountModel();

        controller.init(primaryStage, accountModel);

        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Hardcode filename and read in accounts
        // Let the table view show list of accounts
        launch(args);
    }
}
