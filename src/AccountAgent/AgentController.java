package AccountAgent;

import controller.AbstractController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;
import model.Account.Account;
import model.Agent.Agent;
import model.Agent.DepositAgent;
import model.Agent.WithdrawAgent;
import util.StageUtil;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author sangm (sang.mercado@gmail.com)
 *
 * Controller used to start a DefinedAgent (specifically WithdrawAgent/DepositAgent)
 */
public class AgentController extends AbstractController {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^(?:0|[1-9\\.]\\d{0,2}(?:,?\\d{3})*)(?:\\.\\d+)?$");
    private static final Pattern TIME_PATTERN = Pattern.compile("^\\d+(?:\\.\\d+)?$");
    private static final Pattern FIX_NUMBER_PATTERN = Pattern.compile("[^\\d+\\.]+");

    private AgentThreadMonitor agentThreadMonitor;

    private Account account;
    private Agent.Type agentType;
    private boolean validTransferField;
    private boolean validTimeIntervalField;

    public TextField timeIntervalField;
    public TextField transferField;
    public Button startAgentButton;
    public Label agentLabel;
    public Label accountId;
    public Label accountName;
    public Label accountBalance;

    /**
     * Parent controller will call this function
     *
     * @param account   an account selected from AccountListController
     * @param agentType type of the agent to be initialized (Deposit/Withdraw)
     */
    public void init(Account account, Agent.Type agentType) {
        this.agentType = agentType;
        agentLabel.setText(agentType.toString());

        agentThreadMonitor = AgentThreadMonitor.getInstance();
        setAccount(account);
        prepareFields();
    }

    /**
     * Gets the required field for agent object and creates a new DefinedAgentView.
     * Will close the current winow after the DefinedAgentView is open.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void startAgentHandler(ActionEvent actionEvent) throws IOException {
        String transferString = FIX_NUMBER_PATTERN.matcher(transferField.getText()).replaceAll("");
        Double transferAmount = Double.parseDouble(transferString);
        long timeInterval = (long) (Double.parseDouble(timeIntervalField.getText()) * 1000);
        Agent agent;

        /* keep track of withdraw/deposit agent, if withdraw agent exist, try to unblock */

        if (agentType == Agent.Type.Deposit) {
            agent = new DepositAgent(account, agentType, timeInterval, transferAmount);
        } else {
            agent = new WithdrawAgent(account, agentType, timeInterval, transferAmount);
        }

        DefinedAgentView definedAgentView = new DefinedAgentView();
        DefinedAgentController definedAgentController = definedAgentView.getController();
        definedAgentController.init(agent);

        Stage stage = StageUtil.initStage(definedAgentView, 500, 300);

        stage.setOnCloseRequest(e -> {
            definedAgentController.exitButtonHandler(null);
        });

        stage.show();

        exitButtonHandler(null);
    }

    private void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Hooks up the labels/fields of the class to specific behaviors
     */
    private void prepareFields() {
        StageUtil.setAccountLabels(account, accountId, accountName, accountBalance);
        timeIntervalField.setText("0.0");
        startAgentButton.setDisable(true);

        timeIntervalField.textProperty().addListener((obs, oldValue, newValue) -> {
            startAgentButton.setDisable(!(verifyTimeInterval() && verifyTransferAmount()));
        });

        transferField.textProperty().addListener((obs, oldValue, newValue) -> {
            ObservableList<String> styles = transferField.getStyleClass();
            boolean validTrasferField = verifyTransferAmount();
            if (validTrasferField) {
                if (styles.contains("btn-danger")) {
                    styles.remove("btn-danger");
                }
            } else {
                if (!styles.contains("btn-danger")) {
                    styles.add("btn-danger");
                }
            }

            startAgentButton.setDisable(!(verifyTimeInterval() && verifyTransferAmount()));
        });

        Platform.runLater(() -> {
            transferField.requestFocus();
        });
    }

    /**
     * @return transferAmount is valid
     */
    private boolean verifyTransferAmount() {
        String transferAmount = transferField.getText();
        return NUMBER_PATTERN.matcher(transferAmount).find();
    }

    /**
     * @return timeInterval is valid
     */
    private boolean verifyTimeInterval() {
        String timeInterval = timeIntervalField.getText();
        return TIME_PATTERN.matcher(timeInterval).find() && Double.parseDouble(timeInterval) > 0.0;
    }
}
