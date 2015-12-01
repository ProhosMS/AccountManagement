package AccountAgent;

import view.View;

import java.io.IOException;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class DefinedAgentView extends View {

    private final static String VIEW_FILE = "/AccountAgent/resources/definedAgentView.fxml";

    public DefinedAgentView() throws IOException {
        super(VIEW_FILE);
    }
}
