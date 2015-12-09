package AccountEdit;

import view.View;

import java.io.IOException;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class EditView extends View {

    private final static String EDIT_VIEW_FILE = "/AccountEdit/resources/editView.fxml";

    public EditView() throws IOException {
        super(EDIT_VIEW_FILE);
    }


}
