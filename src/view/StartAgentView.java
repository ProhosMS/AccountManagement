package view;

import java.io.IOException;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class StartAgentView extends View{

    private final static String VIEW_FILE = "/AccountAgent/resources/agentView.fxml";

    public StartAgentView() throws IOException {
        super(VIEW_FILE);
    }
}
