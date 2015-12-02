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
 *
 * Controller used to start a new deposit/withdraw agent on a separate thread.
 *
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

    /**
     * Parent controller will call this function
     * @param agent
     */
    public void init(Agent agent) {
        this.agent = agent;

        prepareFields(agent);

        /* open a new thread and call agent.withdraw/agent.deposit */
        agentThreadMonitor = AgentThreadMonitor.getInstance();

        agentThreadMonitor.execute(agent);
    }

    /**
     * Hooks up the labels/fields of the class to specific behaviors
     */
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

    /**
     * Every controller has a default exitbButtonHandler.
     * However, we need to make sure the agent is properly stopped.
     *
     * @param actionEvent
     */
    @Override
    public void exitButtonHandler(ActionEvent actionEvent) {
        super.exitButtonHandler(actionEvent);
        stopAgentButtonHandler();
    }

    /**
     * Sets the agentStatus to stopped and properly end the thread.
     */
    public void stopAgentButtonHandler() {
        agent.setStatus(Agent.Status.Stopped);
        agent.stop();
    }

    /**
     * pauses the agent.
     */
    public void pauseAgentHandler() {
        agent.pause();
    }

    /**
     * resumes an agent that is previously paused.
     */
    public void resumeAgentButtonHandler(ActionEvent actionEvent) {
        agent.resume();
    }
}
