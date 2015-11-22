package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class View {

    private FXMLLoader loader;
    private Parent root;

    public View(String filename) throws IOException {
        this.loader = new FXMLLoader(getClass().getResource(filename));
        this.root = this.loader.load();
    }

    public <T> T getController() {
        return this.loader.getController();
    }

    public Parent getView() throws IOException {
        return this.root;
    }
}
