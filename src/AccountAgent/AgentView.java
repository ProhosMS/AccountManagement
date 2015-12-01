package AccountAgent;

import view.View;

import java.io.IOException;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AgentView extends View {

    private final static String VIEW_FILE = "/AccountAgent/resources/agentView.fxml";

    public AgentView() throws IOException {
        super(VIEW_FILE);
    }
}
