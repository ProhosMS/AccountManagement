import controller.IndexController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Account;
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

        List<Account> accounts = Arrays.asList(
                new Account("Person1", "aaa", 200.0),
                new Account("Person2", "caa", 300.0),
                new Account("Person3", "baa", 400.0),
                new Account("Person4", "baa", 400.0)
        );


        controller.init(primaryStage, accounts);
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
