package AccountList.view;

import AccountList.AgentCellController;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import model.Agent.Agent;
import view.View;

import java.io.IOException;
import java.util.Map.Entry;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AgentCell extends ListCell<Entry<Agent, Thread>> {

    private final static String CELL_VIEW_FILE = "/AccountList/resources/agentCell.fxml";

    private View cellView;
    private Parent root;
    private AgentCellController cellController;

    public AgentCell() throws IOException {
        cellView = new View(CELL_VIEW_FILE);
        cellController = cellView.getController();
        root = cellView.getView();
    }

    @Override
    public void updateItem(Entry<Agent, Thread> entry, boolean empty) {
        super.updateItem(entry, empty);

        if (empty) {
            setGraphic(null);
        } else {

            getStyleClass().add("alert-info");
            Agent agent = entry.getKey();
            Thread thread = entry.getValue();

            cellController.agentTypeLabel.textProperty().bind(agent.typeProperty().asString());
            cellController.agentStatusLabel.textProperty().bind(agent.statusProperty().asString());

            cellController.threadLabel.setText(thread.getName());
            setGraphic(root);
        }
    }
}
