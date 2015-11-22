package util;

import javafx.scene.Scene;
import javafx.stage.Stage;
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
}
