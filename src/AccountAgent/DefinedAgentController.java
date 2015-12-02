package AccountAgent;

import controller.AbstractController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Agent.Agent;
import model.AgentThreadMonitor;
import util.AccountUtil;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class DefinedAgentController extends AbstractController {

    private AgentThreadMonitor agentThreadMonitor;
    private Agent agent;

    public Button resumeAgentButton;
    public Button stopAgentButton;
    public Button pauseAgentButton;
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

        exitButton.setDisable(true);
        resumeAgentButton.setDisable(true);

        agent.statusProperty().addListener((obs, oldValue, newValue) -> {
            Agent.Status validStatus = newValue == null ? oldValue : newValue;
            stateLabel.setText(validStatus.toString());

            if (newValue != null && newValue == Agent.Status.Stopped) {
                exitButton.setDisable(false);
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

    @Override
    public void exitButtonHandler(ActionEvent actionEvent) {
        super.exitButtonHandler(actionEvent);
        stopAgentButtonHandler();
    }

    public void stopAgentButtonHandler() {
        agent.setStatus(Agent.Status.Stopped);
        agent.stop();
    }

    public void pauseAgentHandler() {
        agent.pause();
    }

    public void resumeAgentButtonHandler(ActionEvent actionEvent) {
        agent.resume();
    }
}
