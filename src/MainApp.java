import controller.IndexController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Account;
import model.AccountModel;
import util.HandleArguments;
import view.View;

import java.util.Arrays;
import java.util.List;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        View mainView = new View("/view/index.fxml");

        Parent root = mainView.getView();

        IndexController controller = mainView.getController();

        /* IndexController expects primaryStage, filename, list of accounts */
        List<String> params = getParameters().getRaw();
        String filename = HandleArguments.process(params);

        AccountModel accountModel = new AccountModel();
        accountModel.loadFromFile(filename);

        controller.init(primaryStage, accountModel);
        controller.setParentStage(primaryStage);

        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Hardcode filename and read in accounts
        // Let the table view show list of accounts
        launch(args);
    }
}
