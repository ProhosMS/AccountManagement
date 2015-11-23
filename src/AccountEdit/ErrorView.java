package AccountEdit;

import view.View;

import java.io.IOException;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class ErrorView extends View {

    private static final String ERROR_VIEW_FILE = "/AccountEdit/resources/errorView.fxml";

    public ErrorView() throws IOException {
        super(ERROR_VIEW_FILE);
    }
}
