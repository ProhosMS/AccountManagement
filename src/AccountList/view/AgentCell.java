package AccountList.view;

import AccountList.AgentCellController;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import model.Agent.Agent;
import util.AccountUtil;
import view.View;

import java.io.IOException;
import java.util.Map.Entry;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AgentCell extends ListCell<Entry<Agent, Thread>> {

    private final static String CELL_VIEW_FILE = "/AccountList/resources/agentCell.fxml";

    /* These values are from main.css */
    private final static String GREEN_HEX = "#5cb85c";
    private final static String BLUE_HEX = "#428bca";
    private final static String RED_HEX = "#d9534f";
    private final static String YELLOW_HEX = "#ed9c28";

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
            Agent agent = entry.getKey();
            Thread thread = entry.getValue();
            Long id = thread.getId();

            cellController.agentStatusCircle.setFill(Color.web(GREEN_HEX));

            cellController
                    .agentTransferAmountLabel
                    .textProperty()
                    .bind(agent.runningAmountProperty().asString("$%.2f"));

            cellController
                    .accountNameLabel
                    .setText(agent.getAccount().getName());

            agent.statusProperty().addListener((obs, oldStatus, newStatus) -> {
                if (newStatus == Agent.Status.Running) {
                    cellController.agentStatusCircle.setFill(Color.web(GREEN_HEX));
                } else if (newStatus == Agent.Status.Paused) {
                    cellController.agentStatusCircle.setFill(Color.web(YELLOW_HEX));
                } else if (newStatus == Agent.Status.Blocked) {
                    cellController.agentStatusCircle.setFill(Color.web(RED_HEX));
                }
            });

            cellController.agentTypeLabel.textProperty().bind(agent.typeProperty().asString());

            cellController.threadLabel.setText(String.format("Thread %s", id.toString()));
            setGraphic(root);
        }
    }
}
