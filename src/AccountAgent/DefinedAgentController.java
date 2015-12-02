package AccountAgent;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Agent.Agent;
import model.AgentThreadMonitor;
import util.AccountUtil;

import java.util.concurrent.Future;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class DefinedAgentController {

    private AgentThreadMonitor agentThreadMonitor;
    private Agent agent;

    public Button resumeAgentButton;
    public Button stopAgentButton;
    public Button pauseAgentButton;
    public Button dismissAgentButton;
    public Label agentLabel;
    public Label timeIntervalLabel;
    public Label operationCountLabel;
    public Label transferAmountLabel;
    public Label stateLabel;
    public Label runningTransferAmountLabel;

    public void init(Agent agent) {
        this.agent = agent;

        prepareFields(agent);

        /* open a new thread and call agent.withdraw/agent.deposit */
        agentThreadMonitor = AgentThreadMonitor.getInstance();

        agentThreadMonitor.execute(agent);
    }

    private void prepareFields(Agent agent) {
        Agent.Type agentType = agent.getType();
        Double timeInterval = agent.getTimeInterval().doubleValue() / 1000;
        double transferAmount = agent.getTransferAmount();

        if (agentType != null) {
            agentLabel.setText(agentType.toString());
        }

        timeIntervalLabel.setText(String.format("Every %.2f second(s)", timeInterval));

        String transferAmountString = AccountUtil.getStringBalance(transferAmount);
        transferAmountLabel.setText(String.format("Transfer %s", transferAmountString));

        runningTransferAmountLabel.setText(AccountUtil.getStringBalance(0.0));

        dismissAgentButton.setDisable(true);
        resumeAgentButton.setDisable(true);

        agent.statusProperty().addListener((obs, oldValue, newValue) -> {
            Agent.Status validStatus = newValue == null ? oldValue : newValue;
            stateLabel.setText(validStatus.toString());

            if (newValue != null && newValue == Agent.Status.Stopped) {
                dismissAgentButton.setDisable(false);
                pauseAgentButton.setDisable(true);
                stopAgentButton.setDisable(true);
                resumeAgentButton.setDisable(true);
            } else if (newValue == Agent.Status.Paused) {
                resumeAgentButton.setDisable(false);
            }
        });

        runningTransferAmountLabel.textProperty().bind(agent.runningAmountProperty().asString("$%.2f"));
        operationCountLabel.textProperty().bind(agent.counterProperty().asString("%s operations"));

    }

    public void dismissAgentButtonHandler(ActionEvent actionEvent) {
        Stage stage = (Stage) dismissAgentButton.getScene().getWindow();
        stage.close();

        stopAgentButtonHandler();
    }

    public void stopAgentButtonHandler() {
        agent.setStatus(Agent.Status.Stopped);

        agent.stop();
    }

    public void pauseAgentHandler() {
        agent.pause();
        /* Rename the button/handler to something more vague that does both pausing/resuming */
        /* Change class from alert-info to alert-success when going from pause to resume */
    }

    public void resumeAgentButtonHandler(ActionEvent actionEvent) {
        agent.resume();
    }
}
